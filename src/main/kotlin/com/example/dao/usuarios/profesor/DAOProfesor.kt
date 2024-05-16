package com.example.dao.usuarios.profesor

import com.example.model.usuarios.Profesor

interface DAOProfesor {
    suspend fun allProfesor(): List<Profesor>
    suspend fun selectAlumnoPorIdentificador(identificador: String): Profesor?
    suspend fun updateContrasenyaProfesor(idProfesor: Int, contrasenya: String): Boolean
}