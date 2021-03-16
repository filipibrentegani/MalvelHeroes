package com.filipibrentegani.marvelheroes.network

import com.filipibrentegani.marvelheroes.BuildConfig
import java.security.MessageDigest

object NetworkUtils {
    fun marvelHashCode(currentTimeMillis: Long): String {
        val md = MessageDigest.getInstance("MD5")
        val hash =
            md.digest("$currentTimeMillis${BuildConfig.PRIVATE_KEY_MARVEL_API}${BuildConfig.PUBLIC_KEY_MARVEL_API}".toByteArray())
        return hash.joinToString("") { "%02x".format(it) }
    }
}