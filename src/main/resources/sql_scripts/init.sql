CREATE TABLE "Location"
(
    "id"   serial PRIMARY KEY,
    "name" varchar(50) NOT NULL
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
    "name"      integer      NOT NULL,
    "is_online" boolean      NOT NULL,
    "balance"   integer      NOT NULL CHECK ("balance" >= 0) DEFAULT 1000,
    "passwrod"  varchar(255) NOT NULL
);

CREATE TABLE "Hero"
(
    "id"     serial PRIMARY KEY,
    "name"   varchar(50) NOT NULL,
    "price"  integer     NOT NULL CHECK ("price" >= 0),
    "health" integer     NOT NULL CHECK ("health" > 0)
);

CREATE TABLE "EffectType"
(
    "name" varchar(30) NOT NULL
);

CREATE TABLE "Multi_Effect"
(
    "id"    serial PRIMARY KEY,
    "name"  varchar(50),
    "price" integer NOT NULL CHECK ("price" >= 0)
);

CREATE TABLE "Multieffect_Map"
(
    "effect_type"    varchar(30) REFERENCES "EffectType" ("name") ON DELETE CASCADE,
    "multieffect_id" integer REFERENCES "Multi_Effect" ("id") ON DELETE CASCADE,
    "effect_val"     integer NOT NULL
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
    "location_id" integer REFERENCES "Location" ("id") ON DELETE SET NULL
);

CREATE TABLE "Fight_Participant"
(
    "id"                serial PRIMARY KEY,
    "fight_id"          integer NOT NULL REFERENCES "Fight" ("id") ON DELETE CASCADE,
    "effect_id"         integer NOT NULL REFERENCES "Multi_Effect" ("id") ON DELETE SET NULL,
    "song_id"           integer NOT NULL REFERENCES "Song" ("id") ON DELETE SET NULL,
    "character_id"      integer NOT NULL REFERENCES "Character" ("id") ON DELETE CASCADE,
    "experience_gained" integer NOT NULL,
    "gold_gained"       integer NOT NULL
);