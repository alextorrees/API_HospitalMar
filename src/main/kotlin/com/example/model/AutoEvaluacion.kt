package com.example.model

import com.example.model.EvaluacionesProfesor.references
import org.jetbrains.exposed.sql.Table
import java.sql.Date

data class AutoEvaluacion(
    val idAutoEvaluacion: Int,
    val idAlumno: Int,
    val fechaEva: Date,
    val puntuacion: Int,
    val comentarios: String,
)

object AutoEvaluaciones: Table("autoevaluacion"){
    val idAutoEvaluacion = integer("idevaluacion").autoIncrement()
    val idAlumno = integer("idalumno").references(Alumnos.idAlumno)
    val fechaEva = varchar("fechaeva", 55)
    val puntuacion = integer("puntuacion")
    val comentarios = varchar("comentarios", 1000)
}