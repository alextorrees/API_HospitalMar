package com.example.dao.evaluaciones.evaluacionProfesor

interface DAOEvaluacionProfesor {
    suspend fun anadirEvaluacionProfesor(idProfesor: Int, idAlumno: Int, fechaEva: String, puntuacion: Int, comentarios: String)

}