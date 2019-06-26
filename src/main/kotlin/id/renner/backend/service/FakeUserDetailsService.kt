package id.renner.backend.service

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.Collections.emptyList

@Service // TODO proper user lookup PAM/some custom db user table
class FakeUserDetailsService : UserDetailsService {
    private val fakeUser = "Niklas"
    private val fakePassword = "\$2a\$10\$pJoTgWR4172hqTUK8ADR2.TmWItEhc8Q0I8VkxJ98raJiqj.9Mnwq"

    override fun loadUserByUsername(username: String): UserDetails? {
        if (username == fakeUser) {
            return User(username, fakePassword, emptyList<GrantedAuthority>())
        }

        throw UsernameNotFoundException("Provided username not found")
    }
}