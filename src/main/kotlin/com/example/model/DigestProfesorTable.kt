package com.example.model

import com.example.dao.usuarios.alumno.daoAlumno
import com.example.dao.usuarios.profesor.daoProfesor
import io.ktor.server.auth.*
import java.security.MessageDigest

data class DigestProfesorTable(val userName: String, val realm: String) : Principal

fun getMd5DigestProfesor(str: String): ByteArray = MessageDigest.getInstance("MD5").digest(str.toByteArray(Charsets.UTF_8))

val myRealmProf = "Access to the '/' path"
var ProfesorTable: MutableMap<String, ByteArray> = mutableMapOf(
    "admin" to getMd5Digest("admin:$myRealmProf:password")
)

suspend fun uploadProfesor(): MutableMap<String, ByteArray> {
    val userList = daoProfesor.allProfesor()
    if (userList.isEmpty()) {
        return mutableMapOf() // Devolver un mapa vacío si no hay usuarios
    }
    for (user in userList) {
        ProfesorTable[user.identificador] = getMd5DigestProfesor("${user.identificador}:$myRealm:${user.contrasenya}")
    }
    return ProfesorTable
}