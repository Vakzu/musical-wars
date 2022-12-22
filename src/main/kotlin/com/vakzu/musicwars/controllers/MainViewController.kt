package com.vakzu.musicwars.controllers

import com.vakzu.musicwars.dto.*
import com.vakzu.musicwars.lobby.LobbyService
import com.vakzu.musicwars.repos.CharacterRepository
import com.vakzu.musicwars.repos.EffectRepository
import com.vakzu.musicwars.repos.SongRepository
import com.vakzu.musicwars.security.MyUserPrincipal
import com.vakzu.musicwars.security.UserService
import com.vakzu.musicwars.services.FightService
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import java.security.Principal

@Controller
class MainViewController(
    val userService: UserService,
    val lobbyService: LobbyService,
    val effectRepository: EffectRepository,
    val characterRepository: CharacterRepository,
    val songRepository: SongRepository
) {

    @GetMapping("/")
    fun mainPage(principal: Principal, model: Model): String {
        val user = userService.findByName(principal.name)!!
        val shop = userService.getShopUserInfo(user.id)
        val effectShop = effectRepository.findEffectShopInfoByUserId(user.id)
        val root = HashMap<String, Any>()
        root["user"] = user
        root["heroes"] = shop
        root["effects"] = effectShop
        model.addAllAttributes(root)
        return "main"
    }

    @GetMapping("/war/{lobbyId}")
    fun getMaster(model: Model, @PathVariable lobbyId: String, principal: Principal): String {
        val user = ((principal as UsernamePasswordAuthenticationToken).principal as MyUserPrincipal).user
        val onlineUsers = userService.getOnlineUsers()
        val lobbyUsers = lobbyService.getLobbyUsers(lobbyId)
        val effects = effectRepository.findEffectByUserId(user.id)
        val characters = characterRepository.findAllByUserId(user.id).map { CharacterDto(it.id, it.hero.name, it.hero.health) }
        val songs: List<SongDto> = if (characters.isNotEmpty()) {
            songRepository.getCharacterAvailableSongs(characters[0].id!!).map { SongDto(it.id, it.name, it.damage) }
        }  else {
            emptyList()
        }
        val isHost = lobbyService.getLobby(lobbyId).hostId == user.id
        val root = HashMap<String, Any>()
        root["user"] = user
        root["onlineUsers"] = onlineUsers
        root["lobbyUsers"] = lobbyUsers
        root["lobbyId"] = lobbyId
        root["effects"] = effects
        root["heroes"] = characters
        root["songs"] = songs
        root["isHost"] = isHost
        model.addAllAttributes(root)
        return "game_page"
    }


}