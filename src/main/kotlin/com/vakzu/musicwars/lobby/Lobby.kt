package com.vakzu.musicwars.lobby

import com.vakzu.musicwars.dto.UserDTO

class Lobby(val lobbyId: String, var hostId: Int) {
    val participants: MutableMap<Int, UserDTO> = HashMap()

    fun addParticipant(user: UserDTO) {
        participants[user.userId] = user
    }

    fun removeParticipant(user: UserDTO) {
        participants.remove(user.userId)
    }
}