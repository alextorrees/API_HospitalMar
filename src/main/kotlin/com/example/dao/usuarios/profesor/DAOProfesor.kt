package com.example.dao.usuarios.profesor

import com.example.model.usuarios.Profesor

interface DAOProfesor {
    suspend fun allProfesor(): List<Profesor>
}