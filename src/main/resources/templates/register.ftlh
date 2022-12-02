<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
</head>
<body>
<a href="/login">Login</a>
<form method="post" action="/register">
    <label for="username">Username: </label>
    <input id="username" name="username" />
    <br/>
    <label for="passwd">Password: </label>
    <input id="passwd" name="password" />

    <label for="repeat-passwd">Repeat password: </label>
    <input id="repeat-passwd" name="confirmPassword"/>
    <button type="submit">Login</button>
</form>
</body>
</html>