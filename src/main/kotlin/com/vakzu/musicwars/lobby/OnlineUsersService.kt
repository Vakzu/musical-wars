package com.vakzu.musicwars.lobby

import com.vakzu.musicwars.dto.UserDTO
import org.springframework.stereotype.Service

@Service
class OnlineUsersService {

    // TODO: change it
    fun getUsersOnline(): List<UserDTO> {
        return listOf(
            UserDTO(1, "Vasya"),
            UserDTO(2, "Zhenya"),
            UserDTO(3, "Sergey")
        )
    }

}