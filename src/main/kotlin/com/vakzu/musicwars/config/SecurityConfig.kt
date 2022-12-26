package com.vakzu.musicwars.config

//import com.vakzu.musicwars.security.JwtFilter

import com.fasterxml.jackson.databind.ObjectMapper
import com.vakzu.musicwars.dto.LoginDto
import com.vakzu.musicwars.security.MyUserPrincipal
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.AuthenticationSuccessHandler


@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf().disable()
            .authorizeRequests()
                .antMatchers("/register").not().fullyAuthenticated()
                .antMatchers("/resources/**").permitAll()
                .anyRequest().authenticated()
            .and()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint())
            .and()
                .formLogin()
                .loginProcessingUrl("/login")
                .successHandler(successHandler())
                .failureHandler(failureHandler())
            .and()
                .logout()
                .permitAll()
                .deleteCookies("JSESSIONID")

        return http.build()
    }

    fun authenticationEntryPoint(): AuthenticationEntryPoint {
        return AuthenticationEntryPoint { _, response, _ ->
            response.status = 401
        }
    }

    fun successHandler(): AuthenticationSuccessHandler {
        return AuthenticationSuccessHandler { _, response, _ ->
            response.status = 200
            response.addHeader("Content-Type", "application/json")
            val objWriter = ObjectMapper().writer()

            val principal = SecurityContextHolder.getContext().authentication.principal
            val user = principal as MyUserPrincipal
            val json = objWriter.writeValueAsString(LoginDto(user.user.name, user.user.id))
            response.writer.write(json)
        }
    }

    fun failureHandler(): AuthenticationFailureHandler {
        return AuthenticationFailureHandler { _, response, _ ->
            response.status = 401
        }
    }

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

}