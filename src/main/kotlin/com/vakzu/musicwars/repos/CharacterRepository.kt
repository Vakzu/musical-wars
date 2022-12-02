package com.vakzu.musicwars.repos;

import com.vakzu.musicwars.entities.Character
import org.springframework.data.jpa.repository.JpaRepository

interface CharacterRepository : JpaRepository<Character, Int> {
    fun findAllByUserId(user_id: Int): List<Character>
}