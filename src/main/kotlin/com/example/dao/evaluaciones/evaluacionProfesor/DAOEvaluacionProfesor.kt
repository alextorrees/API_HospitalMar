package com.example.dao.evaluaciones.evaluacionProfesor

import com.example.model.evaluaciones.EvaluacionProfesor
import java.time.LocalDate

interface DAOEvaluacionProfesor {
    suspend fun selectEvaluacionesProfesor(idAlumno: Int): List<EvaluacionProfesor>
    suspend fun anadirEvaluacionProfesor(idProfesor: Int, idAlumno: Int, fechaEva: LocalDate, puntuacion: Int, comentarios: String)
}