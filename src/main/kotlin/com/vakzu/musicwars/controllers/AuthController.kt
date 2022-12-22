package com.vakzu.musicwars.controllers

import com.vakzu.musicwars.dto.LoginDto
import com.vakzu.musicwars.dto.RegisterRequest
import com.vakzu.musicwars.security.UserService
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(val userService: UserService) {

    @GetMapping("/register")
    fun registerPage(): String = "register"

    @PostMapping("/register")
    fun registerUser(@ModelAttribute("userForm") form: RegisterRequest, model: Model): LoginDto {
        val userId = userService.registerUser(form.username, form.password)
        return LoginDto(form.username, userId)
    }

}