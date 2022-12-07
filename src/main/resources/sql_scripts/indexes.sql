CREATE INDEX ON "user" using hash("name");

CREATE INDEX ON "character" using hash("id");

CREATE INDEX ON "character" using hash("user_id");

CREATE INDEX ON "hero" using hash("id");

CREATE INDEX ON "effect" using hash("id");

CREATE INDEX ON "inventory" using hash("user_id");

CREATE INDEX ON "inventory" using hash("effect_id");