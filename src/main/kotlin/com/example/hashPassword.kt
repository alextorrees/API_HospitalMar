package com.example

import java.security.MessageDigest

fun hashPassword(password: String): String {
    val bytes = password.toByteArray()
    val digest = MessageDigest.getInstance("SHA-256")
    val hashedBytes = digest.digest(bytes)
    return hashedBytes.joinToString("") { "%02x".format(it) }
}