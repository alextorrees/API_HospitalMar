package com.example.model.digests

import com.example.dao.usuarios.profesor.daoProfesor
import io.ktor.server.auth.*

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