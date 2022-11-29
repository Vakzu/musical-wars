package com.vakzu.musicwars.dto

data class LobbyInfo(
    val lobbyId: String,
    val onlineUsers: List<UserDTO>,
    val lobbyUsers: List<UserDTO>,
    val hostId: Int)