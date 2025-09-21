package mcp

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
open class ApiSecurityConfig {

    @Bean
    open fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http.authorizeHttpRequests { r ->
            r
                .requestMatchers(HttpMethod.OPTIONS, "/.well-known/*").permitAll()
                .requestMatchers(HttpMethod.GET, "/.well-known/*").permitAll()
                .requestMatchers("/sse").permitAll()
                .requestMatchers("/mcp*").permitAll()
                .requestMatchers(HttpMethod.GET, "/login**").permitAll()
                .anyRequest().authenticated()
        }.formLogin { f ->
            f.loginPage("/login")
                .permitAll()
        }.logout { logout ->
            logout.permitAll()
        }.csrf { csrf ->
            csrf.disable()
        }.build()
    }

    @Bean
    open fun users(): InMemoryUserDetailsManager {
        val user = User.withUsername("user").password("password").roles("USER").build()
        return InMemoryUserDetailsManager(user)
    }
}