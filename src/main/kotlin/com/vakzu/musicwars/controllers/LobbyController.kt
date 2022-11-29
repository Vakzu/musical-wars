package com.vakzu.musicwars.controllers

import com.vakzu.musicwars.dto.LobbyInfo
import com.vakzu.musicwars.dto.LobbyResponse
import com.vakzu.musicwars.dto.UserDTO
import com.vakzu.musicwars.exceptions.LobbyNotFoundException
import com.vakzu.musicwars.lobby.LobbyService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
class LobbyController(val lobbyService: LobbyService) {

    @PostMapping("/lobby/create")
    fun createLobby(@RequestBody req: UserDTO): LobbyResponse {
        val uuid = lobbyService.createLobby(req)
        return LobbyResponse(uuid, req.userId)
    }

    @GetMapping("/lobby/{id}/info")
    fun getLobbyInfo(@PathVariable id: String): LobbyInfo {
        return lobbyService.getLobbyInfo(id)
    }

    @ExceptionHandler(LobbyNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun lobbyNotFound(e: LobbyNotFoundException): String? {
        return e.message
    }

}