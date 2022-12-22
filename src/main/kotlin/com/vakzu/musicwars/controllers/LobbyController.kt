package com.vakzu.musicwars.controllers

import com.vakzu.musicwars.dto.FightMoveResponse
import com.vakzu.musicwars.dto.SongDto
import com.vakzu.musicwars.dto.websocket.CommandType
import com.vakzu.musicwars.dto.websocket.OnlineMessage
import com.vakzu.musicwars.dto.websocket.ReadyResponse
import com.vakzu.musicwars.dto.websocket.SetReadyRequest
import com.vakzu.musicwars.lobby.LobbyService
import com.vakzu.musicwars.repos.SongRepository
import com.vakzu.musicwars.security.MyUserPrincipal
import com.vakzu.musicwars.services.FightService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/lobby")
class LobbyController(
    val lobbyService: LobbyService,
    val messagingTemplate: SimpMessagingTemplate,
    val fightService: FightService,
    val songRepository: SongRepository
) {

    @PostMapping("/lobby/create")
    fun createLobby(principal: Principal): String {
        val user = ((principal as UsernamePasswordAuthenticationToken).principal as MyUserPrincipal).user
        val uuid = lobbyService.createLobby(user)
        return uuid
    }

    @PostMapping("/lobby/join")
    fun acceptInvite(@RequestParam("lobbyId") lobbyId: String, principal: Principal) {
        val user = ((principal as UsernamePasswordAuthenticationToken).principal as MyUserPrincipal).user
        val lobby = lobbyService.getLobby(lobbyId)
        lobby.addParticipant(user)

        messagingTemplate.convertAndSend("/topic/lobby/$lobbyId", OnlineMessage(CommandType.JOIN, user.id, user.name))
    }

    @PostMapping("/lobby/leave")
    fun leaveLobby(@RequestParam("lobbyId") lobbyId: String, principal: Principal) {
        val user = ((principal as UsernamePasswordAuthenticationToken).principal as MyUserPrincipal).user
        val lobby = lobbyService.getLobby(lobbyId)
        lobby.removeParticipant(user)
        if (lobby.participants.isEmpty()) {
            lobbyService.lobbies.remove(lobby.lobbyId)
        }

        messagingTemplate.convertAndSend("/topic/lobby/$lobbyId", OnlineMessage(CommandType.LEAVE, user.id, user.name))
    }

    @PostMapping("/lobby/ready/set")
    @ResponseBody
    fun setReady(@RequestParam("lobbyId") lobbyId: String, @RequestBody(required = true) readyRequest: SetReadyRequest, principal: Principal) {
        val user = ((principal as UsernamePasswordAuthenticationToken).principal as MyUserPrincipal).user
        val lobby = lobbyService.getLobby(lobbyId)
        lobby.setReady(user, readyRequest)
        messagingTemplate.convertAndSend("/topic/lobby/$lobbyId", ReadyResponse(CommandType.SET_READY, user.id))
    }

    @PostMapping("/lobby/ready/cancel")
    @ResponseBody
    fun cancelReady(@RequestParam("lobbyId") lobbyId: String, principal: Principal) {
        val user = ((principal as UsernamePasswordAuthenticationToken).principal as MyUserPrincipal).user
        val lobby = lobbyService.getLobby(lobbyId)
        lobby.cancelReady(user)
        messagingTemplate.convertAndSend("/topic/lobby/$lobbyId", ReadyResponse(CommandType.CANCEL_READY, user.id))
    }

    @PostMapping("/war/start")
    fun beginFight(@RequestParam("lobbyId") lobbyId: String, @RequestParam locationId: Int): ResponseEntity<*> {
        val lobby = lobbyService.getLobby(lobbyId)
        lobby.locationId = locationId
        if (lobby.isEveryoneReady()) {
            val moves =  fightService.playFight(lobby)
            val fightMoves = moves.map { FightMoveResponse(it.moveNumber, it.fightId, it.attackerId, it.victimId, it.damage) }
            messagingTemplate.convertAndSend("/topic/lobby/$lobbyId", fightMoves)
            return ResponseEntity<Void>(HttpStatus.OK)
        }
        return ResponseEntity<Void>(HttpStatus.BAD_REQUEST)
    }

    @GetMapping("/character/songs")
    fun getAvailableSongs(@RequestParam("characterId") characterId: Int): List<SongDto> {
        val songs = songRepository.getCharacterAvailableSongs(characterId)
        return songs.map { SongDto(it.id, it.name, it.damage) }
    }
}