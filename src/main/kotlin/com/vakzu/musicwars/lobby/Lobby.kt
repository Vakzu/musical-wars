package com.vakzu.musicwars.lobby

import com.vakzu.musicwars.dto.SetReadyRequest
import com.vakzu.musicwars.entities.User

class Lobby(val lobbyId: String, var hostId: Int) {

    val participants: MutableMap<Int, LobbyUser> = HashMap()

    fun addParticipant(user: User) {
        participants[user.id] = LobbyUser(user.id, user.name, false, 0, 0, 0)
    }

    fun removeParticipant(user: User) {
        participants.remove(user.id)
    }

    data class LobbyUser(val userId: Int,
                         val username: String,
                         var ready: Boolean,
                         var characterId: Int,
                         var songId: Int,
                         var effectId: Int)

    fun setReady(user: User, readyRequest: SetReadyRequest) {
        val lobbyUser = participants[user.id] ?: throw IllegalArgumentException("User is not in lobby")
        participants[user.id] = LobbyUser(lobbyUser.userId, lobbyUser.username, true, readyRequest.characterId, readyRequest.songId, readyRequest.effectId)
    }

    fun cancelReady(user: User) {
        val lobbyUser = participants[user.id] ?: throw IllegalArgumentException("User is not in lobby")
        participants[user.id]?.ready = false
    }
}