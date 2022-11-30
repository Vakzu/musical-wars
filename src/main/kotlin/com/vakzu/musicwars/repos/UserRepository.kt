package com.vakzu.musicwars.repos

import com.vakzu.musicwars.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<User, Long>  {

    fun findByName(username: String): User?
}