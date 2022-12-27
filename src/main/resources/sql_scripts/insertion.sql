INSERT INTO "location" ("name")
VALUES ('Vienna'),
       ('Amsterdam'),
       ('Milan'),
       ('Tokyo'),
       ('Paris'),
       ('Salzburg'),
       ('Moscow'),
       ('Uryupinsk');

INSERT INTO "hero" ("name", "price", "health", "img_path")
VALUES ('Wolfgang Amadeus Mozart', 300, 100, 'mozart.jpg'),
       ('Ludwig van Beethoven', 400, 125, 'bethoven.jpg'),
       ('Johann Sebastian Bach', 500, 150, 'bah.jpg'),
       ('Pyotr Ilyich Tchaikovsky', 600, 170, 'tchaikovsky.jpg'),
       ('Frederic Chopin', 700, 200, 'chopin.jpg'),
       ('Dora', 10000, 999999, 'dora.jpg'),
       ('Neuro girl', 500, 148, 'generation.png'),
       ('Ultra cow', 1000, 202, 'cow.jpg');


INSERT INTO "song" ("name", "experience_level", "hero_id", "damage")
VALUES ('Ballade No. 1 in G minor Op. 23', 100, 5, 45),
       ('Moon Sonata', 76, 2, 38),
       ('Toccata and Fugue', 96, 3, 39),
       ('Requiem', 83, 1, 18),
       ('Swan Lake', 65, 4, 40),
       ('Dorafool', 0, 6, 41),
       ('Fell In Love', 0, 6, 43),
       ('Pink hair', 10, 6, 43),
       ('Different', 0, 7, 38),
       ('Dead man"ns bones', 0, 8, 35),
       ('Say what you like', 0, 7, 26),
       ('Never say goodbye', 0, 8, 42);

INSERT INTO "effect" ("name", "price", "stamina", "strength", "luck", "constitution")
VALUES ('Death 13', 50, 0, 4, 0, 1),
       ('Sticky fingers', 70, 4, 0, 1, 1),
       ('Lucky bastard', 65, 1, 0, 5, 0),
       ('Walking corpse', 60, 0, 0, 0, 5),
       ('Can you kill me?', 55, 0, 0, 3, 3);
