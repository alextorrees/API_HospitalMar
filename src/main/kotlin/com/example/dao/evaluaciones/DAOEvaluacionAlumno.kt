package com.example.dao.evaluaciones

import com.example.model.evaluaciones.EvaluacionAlumno

interface DAOEvaluacionAlumno {
    suspend fun selectEvaluacionesPorAlumno(idAlumno: Int, fechaInicio: String, fechaFin: String): List<EvaluacionAlumno>
}