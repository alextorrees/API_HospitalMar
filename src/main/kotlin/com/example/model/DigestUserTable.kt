package com.example.model

import com.example.dao.usuarios.alumno.daoAlumno
import io.ktor.server.auth.*
import java.security.MessageDigest

data class DigestUserTable(val userName: String, val realm: String) : Principal

fun getMd5Digest(str: String): ByteArray = MessageDigest.getInstance("MD5").digest(str.toByteArray(Charsets.UTF_8))

val myRealm = "Access to the '/' path"
var userTable: MutableMap<String, ByteArray> = mutableMapOf(
    "admin" to getMd5Digest("admin:$myRealm:password")
)

suspend fun uploadUser(): MutableMap<String, ByteArray> {
    val userList = daoAlumno.allAlumno()
    if (userList.isEmpty()) {
        return mutableMapOf() // Devolver un mapa vacío si no hay usuarios
    }
    for (user in userList) {
        userTable[user.correo] = getMd5Digest("${user.correo}:$myRealm:${user.contrasenya}")
    }
    return userTable
}
