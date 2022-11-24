CREATE TABLE "Location"
(
    "id"   serial PRIMARY KEY,
    "name" varchar(50) NOT NULL
);

CREATE TABLE "Hero"
(
    "id"     serial PRIMARY KEY,
    "name"   varchar(50) NOT NULL,
    "price"  integer     NOT NULL CHECK ("price" >= 0),
    "health" integer     NOT NULL CHECK ("health" > 0)
);

CREATE TABLE "Song"
(
    "id"               serial PRIMARY KEY,
    "name"             varchar(50) NOT NULL,
    "experience_level" integer     NOT NULL CHECK ("experience_level" >= 0),
    "hero_id"          integer     NOT NULL REFERENCES "Hero" ("id") ON DELETE CASCADE
);

CREATE TABLE "Note"
(
    "id"     serial PRIMARY KEY,
    "name"   varchar(10) NOT NULL,
    "damage" integer     NOT NULL CHECK ("damage" >= 0)
);

CREATE TABLE "Note_Song"
(
    "note_id" integer NOT NULL REFERENCES "Note" ("id") ON DELETE CASCADE,
    "song_id" integer NOT NULL REFERENCES "Song" ("id") ON DELETE CASCADE,
    "amount"  integer NOT NULL
);

CREATE TABLE "User"
(
    "id"        serial PRIMARY KEY,
    "name"      varchar(50)  NOT NULL,
    "is_online" boolean      NOT NULL,
    "balance"   integer      NOT NULL CHECK ("balance" >= 0) DEFAULT 1000,
    "password"  varchar(255) NOT NULL
);

CREATE TABLE "Effect"
(
    "id"    serial PRIMARY KEY,
    "name"  varchar(50),
    "price" integer NOT NULL CHECK ("price" >= 0),
    "stamina" integer NOT NULL CHECK("stamina" >= 0),
    "strength" integer NOT NULL CHECK("strength" >= 0),
    "luck" integer NOT NULL CHECK("luck" >= 0),
    "constitution" integer NOT NULL CHECK("constitution" >= 0)
);

CREATE TABLE "Character"
(
    "id"         serial PRIMARY KEY,
    "experience" integer NOT NULL CHECK ("experience" >= 0),
    "hero_id"    integer NOT NULL REFERENCES "Hero" ("id") ON DELETE CASCADE,
    "user_id"    integer NOT NULL REFERENCES "User" ("id") ON DELETE CASCADE
);

CREATE TABLE "Fight"
(
    "id"          serial PRIMARY KEY,
    "start"       timestamp with time zone NOT NULL,
    "location_id" integer REFERENCES "Location" ("id") ON DELETE SET NULL
);

CREATE TABLE "Fight_Participant"
(
    "id"                serial PRIMARY KEY,
    "fight_id"          integer NOT NULL REFERENCES "Fight" ("id") ON DELETE CASCADE,
    "effect_id"         integer NOT NULL REFERENCES "Effect" ("id") ON DELETE SET NULL,
    "song_id"           integer NOT NULL REFERENCES "Song" ("id") ON DELETE SET NULL,
    "character_id"      integer NOT NULL REFERENCES "Character" ("id") ON DELETE CASCADE,
    "experience_gained" integer NOT NULL,
    "gold_gained"       integer NOT NULL,
    "position"          integer NOT NULL
);

CREATE TABLE "Fight_Moves" (
    "move_number" integer NOT NULL,
    "fight_id" integer NOT NULL REFERENCES "Fight" ("id"),
    "attacker_id" integer NOT NULL REFERENCES "Fight_Participant" ("id"),
    "victim_id" integer REFERENCES "Fight_Participant" ("id"),
    "damage" integer NOT NULL,
    PRIMARY KEY ("move_number", "fight_id")
)