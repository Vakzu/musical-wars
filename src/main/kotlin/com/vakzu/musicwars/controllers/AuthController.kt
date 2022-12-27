package com.vakzu.musicwars.controllers

import com.vakzu.musicwars.dto.LoginDto
import com.vakzu.musicwars.dto.OnlineUserDto
import com.vakzu.musicwars.dto.RegisterRequest
import com.vakzu.musicwars.exceptions.UserAlreadyExistsException
import com.vakzu.musicwars.security.MyUserPrincipal
import com.vakzu.musicwars.security.UserService
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
class AuthController(val userService: UserService) {

    @PostMapping("/register")
    fun registerUser(@RequestBody(required = true) form: RegisterRequest): LoginDto {
        val userId = userService.registerUser(form.username, form.password)
        return LoginDto(form.username, userId)
    }

    @GetMapping("/user/balance")
    fun getUserBalance(principal: Principal): Int {
        val user = ((principal as UsernamePasswordAuthenticationToken).principal as MyUserPrincipal).user
        return user.balance
    }

    @GetMapping("/user/online")
    fun getOnlineUsers(principal: Principal): List<OnlineUserDto> {
        return userService.getOnlineUsers().map { OnlineUserDto(it.id, it.name) }
    }

    @ExceptionHandler(UserAlreadyExistsException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun userAlreadyExists(e: UserAlreadyExistsException): String? {
        return e.message
    }

}