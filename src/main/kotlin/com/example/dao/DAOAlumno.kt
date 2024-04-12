package com.example.dao

import com.example.model.Alumno

interface DAOAlumno {
    suspend fun añadirNuevoAlumno(nombre: String, apellido1: String, apellido2: String, dni: String, correo: String, identificador: String, codCiclo: Int): Alumno?

    suspend fun cambiarContrasenya(idAlumno: Int, contrasenya: String): Boolean
    suspend fun deleteAlumno(idAlumno: Int): Boolean
}