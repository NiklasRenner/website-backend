package id.renner.backend.config.filter

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTCreationException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import id.renner.backend.model.UserDto
import id.renner.backend.util.EXPIRATION_TIME
import id.renner.backend.util.HEADER_STRING
import id.renner.backend.util.ISSUER
import id.renner.backend.util.TOKEN_PREFIX
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.InsufficientAuthenticationException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import java.io.IOException
import java.time.Instant
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtAuthenticationFilter(val authManager: AuthenticationManager, loginUrl: String, val algorithm: Algorithm) : AbstractAuthenticationProcessingFilter(loginUrl) {

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        try {
            val user = ObjectMapper().readValue<UserDto>(request.inputStream)

            return authManager.authenticate(UsernamePasswordAuthenticationToken(user.username, user.password, ArrayList()))
        } catch (ex: IOException) {
            throw InsufficientAuthenticationException(ex.message)
        }
    }

    override fun successfulAuthentication(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain, authResult: Authentication) {
        try {
            val token = JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject((authResult.principal as User).username)
                    .withExpiresAt(Date(Instant.now().toEpochMilli() + EXPIRATION_TIME))
                    .sign(algorithm)

            response.addHeader(HEADER_STRING, TOKEN_PREFIX + token)
        } catch (ex: JWTCreationException) {
            throw RuntimeException(ex)
        }
    }
}