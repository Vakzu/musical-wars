package com.vakzu.musicwars.controllers

import com.vakzu.musicwars.OnlineMessage
import com.vakzu.musicwars.WebSocketConfig
import org.slf4j.LoggerFactory
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller
import java.util.*

@Controller
class BaseController {

    val log = LoggerFactory.getLogger(WebSocketConfig::class.java)


    @MessageMapping("/online")
    @SendTo("/topic/online")
    fun joinOrExit(message: OnlineMessage) = message

}