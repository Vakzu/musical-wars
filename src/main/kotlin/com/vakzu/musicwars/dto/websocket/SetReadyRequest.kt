package com.vakzu.musicwars.dto.websocket

class SetReadyRequest(val commandType: CommandType, val characterId: Int, val songId: Int, val effectId: Int)