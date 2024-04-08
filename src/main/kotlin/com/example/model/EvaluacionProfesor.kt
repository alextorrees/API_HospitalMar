package com.example.model

import com.example.model.EvaluacionesProfesor.references
import org.jetbrains.exposed.sql.Table
import java.sql.Date

data class EvaluacionProfesor(
    val idEvaluacionProfesor: Int,
    val idAlumno: Int,
    val idProfesor: Int,
    val fechaEva: Date,
    val puntuacion: Int,
    val comentarios: String,
)

object EvaluacionesProfesor: Table("evaluacionprofesor"){
    val idEvaluacionProfesor = integer("idevaluacionprofesor").autoIncrement()
    val idAlumno = integer("idalumno").references(Alumnos.idAlumno)
    val idProfesor = integer("idprofesor").references(Alumnos.idProfesor)
    val fechaEva = varchar("fechaeva", 55)
    val puntuacion = integer("puntuacion")
    val comentarios = varchar("comentarios", 1000)
}
