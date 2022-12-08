package com.vakzu.musicwars.lobby

interface HeroShopInfo {
    fun getHero_id(): Int?
    fun getName(): String?
    fun getPrice(): Int?
    fun getHealth(): Int?
    fun getImg_path(): String?
    fun getUser_id(): Int?
}