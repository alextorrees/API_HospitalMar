package com.example.model

import java.util.Date

data class Evaluacion(
    val idEvaluacion: Int,
    val idAlumno: Int,
    val tipoEvaluacion: Int,
    val fecha: Date,
    val idCompetencia: Int,
    val idEvaluador: Int,
    val idProfesor: Int
)
