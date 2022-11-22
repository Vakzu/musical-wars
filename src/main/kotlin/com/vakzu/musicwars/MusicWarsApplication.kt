package com.vakzu.musicwars

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MusicWarsApplication

fun main(args: Array<String>) {
    runApplication<MusicWarsApplication>(*args)
}
