CREATE OR REPLACE FUNCTION buy_hero(userId integer, heroId integer) RETURNS bool AS
$$
DECLARE
    hero_price integer = (SELECT "price"
                          FROM "hero"
                          WHERE "id" = heroId);
BEGIN
    IF (hero_price > (SELECT "balance" FROM "user" WHERE "id" = userId)) THEN
        RETURN FALSE;
    END IF;

    UPDATE "user"
    SET "balance" = "balance" - hero_price
    WHERE "id" = userId;

    INSERT INTO "character" ("experience", "hero_id", "user_id")
    VALUES (0, heroId, userId);

    RETURN TRUE;
END;
$$ LANGUAGE plpgsql;

CREATE TYPE participant_info AS
(
    "id"             integer,
    "user_id"        integer,
    "participant_id" integer,
    "health"         integer,
    "damage"         bigint,
    "stamina"        integer,
    "luck"           integer
);

CREATE OR REPLACE FUNCTION get_base_character_info(characterId integer, songId integer) RETURNS SETOF participant_info AS
$$
BEGIN
    RETURN QUERY
        SELECT "character".id, "character".user_id, -1, H.health,
               SUM(amount * N.damage) AS "damage",
               0 AS "stamina",
               0 AS "luck"
        FROM "character"
                 JOIN "hero" H on "character".hero_id = H.id
                 JOIN "song" S on H.id = S.hero_id
                 JOIN "note_song" NS on S.id = NS.song_id
                 JOIN "note" N on N.id = NS.note_id
        WHERE "character".id = characterId
          AND S.id = songId
        GROUP BY "character".id, H.health;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION get_characters_info_with_songs(characters integer[], songs integer[]) RETURNS SETOF participant_info AS
$$
DECLARE
    x integer;
BEGIN
    FOR x in 1..array_length(characters, 1)
        LOOP
            RETURN NEXT get_base_character_info(characters[x], songs[x]);
        end loop;
end;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION get_upgrade_character_info(participant participant_info, effectId integer) RETURNS participant_info AS
$$
DECLARE
    effect "effect"%ROWTYPE;
BEGIN
    SELECT * INTO effect FROM "effect" WHERE "effect".id = effectId;
    -- somehow increase damage and health
    -- for now assume values are in percents
    participant.health = participant.health * (1 + effect.constitution/100);
    participant.damage = participant.damage * (1 + effect.strength/100);

    participant.luck = effect.luck;
    participant.stamina = effect.stamina;

    RETURN participant;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION shuffle_array(arr anyarray) RETURNS anyarray AS
$$
BEGIN
    RETURN (SELECT array_agg(u ORDER BY random()) FROM unnest(arr) AS u);
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION randint(min integer, max integer) RETURNS integer AS
$$
BEGIN
    RETURN floor((SELECT * FROM random()) * (max + 1 - min) + min);
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION begin_fight(integer[], integer[], integer[], integer) RETURNS SETOF "fight_moves" AS
$$
DECLARE
    characters ALIAS FOR $1;
    effects ALIAS FOR $2;
    songs ALIAS FOR $3;
    location ALIAS FOR $4;
    orders integer[] = (SELECT array_agg(gs.val) FROM generate_series(1, array_length(characters, 1)) gs(val));
    character_info participant_info[];
    x participant_info;
    y participant_info;
    total_players integer = array_length(characters, 1);
    alive_amount integer = total_players;
    k integer;
    p integer;
    kicks integer;
    alive_ids integer[] = characters;
    fightId integer;
    fight_participants integer[];
    moveNumber integer = 0;
BEGIN
    INSERT INTO "fight" (start, location_id) VALUES (now(), location) RETURNING "fight".id INTO fightId;

    FOR i in 1..array_length(characters, 1)
    LOOP
        INSERT INTO "fight_participant" (fight_id, effect_id, song_id, character_id, experience_gained, gold_gained, position)
        VALUES (fightId, effects[i], songs[i], characters[i], 0, 0, 0) RETURNING id INTO p;

        -- get basic info about hero characteristics

        x = get_upgrade_character_info(
                get_base_character_info(characters[i], songs[i]),
                effects[i]);
        x.participant_id = p;

        character_info = array_append(character_info, x);
        RAISE NOTICE 'character %: %', i, character_info[i];
    END LOOP;

    WHILE alive_amount > 1
    LOOP
        orders = shuffle_array(orders);
        RAISE NOTICE 'orders: %', orders;
        FOREACH k IN ARRAY orders
        LOOP
            x = character_info[k];
            IF (x.health <= 0) THEN
                CONTINUE;
            END IF;
            moveNumber = moveNumber + 1;
            -- stamina is in 20es of 100;
            IF (random() < x.stamina / 20) THEN
                kicks = 2;
            ELSE
                kicks = 1;
            END IF;

            RAISE NOTICE '% attacks with % kicks', x.id, kicks;
            FOR i IN 1..kicks
            LOOP
                raise notice 'Alive_ids: %', alive_ids;
                LOOP
                    p = randint(1, array_length(alive_ids, 1));
                    EXIT WHEN alive_ids[p] <> x.id;
                END LOOP;

                RAISE NOTICE 'p = %', p;
                RAISE NOTICE 'character_info: %', character_info;

                FOREACH y IN ARRAY character_info
                LOOP
                    IF (y.id = alive_ids[p]) THEN
                        RAISE NOTICE 'victim is %', y.id;
                        INSERT INTO "fight_moves" (move_number, fight_id, attacker_id, victim_id, damage)
                        VALUES (moveNumber, fightId, x.participant_id, y.participant_id, x.damage);

                        y.health = y.health - x.damage;

                        IF (y.health <= 0) THEN
                            alive_amount = alive_amount - 1;
                            alive_ids = array_remove(alive_ids, y.id);
                            UPDATE "fight_participant"
                            SET position = alive_amount + 1,
                                experience_gained = 60 * total_players / pow(2, alive_amount + 1),
                                gold_gained = 100 * total_players / pow(2, alive_amount + 1)
                            WHERE id = y.participant_id;
                            RAISE NOTICE 'Victim died';
                        end if;
                        EXIT;
                    end if;
                END LOOP;

            END LOOP;
        END LOOP;
    END LOOP;

    UPDATE "fight_participant"
    SET position = 1,
        experience_gained = 60 * total_players / 2,
        gold_gained = 30 * total_players / 2
    WHERE id = x.participant_id;

    RETURN QUERY (SELECT fight_moves.fight_id, move_number, AFP.character_id, VFP.character_id, damage FROM fight_moves
                JOIN "fight_participant" AFP on fight_moves.attacker_id = AFP.id
                JOIN "fight_participant" VFP on fight_moves.victim_id = VFP.id
                WHERE fight_moves.fight_id = fightId);
END;
$$ LANGUAGE 'plpgsql';
