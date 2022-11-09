CREATE TABLE "Location" (
  "id" serial PRIMARY KEY,
  "name" varchar(50) NOT NULL
);

CREATE TABLE "Song" (
  "id" serial PRIMARY KEY,
  "name" varchar(50) NOT NULL,
  "experience_level" integer NOT NULL CHECK("experience_level" >= 0),
  "hero_id" integer NOT NULL REFERENCES "Hero" ("id") ON DELETE CASCADE
);

CREATE TABLE "Note" (
  "id" serial PRIMARY KEY,
  "name" varchar(10) NOT NULL,
  "damage" integer NOT NULL CHECK("damage" >= 0),
);

CREATE TABLE "Note_Song" (
  "note_id" integer NOT NULL REFERENCES "Note" ("id"),
  "song_id" integer NOT NULL REFERENCES "Song" ("id"),
  "amount" integer NOT NULL
);

CREATE TABLE "User" (
  "id" serial PRIMARY KEY,
  "name" integer NOT NULL,
  "is_online" boolean NOT NULL,
  "balance" integer NOT NULL CHECK("balance" >= 0) DEFAULT 1000
);

CREATE TABLE "Hero" (
  "id" serial PRIMARY KEY,
  "name" varchar(50) NOT NULL,
  "price" integer NOT NULL CHECK("price" >= 0),
  "health" integer NOT NULL CHECK("health" > 0)
);

CREATE TABLE "Effect" (
  "id" serial PRIMARY KEY,
  "name" varchar(30),
  "price" integer NOT NULL CHECK("price" >= 0),
  "target" integer NOT NULL CHECK("target" = 0 OR "target" = 1),
  "health" integer NOT NULL CHECK("health" >= 0),
  "damage" integer NOT NULL CHECK("damage" >= 0)
);

CREATE TABLE "Character" (
  "id" serial PRIMARY KEY,
  "experience" integer NOT NULL CHECK("experience" >= 0),
  "hero_id" integer NOT NULL REFERENCES "Hero" ("id") ON DELETE CASCADE,
  "user_id" integer NOT NULL REFERENCES "User" ("id") ON DELETE CASCADE
);  

CREATE TABLE "Fight" (
  "id" serial PRIMARY KEY,
  "winner" integer REFERENCES "Character" ("id") ON DELETE SET NULL,
  "location_id" integer REFERENCES "Location" ("id") ON DELETE SET NULL
);

CREATE TABLE "Fight_Participant" (
  "id" serial PRIMARY KEY,
  "fight_id" integer NOT NULL REFERENCES "Fight" ("id") ON DELETE CASCADE,
  "effect_id" integer NOT NULL REFERENCES "Effect" ("id") ON DELETE SET NULL,
  "song_id" integer NOT NULL REFERENCES "Song" ("id") ON DELETE SET NULL,
  "character_id" integer NOT NULL REFERENCES "Character" ("id") ON DELETE CASCADE,
  "damage" integer NOT NULL CHECK("damage" >= 0),
  "is_alive" boolean NOT NULL
);