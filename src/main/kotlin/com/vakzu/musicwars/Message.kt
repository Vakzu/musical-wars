package com.vakzu.musicwars

import com.vakzu.musicwars.dto.websocket.CommandType

class OnlineMessage(val type: CommandType, val userId: Int, val username: String)
