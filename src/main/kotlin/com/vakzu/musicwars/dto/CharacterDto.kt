package com.vakzu.musicwars.dto

import java.io.Serializable

/**
 * A DTO for the {@link com.vakzu.musicwars.entities.Character} entity
 */
data class CharacterDto(val id: Int? = null, val heroName: String? = null, val heroHealth: Int? = null) : Serializable