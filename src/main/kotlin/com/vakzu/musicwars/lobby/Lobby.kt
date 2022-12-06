package com.vakzu.musicwars.lobby

import com.vakzu.musicwars.entities.User

class Lobby(val lobbyId: String, var hostId: Int) {

    val participants: MutableMap<Int, LobbyUser> = HashMap()

    fun addParticipant(user: User) {
        participants[user.id] = LobbyUser(user.id, user.name, false)
    }

    fun removeParticipant(user: User) {
        participants.remove(user.id)
    }

    class LobbyUser(val userId: Int, val username: String, val ready: Boolean)
}