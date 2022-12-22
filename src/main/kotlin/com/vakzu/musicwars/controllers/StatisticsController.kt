package com.vakzu.musicwars.controllers

import com.vakzu.musicwars.dto.StatisticsDto
import com.vakzu.musicwars.security.MyUserPrincipal
import com.vakzu.musicwars.security.UserService
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/stats")
class StatisticsController(val userService: UserService) {

    @GetMapping
    fun getStatistics(principal: Principal, model: Model): StatisticsDto {
        val user = ((principal as UsernamePasswordAuthenticationToken).principal as MyUserPrincipal).user
        val statistics = userService.getUserStatistics(user.id)
        return StatisticsDto(
            statistics.getGamesCount(),
            statistics.getWinsCount(),
            statistics.getAveragePlace(),
            statistics.getLastGameDate()
        )
    }

}