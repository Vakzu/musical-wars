package com.vakzu.musicwars.security

import com.vakzu.musicwars.dto.AuthRequest
import com.vakzu.musicwars.entities.User
import com.vakzu.musicwars.exceptions.PasswordMismatchException
import com.vakzu.musicwars.exceptions.UserAlreadyExistsException
import com.vakzu.musicwars.repos.UserRepository
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: BCryptPasswordEncoder
) {

    fun registerUser(authRequest: AuthRequest) {
        val existing = userRepository.findByName(authRequest.username)
        if (existing != null) {
            throw UserAlreadyExistsException(existing.name)
        }

        val user = User()
        user.name = authRequest.username
        user.password = passwordEncoder.encode(authRequest.password)
        user.isOnline = true

        userRepository.save(user)
    }

    fun login(authRequest: AuthRequest) {
        val existing = userRepository.findByName(authRequest.username)
            ?: throw UsernameNotFoundException(authRequest.username)

        if (!passwordEncoder.matches(authRequest.password, existing.password)) {
            throw PasswordMismatchException()
        }
    }

}