package com.example.dao.evaluaciones.autoevaluacion

interface DAOAutoevaluacion {

    suspend fun anadirAutoevaluacion (idAlumno: Int, puntuacion: Int, fechaEva: String, comentarios: String)

}