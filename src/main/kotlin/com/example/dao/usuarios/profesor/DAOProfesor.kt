package com.example.dao.usuarios.profesor

import com.example.model.usuarios.Alumno
import com.example.model.usuarios.Profesor

interface DAOProfesor {
    suspend fun allProfesor(): List<Profesor>
    suspend fun selectAlumnoPorIdentificador(identificador: String): Profesor?
}