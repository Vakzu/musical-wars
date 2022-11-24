CREATE OR REPLACE FUNCTION reduce_user_balance() 
RETURNS TRIGGER AS
$$
BEGIN
    IF ((SELECT "price" FROM "Hero" WHERE "id" = NEW."hero_id") >
        (SELECT "balance" FROM "User" WHERE "id" = NEW."user_id")) THEN
        RETURN NULL;
    END IF;

    UPDATE "User" 
    SET "balance" = "balance" - ( SELECT "price" FROM "Hero" WHERE "id" = NEW."hero_id" )
    WHERE "id" = NEW."user_id";
END;
$$
LANGUAGE 'plpgsql';

CREATE TRIGGER "buy_hero_trigger" BEFORE INSERT ON "Character"
FOR EACH STATEMENT 
EXECUTE FUNCTION reduce_user_balance();