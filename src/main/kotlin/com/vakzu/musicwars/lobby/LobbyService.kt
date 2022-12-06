package com.vakzu.musicwars.lobby

import com.vakzu.musicwars.entities.User
import com.vakzu.musicwars.exceptions.LobbyNotFoundException
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class LobbyService {

    val lobbies = HashMap<String, Lobby>()

    fun createLobby(user: User): String {
        val uuid = UUID.randomUUID().toString()
        val lobby = Lobby(uuid, user.id)
        lobby.addParticipant(user)
        lobbies[uuid] = lobby
        return uuid
    }

    fun changeHost(lobbyId: String, userId: Int) {
        getLobby(lobbyId).hostId = userId
    }

    fun getLobby(id: String) = lobbies[id] ?: throw LobbyNotFoundException(id)

    fun getLobbyUsers(id: String): List<Lobby.LobbyUser> {
        val lobby = getLobby(id)
        return lobby.participants.values.toList()
    }
}