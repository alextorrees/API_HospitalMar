package com.example.model.evaluaciones

import com.example.model.usuarios.Alumnos
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.Date
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date
import java.time.LocalDate


data class EvaluacionProfesor(
    val idEvaluacionProfesor: Int,
    val idAlumno: Int,
    val idProfesor: Int,
    val fechaEva: LocalDate,
    val puntuacion: Int,
    val comentarios: String
)

object EvaluacionesProfesor: Table("evaluacionprofesor"){
    val idEvaluacionProfesor = integer("idevaluacionprofesor").autoIncrement()
    val idAlumno = integer("idalumno").references(Alumnos.idAlumno)
    val idProfesor = integer("idprofesor").references(Alumnos.idProfesor)
    val fechaEva = date("fechaeva")
    val puntuacion = integer("puntuacion")
    val comentarios = varchar("comentarios", 1000)
}
