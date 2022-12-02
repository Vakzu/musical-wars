package com.vakzu.musicwars.controllers

import com.vakzu.musicwars.OnlineMessage
import com.vakzu.musicwars.dto.RegisterRequest
import com.vakzu.musicwars.dto.websocket.CommandType
import com.vakzu.musicwars.lobby.LobbyService
import com.vakzu.musicwars.security.MyUserPrincipal
import com.vakzu.musicwars.security.UserService
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import java.security.Principal

@Controller
class MainViewController(
    val messagingTemplate: SimpMessagingTemplate,
    val userService: UserService,
    val lobbyService: LobbyService
) {

    @GetMapping("/")
    fun mainPage(principal: Principal, model: Model): String {
        val user = ((principal as UsernamePasswordAuthenticationToken).principal as MyUserPrincipal).user
        val root = HashMap<String, Any>()
        root["user"] = user
        model.addAllAttributes(root)
        return "main"
    }


    @GetMapping("/register")
    fun registerPage(): String = "register"

    @PostMapping("/register")
    fun registerUser(@ModelAttribute("userForm") form: RegisterRequest, model: Model): String {
        userService.registerUser(form.username, form.password)
        return "redirect:/login"
    }

    @GetMapping("/login")
    fun loginPage(): String = "login"

    @GetMapping("/war/{lobbyId}")
    fun getMaster(model: Model, @PathVariable lobbyId: String, principal: Principal): String {
        val user = ((principal as UsernamePasswordAuthenticationToken).principal as MyUserPrincipal).user
        val onlineUsers = userService.getOnlineUsers()
        val lobbyUsers = lobbyService.getLobbyUsers(lobbyId)
        val root = HashMap<String, Any>()
        root["user"] = user
        root["onlineUsers"] = onlineUsers
        root["lobbyUsers"] = lobbyUsers
        root["lobbyId"] = lobbyId
        model.addAllAttributes(root)
        return "game_page"
    }

    @GetMapping("/lobby/create")
    fun createLobby(principal: Principal): String {
        val user = ((principal as UsernamePasswordAuthenticationToken).principal as MyUserPrincipal).user
        val uuid = lobbyService.createLobby(user)
        return "redirect:/war/$uuid"
    }

    @PostMapping("/lobby/join")
    fun acceptInvite(@RequestParam("lobbyId") lobbyId: String, principal: Principal): String {
        val user = ((principal as UsernamePasswordAuthenticationToken).principal as MyUserPrincipal).user
        val lobby = lobbyService.getLobby(lobbyId)
        lobby.addParticipant(user)

        messagingTemplate.convertAndSend("/topic/lobby/$lobbyId", OnlineMessage(CommandType.JOIN, user.id, user.name))

        return "redirect:/war/${lobbyId}"
    }

    @PostMapping("/lobby/leave")
    fun leaveLobby(@RequestParam("lobbyId") lobbyId: String, principal: Principal): String {
        val user = ((principal as UsernamePasswordAuthenticationToken).principal as MyUserPrincipal).user
        val lobby = lobbyService.getLobby(lobbyId)
        lobby.removeParticipant(user)
        if (lobby.participants.isEmpty()) {
            lobbyService.lobbies.remove(lobby.lobbyId)
        }

        messagingTemplate.convertAndSend("/topic/lobby/$lobbyId", OnlineMessage(CommandType.LEAVE, user.id, user.name))

        return "redirect:/"
    }

}