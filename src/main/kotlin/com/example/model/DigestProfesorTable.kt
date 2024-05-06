package com.example.model

import com.example.dao.usuarios.alumno.daoAlumno
import com.example.dao.usuarios.profesor.daoProfesor
import io.ktor.server.auth.*
import java.security.MessageDigest

data class DigestProfesorTable(val userName: String, val realm: String) : Principal


var profesorTable: MutableMap<String, ByteArray> = mutableMapOf()

suspend fun uploadProfesor(): MutableMap<String, String> {
    val profesorList = daoProfesor.allProfesor()
    val profesorTable = mutableMapOf<String, String>()

    for (profesor in profesorList) {
        profesorTable[profesor.identificador] = profesor.contrasenya
    }

    return profesorTable
}