package com.vakzu.musicwars.controllers

import com.vakzu.musicwars.dto.AuthRequest
import com.vakzu.musicwars.dto.AuthResponse
import com.vakzu.musicwars.security.JwtUtil
import com.vakzu.musicwars.security.UserService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(val userService: UserService, val jwtUtil: JwtUtil) {

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    fun registerUser(@RequestBody authRequest: AuthRequest) {
        userService.registerUser(authRequest)
    }

    @PostMapping("/auth")
    @ResponseStatus(HttpStatus.OK)
    fun authenticate(@RequestBody authRequest: AuthRequest): AuthResponse {
        userService.login(authRequest)
        return AuthResponse(jwtUtil.generateToken(authRequest.username))
    }
}