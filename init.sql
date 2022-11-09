CREATE TABLE "Location" (
  "id" serial PRIMARY KEY,
  "name" varchar(50) NOT NULL
);

CREATE TABLE "Song" (
  "id" serial PRIMARY KEY,
  "name" varchar(50) NOT NULL,
  "experience_level" integer NOT NULL,
  "hero_id" integer NOT NULL
);

CREATE TABLE "Note" (
  "id" serial PRIMARY KEY,
  "name" varchar(10) NOT NULL,
  "damage" integer NOT NULL
);

CREATE TABLE "Song_Note" (
  "song_id" integer NOT NULL REFERENCES "Song" ("id"),
  "note_id" integer NOT NULL REFERENCES "Note" ("id")
);

CREATE TABLE "User" (
  "id" serial PRIMARY KEY,
  "name" integer NOT NULL,
  "is_online" boolean NOT NULL
);

CREATE TABLE "Hero" (
  "id" serial PRIMARY KEY,
  "name" varchar(50) NOT NULL,
  "price" integer NOT NULL,
  "health" integer NOT NULL
);

CREATE TABLE "Effect" (
  "id" serial PRIMARY KEY,
  "name" varchar(30),
  "price" integer NOT NULL,
  "target" integer NOT NULL,
  "health" integer NOT NULL,
  "damage" integer NOT NULL
);

CREATE TABLE "Character" (
  "id" serial PRIMARY KEY,
  "experience" integer NOT NULL,
  "hero_id" integer NOT NULL REFERENCES "Hero" ("id"),
  "user_id" integer NOT NULL REFERENCES "User" ("id")
);

CREATE TABLE "Fight" (
  "id" serial PRIMARY KEY,
  "winner" integer REFERENCES "Character" ("id"),
  "location_id" integer REFERENCES "Location" ("id")
);

CREATE TABLE "Fight_Participant" (
  "id" serial PRIMARY KEY,
  "fight_id" integer NOT NULL REFERENCES "Fight" ("id"),
  "effect_id" integer NOT NULL REFERENCES "Effect" ("id"),
  "song_id" integer NOT NULL REFERENCES "Song" ("id"),
  "character_id" integer NOT NULL "Character" ("id"),
  "damage" integer NOT NULL,
  "is_alive" boolean NOT NULL
);