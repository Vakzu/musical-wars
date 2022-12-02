package com.vakzu.musicwars.config

import com.sun.security.auth.UserPrincipal
import com.vakzu.musicwars.security.MyUserPrincipal
import org.slf4j.LoggerFactory
import org.springframework.http.server.ServerHttpRequest
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.server.support.DefaultHandshakeHandler
import java.security.Principal

@Component
class UserHandshakeHandler: DefaultHandshakeHandler() {

    override fun determineUser(
        request: ServerHttpRequest,
        wsHandler: WebSocketHandler,
        attributes: MutableMap<String, Any>
    ): Principal {
        val context = SecurityContextHolder.getContext().authentication.principal as MyUserPrincipal
        return UserPrincipal(context.username)
    }
}