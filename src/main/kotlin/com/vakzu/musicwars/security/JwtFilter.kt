package com.vakzu.musicwars.security

import com.vakzu.musicwars.repos.UserRepository
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.GenericFilterBean
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

@Component
class JwtFilter(val jwtUtil: JwtUtil, val userRepository: UserRepository): GenericFilterBean() {
    override fun doFilter(request: ServletRequest, response: ServletResponse?, chain: FilterChain) {
        val header = (request as HttpServletRequest).getHeader(HttpHeaders.AUTHORIZATION)
        if (header != null) {
            val token = header.substring(7)
            if (token.isNotEmpty() && jwtUtil.validateToken(token)) {
                val username = jwtUtil.extractUsername(token)
                val user = userRepository.findByName(username)

                if (user != null) {
                    val auth = UsernamePasswordAuthenticationToken(user, "", null)
                    SecurityContextHolder.getContext().authentication = auth
                }
            }
        }
        chain.doFilter(request, response)
    }
}