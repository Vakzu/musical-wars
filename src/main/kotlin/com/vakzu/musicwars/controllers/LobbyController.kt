package com.vakzu.musicwars.controllers

import com.vakzu.musicwars.dto.FightMoveResponse
import com.vakzu.musicwars.dto.LobbyUserDto
import com.vakzu.musicwars.dto.websocket.CommandType
import com.vakzu.musicwars.dto.websocket.OnlineMessage
import com.vakzu.musicwars.dto.websocket.ReadyResponse
import com.vakzu.musicwars.dto.websocket.SetReadyRequest
import com.vakzu.musicwars.lobby.LobbyService
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
    val fightService: FightService
) {

    @PostMapping("/create")
    fun createLobby(principal: Principal): String {
        val user = ((principal as UsernamePasswordAuthenticationToken).principal as MyUserPrincipal).user
        val uuid = lobbyService.createLobby(user)
        return uuid
    }

    @PostMapping("/join")
    fun acceptInvite(@RequestParam("lobbyId") lobbyId: String, principal: Principal) {
        val user = ((principal as UsernamePasswordAuthenticationToken).principal as MyUserPrincipal).user
        val lobby = lobbyService.getLobby(lobbyId)
        lobby.addParticipant(user)

        messagingTemplate.convertAndSend("/topic/lobby/$lobbyId/changeMembers", OnlineMessage(CommandType.JOIN, user.id, user.name))
    }

    @PostMapping("/leave")
    fun leaveLobby(@RequestParam("lobbyId") lobbyId: String, principal: Principal) {
        val user = ((principal as UsernamePasswordAuthenticationToken).principal as MyUserPrincipal).user
        val lobby = lobbyService.getLobby(lobbyId)
        lobby.removeParticipant(user)
        if (lobby.participants.isEmpty()) {
            lobbyService.lobbies.remove(lobby.lobbyId)
        }

        messagingTemplate.convertAndSend("/topic/lobby/$lobbyId/changeMembers", OnlineMessage(CommandType.LEAVE, user.id, user.name))
    }

    @PostMapping("/ready/set")
    @ResponseBody
    fun setReady(@RequestParam("lobbyId") lobbyId: String, @RequestBody(required = true) readyRequest: SetReadyRequest, principal: Principal) {
        val user = ((principal as UsernamePasswordAuthenticationToken).principal as MyUserPrincipal).user
        val lobby = lobbyService.getLobby(lobbyId)
        lobby.setReady(user, readyRequest)
        messagingTemplate.convertAndSend("/topic/lobby/$lobbyId/changeReady", ReadyResponse(CommandType.SET_READY, user.id))
    }

    @PostMapping("/ready/cancel")
    @ResponseBody
    fun cancelReady(@RequestParam("lobbyId") lobbyId: String, principal: Principal) {
        val user = ((principal as UsernamePasswordAuthenticationToken).principal as MyUserPrincipal).user
        val lobby = lobbyService.getLobby(lobbyId)
        lobby.cancelReady(user)
        messagingTemplate.convertAndSend("/topic/lobby/$lobbyId/changeReady", ReadyResponse(CommandType.CANCEL_READY, user.id))
    }

    @PostMapping("/start")
    fun beginFight(@RequestParam("lobbyId") lobbyId: String, @RequestParam locationId: Int): ResponseEntity<*> {
        val lobby = lobbyService.getLobby(lobbyId)
        lobby.locationId = locationId
        if (lobby.isEveryoneReady()) {
            val moves =  fightService.playFight(lobby)
            val fightMoves = moves.map { FightMoveResponse(it.moveNumber, it.fightId, it.attackerId, it.victimId, it.damage) }
            messagingTemplate.convertAndSend("/topic/lobby/$lobbyId/startFight", fightMoves)
            return ResponseEntity<Void>(HttpStatus.OK)
        }
        return ResponseEntity<Void>(HttpStatus.BAD_REQUEST)
    }

    @GetMapping("/users")
    fun getLobbyUsers(@RequestParam("lobbyId") lobbyId: String): List<LobbyUserDto> {
        val lobby = lobbyService.getLobby(lobbyId)
        return lobby.participants.map { LobbyUserDto(it.key, it.value.username, it.value.ready) }
    }

    @GetMapping("/status")
    fun getLobbyStatus(@RequestParam("lobbyId") lobbyId: String): Int {
        val lobby = lobbyService.getLobby(lobbyId)
        return lobby.hostId
    }
}