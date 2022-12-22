package com.vakzu.musicwars.dto

class HeroDto(val id: Int?, val name: String?, val health: Int?, val price: Int?, val imgSrc: String?)

class AllHeroResponse(val heroes: List<HeroDto>)