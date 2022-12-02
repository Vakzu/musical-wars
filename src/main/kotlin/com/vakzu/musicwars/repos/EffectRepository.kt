package com.vakzu.musicwars.repos

import com.vakzu.musicwars.entities.Effect
import org.springframework.data.jpa.repository.JpaRepository

interface EffectRepository: JpaRepository<Effect, Long> {

}