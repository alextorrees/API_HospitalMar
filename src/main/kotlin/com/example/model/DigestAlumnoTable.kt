package com.example.model

import com.example.dao.usuarios.alumno.daoAlumno
import com.example.dao.usuarios.profesor.daoProfesor
import com.example.getMd5DigestForPassword
import io.ktor.server.auth.*
import java.security.MessageDigest

data class DigestUserTable(val userName: String, val realm: String) : Principal


var userTable: MutableMap<String, ByteArray> = mutableMapOf()

suspend fun uploadAlumno(): MutableMap<String, String> {
    val userList = daoAlumno.allAlumno()
    val userTable = mutableMapOf<String, String>()

    for (user in userList) {
        val hashedPassword = getMd5DigestForPassword(user.contrasenya)
        userTable[user.identificador] = hashedPassword
    }

    return userTable
}



