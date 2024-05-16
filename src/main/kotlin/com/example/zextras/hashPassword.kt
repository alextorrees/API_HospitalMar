package com.example.zextras

import java.security.MessageDigest

/**
 * Función que devuelve el hash MD5 de una contraseña.
 * @param password Contraseña para la que se calculará el hash MD5.
 * @return Hash MD5 de la contraseña.
 */
fun getMd5DigestForPassword(password: String): String {
    val digest = MessageDigest.getInstance("MD5").digest(password.toByteArray(Charsets.UTF_8))
    return digest.joinToString("") { "%02x".format(it) }
}