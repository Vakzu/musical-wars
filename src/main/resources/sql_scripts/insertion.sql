INSERT INTO "location" ("name")
VALUES ('Vienna'),
       ('Amsterdam'),
       ('Milan'),
       ('Tokyo'),
       ('Paris'),
       ('Salzburg'),
       ('Moscow');

INSERT INTO "hero" ("name", "price", "health", "img_path")
VALUES ('Wolfgang Amadeus Mozart', 300, 100, 'mozart.jpg'),
       ('Ludwig van Beethoven', 400, 125, 'bethoven.jpg'),
       ('Johann Sebastian Bach', 500, 150, 'bah.jpg'),
       ('Pyotr Ilyich Tchaikovsky', 600, 170, 'tchaikovsky.jpg'),
       ('Frederic Chopin', 700, 200, 'chopin.jpg'),
       ('Dora', 10000, 999999, 'dora.jpg'),
       ('Samuel Kim', 10, 200, 'samuel.jpg'); -- jojo no music author

INSERT INTO "song" ("name", "experience_level", "hero_id", "damage")
VALUES ('Ballade No. 1 in G minor Op. 23', 100, 5, 45),
       ('Moon Sonata', 76, 2, 38),
       ('Toccata and Fugue', 96, 3, 39),
       ('Requiem', 83, 1, 18),
       ('Swan Lake', 65, 4, 40),
       ('Dorafool', 0, 6, 41),
       ('Fell In Love', 0, 6, 43),
       ('Pink hair', 10, 6, 43),
       ('Pillar Men Theme', 0, 7, 43);

INSERT INTO "effect" ("name", "price", "stamina", "strength", "luck", "constitution")
VALUES ('Death 13', 50, 0, 4, 0, 1),
       ('Sticky fingers', 70, 4, 0, 1, 1),
       ('Lucky bastard', 65, 1, 0, 5, 0);

INSERT INTO "user" ("name", "is_online", "password_hash")
VALUES ('Petya', true, 'qwety134'),
       ('Nastya', true, 'aboba2000');

INSERT INTO "character" ("experience", "hero_id", "user_id")
VALUES (0, 6, 1),
       (5, 3, 1),
       (60, 7, 2);