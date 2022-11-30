package com.vakzu.musicwars.exceptions

class UserAlreadyExistsException(name: String): RuntimeException("User $name already exists")