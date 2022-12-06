package com.vakzu.musicwars.exceptions

class LobbyNotFoundException(id: String): RuntimeException("Lobby with id $id not found")