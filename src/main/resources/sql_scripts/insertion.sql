INSERT INTO "Location" ("id", "name")
VALUES (1, 'Vienna'),
       (2, 'Amsterdam'),
       (3, 'Milan'),
       (4, 'Tokyo'),
       (5, 'Paris'),
       (6, 'Salzburg'),
       (7, 'Moscow');

INSERT INTO "Hero" ("id", "name", "price", "health")
VALUES (1, 'Wolfgang Amadeus Mozart', 300, 100),
       (2, 'Ludwig van Beethoven', 400, 125),
       (3, 'Johann Sebastian Bach', 500, 150),
       (4, 'Pyotr Ilyich Tchaikovsky', 600, 170),
       (5, 'Frederic Chopin', 700, 200),
       (6, 'Dora', 10000, 999999),
       (7, 'Samuel Kim', 10, 200); -- jojo no music author

INSERT INTO "Note" ("id", "name", "damage")
VALUES (1, 'A', 10),
       (2, 'B', 11),
       (3, 'C', 11),
       (4, 'D', 12),
       (5, 'E', 12),
       (6, 'F', 13),
       (7, 'G', 13);

INSERT INTO "Song" ("id", "name", "experience_level", "hero_id")
VALUES (1, 'Ballade No. 1 in G minor Op. 23', 100, 5),
       (2, 'Moon Sonata', 76, 2),
       (3, 'Toccata and Fugue', 96, 3),
       (4, 'Requiem', 83, 1),
       (5, 'Swan Lake', 65, 4),
       (6, 'Dorafool', 0, 6),
       (7, 'Fell In Love', 0, 6),
       (8, 'Pink hair', 10, 6),
       (9, 'Pillar Men Theme', 0, 7);

INSERT INTO "Note_Song" ("note_id", "song_id", "amount")
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

INSERT INTO "Effect" ("id", "name", "price", "stamina", "strength", "luck", "constitution")
VALUES (1, 'Death 13', 50, 0, 4, 0, 1),
       (2, 'Sticky fingers', 70, 4, 0, 1, 1),
       (3, 'Lucky bastard', 65, 1, 0, 5, 0);

INSERT INTO "User" ("id", "name", "is_online", "password")
VALUES (1, 'Petya', true, 'qwety134'),
       (2, 'Nastya', true, 'aboba2000');

INSERT INTO "Character" ("id", "experience", "hero_id", "user_id")
VALUES (1, 0, 6, 1),
       (2, 5, 3, 1),
       (3, 60, 7, 2);