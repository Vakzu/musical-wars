package com.vakzu.musicwars.repos

import com.vakzu.musicwars.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<User, Long>  {
    fun findByName(username: String): User?

    @Query("SELECT u FROM User u WHERE u.isOnline = true")
    fun getOnlineUsers(): List<User>

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE \"user\" SET is_online=false WHERE name = ?1")
    fun setUserOffline(username: String)

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE \"user\" SET is_online=true WHERE name = ?1")
    fun setUserOnline(username: String)
}