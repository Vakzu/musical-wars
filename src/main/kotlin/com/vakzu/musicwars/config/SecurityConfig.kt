package com.vakzu.musicwars.config

//import com.vakzu.musicwars.security.JwtFilter

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain


@Configuration
@EnableWebSecurity
class SecurityConfig(
//    val jwtFilter: JwtFilter
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/register").not().fullyAuthenticated()
//            .antMatchers("/war/**", "/game", "/").authenticated()
            .antMatchers("/resources/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .loginPage("/login")
            .defaultSuccessUrl("/")
            .and()
            .logout()
            .permitAll()
            .logoutSuccessUrl("/login")
            .deleteCookies("JSESSIONID")

        return http.build()
    }

//    @Bean
//    fun userDetailsService(): InMemoryUserDetailsManager? {
//        val user1: UserDetails = User.withUsername("user1")
//            .password(passwordEncoder().encode("user1Pass"))
//            .roles("USER")
//            .build()
//        val user2: UserDetails = User.withUsername("user2")
//            .password(passwordEncoder().encode("user2Pass"))
//            .roles("USER")
//            .build()
//        val admin: UserDetails = User.withUsername("admin")
//            .password(passwordEncoder().encode("adminPass"))
//            .roles("ADMIN")
//            .build()
//        return InMemoryUserDetailsManager(user1, user2, admin)
//    }

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

}