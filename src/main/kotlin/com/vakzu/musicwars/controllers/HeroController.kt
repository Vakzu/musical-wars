package com.vakzu.musicwars.controllers

import com.vakzu.musicwars.dto.AllHeroResponse
import com.vakzu.musicwars.dto.HeroDto
import com.vakzu.musicwars.security.MyUserPrincipal
import com.vakzu.musicwars.security.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/hero")
class HeroController(val userService: UserService) {

    @GetMapping("/all")
    @ResponseBody
    fun getAllHeroes(principal: Principal): AllHeroResponse {
        val user = ((principal as UsernamePasswordAuthenticationToken).principal as MyUserPrincipal).user
        val heroes = userService.getShopUserInfo(user.id)
        val result = heroes.map { HeroDto(it.getHero_id(), it.getName(), it.getHealth(), it.getPrice(), it.getImg_path()) }
        return AllHeroResponse(result)
    }

    @PostMapping("/buy")
    fun buyHero(@RequestParam("heroId") heroId: Int, principal: Principal): ResponseEntity<*> {
        val user = ((principal as UsernamePasswordAuthenticationToken).principal as MyUserPrincipal).user
        val result = userService.buyHero(user.id, heroId)
        return ResponseEntity<Void>(if (result) HttpStatus.OK else HttpStatus.BAD_REQUEST)
    }

}