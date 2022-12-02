package com.vakzu.musicwars.dto

import java.io.Serializable

/**
 * A DTO for the {@link com.vakzu.musicwars.entities.Effect} entity
 */
data class EffectDto(
    val id: Int? = null,
    val name: String? = null,
    val price: Int? = null,
    val stamina: Int? = null,
    val strength: Int? = null,
    val luck: Int? = null,
    val constitution: Int? = null
) : Serializable