package com.vakzu.musicwars.security

import io.jsonwebtoken.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtUtil {

    val logger: Logger = LoggerFactory.getLogger(JwtUtil::class.java)!!

    private val secret: String = Base64.getEncoder()
        .encodeToString("14wcjuT4R2GOZ1Bu1YMZ6AjMs5Ffgl8aMtH0wzKNwhtLxM2cZ3gaRNdrv4z9Jo1fQHAI54kamyeJxGOAIFFnvSpCW27BhA9v4Vv6UJPVRqPZwgSxM5bUqLeM".toByteArray())

    @Value("\${jwt.expiration-time}")
    private val expirationTime: Long = 0

    fun generateToken(username: String): String {
        val date = Date()
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(date)
            .setExpiration(Date(date.time + expirationTime * 1000))
            .signWith(SignatureAlgorithm.HS512, secret)
            .compact()
    }

    fun extractUsername(authToken: String): String {
        return getClaimsFromToken(authToken)
            .subject
    }

    fun getClaimsFromToken(token: String): Claims {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).body
    }

    fun validateToken(token: String): Boolean {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token)
            return true
        } catch (ex: SecurityException) {
            logger.error("Security exception", ex)
        } catch (ex: MalformedJwtException) {
            logger.error("MalformedJwtException exception", ex)
        } catch (ex: ExpiredJwtException) {
            logger.error("ExpiredJwtException exception", ex)
        } catch (ex: UnsupportedJwtException) {
            logger.error("UnsupportedJwtException exception", ex)
        } catch (ex: IllegalArgumentException) {
            logger.error("IllegalArgumentException exception", ex)
        }
        return false
    }
}