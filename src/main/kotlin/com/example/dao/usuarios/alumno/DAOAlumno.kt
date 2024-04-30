package com.example.dao.usuarios.alumno

import com.example.model.evaluaciones.EvaluacionAlumno
import com.example.model.usuarios.Alumno

interface DAOAlumno {
    suspend fun allAlumno(): List<Alumno>
    suspend fun alumno(idAlumno: Int): Alumno?
    suspend fun selectAlumnoPorCorreo(correo: String): Alumno?
    suspend fun selectAlumnoPorProfesor(idProfesor: Int): List<Alumno>
    suspend fun insertNuevoAlumno(nombre: String, apellidos: String, correo: String, identificador: String, etiqueta: String, especialidad: String, grupos: String, contrasenya: String, idProfesor: Int): Alumno?
    suspend fun updateContrasenya(idAlumno: Int, contrasenya: String): Boolean
    suspend fun deleteAlumno(idAlumno: Int): Boolean

    suspend fun selectJoin(): List<Alumno>
}

