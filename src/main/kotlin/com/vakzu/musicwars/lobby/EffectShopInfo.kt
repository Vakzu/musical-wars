package com.vakzu.musicwars.lobby

interface EffectShopInfo {
    fun getId(): Int
    fun getName(): String
    fun getPrice(): Int
    fun getStamina(): Int
    fun getStrength(): Int
    fun getLuck(): Int
    fun getConstitution(): Int
    fun getAmount(): Int
}