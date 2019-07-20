package id.renner.backend.util

import java.math.BigInteger
import java.net.Inet4Address
import java.net.URLDecoder
import java.security.SecureRandom


fun generateId(byteLength: Int): String {
    val secureRandom = SecureRandom()
    val token = ByteArray(byteLength)
    secureRandom.nextBytes(token)
    return BigInteger(1, token).toString(16)
}

fun hostname(): String = Inet4Address.getLocalHost().hostName

fun String.decode(): String = URLDecoder.decode(this, "UTF-8")