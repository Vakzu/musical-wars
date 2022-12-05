CREATE OR REPLACE FUNCTION get_online_list() RETURNS SETOF integer AS $$
    SELECT "User"."id" FROM "User" WHERE "User"."is_online" = TRUE;
$$ LANGUAGE SQL;