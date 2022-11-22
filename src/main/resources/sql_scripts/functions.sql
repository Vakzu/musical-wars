CREATE OR REPLACE FUNCTION buy_hero(userId integer, heroId integer) RETURNS bool AS
$$
DECLARE
    hero_price integer = (SELECT "price" FROM "Hero" WHERE "id" = heroId);
BEGIN
    IF (hero_price > (SELECT "balance" FROM "User" WHERE "id" = userId)) THEN
        RETURN FALSE;
    END IF;

    UPDATE "User"
    SET "balance" = "balance" - hero_price
    WHERE "id" = userId;

    INSERT INTO "Character" ("experience", "hero_id", "user_id")
    VALUES (0, heroId, userId);

    RETURN TRUE;
END;
$$ LANGUAGE 'plpgsql'