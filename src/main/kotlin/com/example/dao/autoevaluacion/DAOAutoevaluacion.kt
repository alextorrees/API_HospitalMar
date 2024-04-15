package com.example.dao.autoevaluacion

interface DAOAutoevaluacion {

    suspend fun anadirAutoevaluacion (idAlumno: Int, puntuacion: Int, fechaEva: String, comentarios: String)

}