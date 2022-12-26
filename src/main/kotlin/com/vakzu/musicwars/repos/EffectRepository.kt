package com.vakzu.musicwars.repos

import com.vakzu.musicwars.entities.Effect
import com.vakzu.musicwars.lobby.EffectShopInfo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface EffectRepository: JpaRepository<Effect, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM get_effect_shop_info(?1)")
    fun findEffectShopInfoByUserId(userId: Int): List<EffectShopInfo>

    @Query(nativeQuery = true, value = "SELECT * FROM get_available_effects_for_user(?1)")
    fun findEffectsByUserId(userId: Int): List<Effect>

    @Query(nativeQuery = true, value = "SELECT * FROM buy_effect(?1, ?2)")
    fun buyEffect(userId: Int, effectId: Int): Boolean
}