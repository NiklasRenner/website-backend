package id.renner.backend.config

import com.auth0.jwt.algorithms.Algorithm
import id.renner.backend.config.filter.JwtAuthenticationFilter
import id.renner.backend.config.filter.JwtAuthorizationFilter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.HttpStatusEntryPoint
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import javax.servlet.Filter


@Configuration
class SecurityConfig(val fakeUserDetailsService: UserDetailsService) : WebSecurityConfigurerAdapter() {

    @Value("\${jwt.secret}")
    private lateinit var secret: String

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        val algorithm = Algorithm.HMAC256(secret)

        http.cors()
                .and()
                .httpBasic().disable()
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/services", "/dot/**", "/p/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().disable()
                .logout().disable()
                .addFilterBefore<UsernamePasswordAuthenticationFilter>(JwtAuthenticationFilter(authenticationManager(), "/login", algorithm))
                .addFilterBefore<UsernamePasswordAuthenticationFilter>(JwtAuthorizationFilter(authenticationManager(), algorithm))
                .exceptionHandling()
                .authenticationEntryPoint(HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
    }

    @Throws(Exception::class)
    override fun configure(managerBuilder: AuthenticationManagerBuilder) {
        managerBuilder.userDetailsService(fakeUserDetailsService).passwordEncoder(passwordEncoder())
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val corsConfiguration = CorsConfiguration()
        corsConfiguration.allowedOrigins = listOf("http://localhost:4200", "https://renner.id")
        corsConfiguration.exposedHeaders = listOf("Authorization")

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", corsConfiguration.applyPermitDefaultValues())
        return source
    }
}

private inline fun <reified T : Filter> HttpSecurity.addFilterBefore(filter: Filter): HttpSecurity = this.addFilterBefore(filter, T::class.java)