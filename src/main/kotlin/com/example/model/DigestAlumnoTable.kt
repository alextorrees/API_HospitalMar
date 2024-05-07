package com.example.model

import com.example.dao.usuarios.alumno.daoAlumno
import io.ktor.server.auth.*

data class DigestUserTable(val userName: String, val realm: String) : Principal


var userTable: MutableMap<String, ByteArray> = mutableMapOf()

suspend fun uploadAlumno(): MutableMap<String, String> {
    val alumnoList = daoAlumno.allAlumno()
    val alumnoTable = mutableMapOf<String, String>()

    for (alumno in alumnoList) {
        alumnoTable[alumno.identificador] = alumno.contrasenya
    }

    return alumnoTable
}



