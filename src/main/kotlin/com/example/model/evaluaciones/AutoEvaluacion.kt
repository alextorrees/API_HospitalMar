package com.example.model.evaluaciones

import com.example.model.usuarios.Alumnos
import org.jetbrains.exposed.sql.Table

data class AutoEvaluacion(
    val idAutoEvaluacion: Int,
    val idAlumno: Int,
    val fechaEva: java.util.Date,
    val puntuacion: Int,
    val comentarios: String,
//    val idcomp1: Int,
//    val punt1: Int,
//    val idcomp2: Int,
//    val punt2: Int,
//    val idcomp3: Int,
//    val punt3: Int,
)

object AutoEvaluaciones: Table("autoevaluacion"){
    val idAutoEvaluacion = integer("idevaluacion").autoIncrement()
    val idAlumno = integer("idalumno").references(Alumnos.idAlumno)
    val fechaEva = varchar("fechaeva", 55)
    val puntuacion = integer("puntuacion")
    val comentarios = varchar("comentarios", 1000)
}