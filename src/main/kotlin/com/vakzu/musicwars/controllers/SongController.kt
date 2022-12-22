package com.vakzu.musicwars.controllers

import com.vakzu.musicwars.dto.CharacterDto
import com.vakzu.musicwars.dto.SongDto
import com.vakzu.musicwars.repos.CharacterRepository
import com.vakzu.musicwars.repos.LocationRepository
import com.vakzu.musicwars.repos.SongRepository
import com.vakzu.musicwars.security.MyUserPrincipal
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
class SongController(val songRepository: SongRepository,
                     val characterRepository: CharacterRepository,
                     val locationRepository: LocationRepository
) {

    @GetMapping("/character/songs")
    fun getAvailableSongs(@RequestParam("characterId") characterId: Int): List<SongDto> {
        val songs = songRepository.getCharacterAvailableSongs(characterId)
        return songs.map { SongDto(it.id, it.name, it.damage) }
    }

    @GetMapping("/character/all")
    fun getUserCharacter(principal: Principal): List<CharacterDto> {
        val user = ((principal as UsernamePasswordAuthenticationToken).principal as MyUserPrincipal).user
        return characterRepository.findAllByUserId(user.id).map { CharacterDto(it.id, it.hero.name, it.hero.health) }
    }

    @GetMapping("/locations/all")
    fun getAllLocations(): List<Map<Int, String>> {
        return locationRepository.findAll().map { mapOf(it.id to it.name) }
    }
}