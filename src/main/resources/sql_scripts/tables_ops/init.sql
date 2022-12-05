CREATE TABLE "Location" (
    "id"               serial PRIMARY KEY,
    "name"             varchar(50) NOT NULL
);

CREATE TABLE "Fight" (
    "id"               serial PRIMARY KEY,
    "start"            timestamp NOT NULL,
    "location_id"      integer REFERENCES "Location" ("id") ON DELETE SET NULL
);

CREATE TABLE "Song" (
    "id"               serial PRIMARY KEY,
    "name"             varchar(50) NOT NULL,
    "experience_level" integer NOT NULL CHECK ("experience_level" >= 0),
    "hero_id"          integer     NOT NULL REFERENCES "Hero" ("id") ON DELETE CASCADE,
    "damage"           integer NOT NULL CHECK ("damage" >= 0)
);

CREATE TABLE "Effect" (
    "id"               serial PRIMARY KEY,
    "name"             varchar(30) NOT NULL,
    "price"            integer NOT NULL,
    "stamina"          integer NOT NULL,
    "strength"         integer NOT NULL,
    "luck"             integer NOT NULL,
    "constitution"     integer NOT NULL
);

CREATE TABLE "Hero" (
    "id"               serial PRIMARY KEY,
    "name"             varchar(50) NOT NULL,
    "price"            integer NOT NULL,
    "health"           integer NOT NULL,
    "strength"         integer NOT NULL,
    "stamina"          integer NOT NULL,
    "luck"             integer NOT NULL
)

CREATE TABLE "Character" (
    "id"                serial PRIMARY KEY,
    "experience"        integer NOT NULL,
    "hero_id"           integer NOT NULL REFERENCES "Hero" ("id") ON DELETE CASCADE,
    "user_id"           integer NOT NULL REFERENCES "User" ("id") ON DELETE CASCADE
)

CREATE TABLE "User" (
    "id"                serial PRIMARY KEY,
    "name"              varchar(255) NOT NULL,
    "is_online"         boolean NOT NULL,
    "password_hash"     varchar(255) NOT NULL
) 

CREATE TABLE "Fight_Participant" (
    "id"                serial PRIMARY KEY,
    "fight_id"          integer NOT NULL REFERENCES "Fight" ("id") ON DELETE CASCADE,
    "song_id"           integer NOT NULL REFERENCES "Song" ("id") ON DELETE SET NULL,
    "effect_id"         integer NOT NULL REFERENCES "Multi_Effect" ("id") ON DELETE SET NULL,
    "character_id"      integer NOT NULL REFERENCES "Character" ("id") ON DELETE CASCADE,
    "experience_gained" integer NOT NULL,
    "gold_gained"       integer NOT NULL
);