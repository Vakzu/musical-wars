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

INSERT INTO "Location" VALUES
(1, 'Viena'),
(2, 'Amsterdam'),
(3, 'Milasn'),
(4, 'Tokyo'),
(5, 'Paris'),
(6, 'Salzburg'),
(7, 'Moscow');

INSERT INTO "Hero" VALUES 
(1, 'Wolfgang Amadeus Mozart', 300, 100)
(2, 'Ludwig van Beethoven', 400, 125)
(3, 'Johann Sebastian Bach', 500, 150)
(4, 'Pyotr Ilyich Tchaikovsky', 600, 175)
(5, 'Frederic Chopin', 700, 200),
(6, 'Dora', 10000, 999999),
(7, 'Samuel Kim', 10, 200);

INSERT INTO "Note" VALUES
(1, 'A', 10),
(2, 'B', 11),
(3, 'C', 11),
(4, 'D', 12),
(5, 'E', 12),
(6, 'F', 13),
(7, 'G', 13);

INSERT INTO "Song" VALUES
(1, 'Ballade No. 1 in G minor Op. 23', 100, 5),
(2, 'Moon Sonata', 76, 2),
(3, 'Toccata and Fugue', 96, 3),
(4, 'Requiem', 83, 1),
(5, 'Swan Lake', 65, 4),
(6, 'Dorafool', 0, 6),
(7, 'Fell In Love', 0, 6),
(8, 'Pink hair', 10, 6),
(9, 'Pillar Men Theme', 0, 7);

INSERT INTO "Note_Song" VALUES
(1, 1, 10),
(1, 2, 12),
(1, 4, 23),
(1, 9, 34),
(2, 5, 12),
(2, 7, 17),
(3, 4, 18),
(3, 8, 16),
(3, 1, 28),
(4, 7, 15),
(4, 9, 23),
(4, 6, 20),
(4, 3, 9),
(5, 2, 8),
(5, 7, 6),
(5, 3, 15),
(6, 6, 6),
(6, 7, 1),
(6, 5, 19),
(7, 1, 5),
(7, 2, 14),
(7, 3, 15),
(7, 4, 21),
(7, 8, 17);

INSERT INTO "Effect" VALUES 
(1, 'Strength', 10, 0, 10, 0),
(2, 'Wheel of fortune', 10, 1, 0, 10);

INSERT INTO "User" VALUES
(1, "Vasaya", FALSE, 1000),
(2, "Joshua", TRUE, 1000),
(3, "Michael", TRUE, 1000);
