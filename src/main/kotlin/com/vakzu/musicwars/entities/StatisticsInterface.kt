package com.vakzu.musicwars.entities

interface StatisticsInterface {
    fun getGamesCount(): Int?
    fun getWinsCount(): Int?
    fun getAveragePlace(): Double?
    fun getLastGameDate(): String?
}