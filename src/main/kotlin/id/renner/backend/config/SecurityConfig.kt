package id.renner.backend.config

import id.renner.backend.config.filter.JwtAuthenticationFilter
import id.renner.backend.config.filter.JwtAuthorizationFilter
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import javax.servlet.Filter


@Configuration
class SecurityConfig(val fakeUserDetailsService: UserDetailsService, val passwordEncoder: PasswordEncoder) : WebSecurityConfigurerAdapter() {

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/services").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().disable()
                .logout().disable()
                .addFilterBefore<UsernamePasswordAuthenticationFilter>(JwtAuthenticationFilter(authenticationManager(), "/login"))
                .addFilterBefore<UsernamePasswordAuthenticationFilter>(JwtAuthorizationFilter(authenticationManager()))
    }

    @Throws(Exception::class)
    override fun configure(managerBuilder: AuthenticationManagerBuilder) {
        managerBuilder.userDetailsService(fakeUserDetailsService).passwordEncoder(passwordEncoder)
    }
}

private inline fun <reified T : Filter> HttpSecurity.addFilterBefore(filter: Filter): HttpSecurity = this.addFilterBefore(filter, T::class.java)