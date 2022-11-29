package com.vakzu.musicwars.lobby

import com.vakzu.musicwars.dto.LobbyInfo
import com.vakzu.musicwars.dto.UserDTO
import com.vakzu.musicwars.exceptions.LobbyNotFoundException
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class LobbyService(val onlineUsersService: OnlineUsersService) {

    val lobbies = HashMap<String, Lobby>()

    fun createLobby(userDTO: UserDTO): String {
        val uuid = UUID.randomUUID().toString()
        val lobby = Lobby(uuid, userDTO.userId)
        lobby.addParticipant(userDTO)
        lobbies[uuid] = lobby
        return uuid
    }

    fun changeHost(lobbyId: String, userId: Int) {
        getLobby(lobbyId).hostId = userId
    }

    private fun getLobby(id: String) = lobbies[id] ?: throw LobbyNotFoundException(id)

    fun getLobbyUsers(id: String): List<UserDTO> {
        val lobby = getLobby(id)
        return lobby.participants.values.toList()
    }

    fun getLobbyInfo(lobbyId: String): LobbyInfo {
        val lobby = getLobby(lobbyId)
        return LobbyInfo(lobbyId, onlineUsersService.getUsersOnline(), getLobbyUsers(lobbyId), lobby.hostId)
    }
}