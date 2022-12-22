package com.vakzu.musicwars.controllers

import com.vakzu.musicwars.dto.SongDto
import com.vakzu.musicwars.repos.SongRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class SongController(val songRepository: SongRepository) {

    @GetMapping("/character/songs")
    fun getAvailableSongs(@RequestParam("characterId") characterId: Int): List<SongDto> {
        val songs = songRepository.getCharacterAvailableSongs(characterId)
        return songs.map { SongDto(it.id, it.name, it.damage) }
    }

}