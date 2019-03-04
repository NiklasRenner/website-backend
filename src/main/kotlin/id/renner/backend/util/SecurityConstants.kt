package id.renner.backend.util

import java.time.Duration

const val ISSUER = "renner.id"
const val TOKEN_PREFIX = "Bearer "
const val HEADER_STRING = "Authorization"
val EXPIRATION_TIME: Long =  Duration.ofDays(10).toMillis()
