INSERT INTO "location" ("name")
VALUES ('Vienna'),
       ('Amsterdam'),
       ('Milan'),
       ('Tokyo'),
       ('Paris'),
       ('Salzburg'),
       ('Moscow');

INSERT INTO "hero" ("name", "price", "health")
VALUES ('Wolfgang Amadeus Mozart', 300, 100),
       ('Ludwig van Beethoven', 400, 125),
       ('Johann Sebastian Bach', 500, 150),
       ('Pyotr Ilyich Tchaikovsky', 600, 170),
       ('Frederic Chopin', 700, 200),
       ('Dora', 10000, 999999),
       ('Samuel Kim', 10, 200); -- jojo no music author

INSERT INTO "note" ("id", "name", "damage")
VALUES ('A', 10),
       ('B', 11),
       ('C', 11),
       ('D', 12),
       ('E', 12),
       ('F', 13),
       ('G', 13);

INSERT INTO "song" ("name", "experience_level", "hero_id")
VALUES ('Ballade No. 1 in G minor Op. 23', 100, 5),
       ('Moon Sonata', 76, 2),
       ('Toccata and Fugue', 96, 3),
       ('Requiem', 83, 1),
       ('Swan Lake', 65, 4),
       ('Dorafool', 0, 6),
       ('Fell In Love', 0, 6),
       ('Pink hair', 10, 6),
       ('Pillar Men Theme', 0, 7);

INSERT INTO "note_song" ("note_id", "song_id", "amount")
VALUES (1, 1, 10),
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

INSERT INTO "effect" ("name", "price", "stamina", "strength", "luck", "constitution")
VALUES ('Death 13', 50, 0, 4, 0, 1),
       ('Sticky fingers', 70, 4, 0, 1, 1),
       ('Lucky bastard', 65, 1, 0, 5, 0);

INSERT INTO "user" ("name", "is_online", "password")
VALUES ('Petya', true, 'qwety134'),
       ('Nastya', true, 'aboba2000');

INSERT INTO "character" ("experience", "hero_id", "user_id")
VALUES (0, 6, 1),
       (5, 3, 1),
       (60, 7, 2);