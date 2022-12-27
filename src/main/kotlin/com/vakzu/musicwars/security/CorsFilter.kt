package com.vakzu.musicwars.security

import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class CorsFilter: OncePerRequestFilter() {
    override fun doFilterInternal(
        req: HttpServletRequest,
        resp: HttpServletResponse,
        filterChain: FilterChain
    ) {
        resp.addHeader("Access-Control-Allow-Origin", "http://localhost:3000")
        resp.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, PATCH, HEAD")
        resp.addHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, Authorization")
        resp.addHeader("Access-Control-Expose-Headers", "Access-Control-Allow-Origin, Access-Control-Allow-Credentials")
        resp.addHeader("Access-Control-Allow-Credentials", "true")
        resp.addIntHeader("Access-Control-Max-Age", 10)
        filterChain.doFilter(req, resp)
    }
}