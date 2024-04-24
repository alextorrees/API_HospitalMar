package com.example.model.evaluaciones

import com.example.model.usuarios.Alumnos
import java.util.Date
import org.jetbrains.exposed.sql.Table

data class EvaluacionProfesor(
    val idEvaluacionProfesor: Int,
    val idAlumno: Int,
    val idProfesor: Int,
    val fechaEva: java.util.Date,
    val puntuacion: Int,
    val comentarios: String
)

object EvaluacionesProfesor: Table("evaluacionprofesor"){
    val idEvaluacionProfesor = integer("idevaluacionprofesor").autoIncrement()
    val idAlumno = integer("idalumno").references(Alumnos.idAlumno)
    val idProfesor = integer("idprofesor").references(Alumnos.idProfesor)
    val fechaEva = varchar("fechaeva", 55)
    val puntuacion = integer("puntuacion")
    val comentarios = varchar("comentarios", 1000)
}
