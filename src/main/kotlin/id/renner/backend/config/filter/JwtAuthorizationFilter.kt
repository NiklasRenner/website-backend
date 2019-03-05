package id.renner.backend.config.filter


import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import id.renner.backend.util.HEADER_STRING
import id.renner.backend.util.ISSUER
import id.renner.backend.util.TOKEN_PREFIX
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import java.io.IOException
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class JwtAuthorizationFilter(authenticationManager: AuthenticationManager, algorithm: Algorithm) : BasicAuthenticationFilter(authenticationManager) {
    private val verifier: JWTVerifier = JWT.require(algorithm)
            .withIssuer(ISSUER)
            .build()

    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val header = request.getHeader(HEADER_STRING)
        if (header != null && header.startsWith(TOKEN_PREFIX)) {
            SecurityContextHolder.getContext().authentication = getAuthentication(request)
        }

        chain.doFilter(request, response)
    }

    private fun getAuthentication(request: HttpServletRequest): UsernamePasswordAuthenticationToken? {
        val token = request.getHeader(HEADER_STRING) ?: return null

        try {
            val jwt = verifier.verify(token.removePrefix(TOKEN_PREFIX))
            return UsernamePasswordAuthenticationToken(jwt.subject, null, ArrayList<GrantedAuthority>())
        } catch (ex: JWTVerificationException) {
            throw RuntimeException(ex)
        }
    }
}