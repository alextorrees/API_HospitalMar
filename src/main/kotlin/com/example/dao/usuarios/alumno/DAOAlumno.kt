package com.example.dao.usuarios.alumno

import com.example.model.usuarios.Alumno

interface DAOAlumno {
    suspend fun allAlumno(): List<Alumno>
    suspend fun alumno(idAlumno: Int): Alumno?
    suspend fun selectAlumnoPorIdentificador(identificador: String): Alumno?
    suspend fun selectAlumnoPorProfesor(idProfesor: Int): List<Alumno>
    suspend fun updateContrasenya(idAlumno: Int, contrasenya: String): Boolean
    suspend fun deleteAlumno(idAlumno: Int): Boolean
}

