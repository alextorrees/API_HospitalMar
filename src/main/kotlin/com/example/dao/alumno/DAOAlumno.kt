package com.example.dao.alumno

import com.example.model.usuarios.Alumno

interface DAOAlumno {
    suspend fun anadirNuevoAlumno(nombre: String, apellido1: String, apellido2: String, dni: String, correo: String, identificador: String, codCiclo: Int): Alumno?
    suspend fun cambiarContrasenya(idAlumno: Int, contrasenya: String): Boolean
    suspend fun deleteAlumno(idAlumno: Int): Boolean
}