package com.example

import java.security.MessageDigest

fun getMd5DigestForPassword(password: String): String {
    val digest = MessageDigest.getInstance("MD5").digest(password.toByteArray(Charsets.UTF_8))
    return digest.joinToString("") { "%02x".format(it) }
}