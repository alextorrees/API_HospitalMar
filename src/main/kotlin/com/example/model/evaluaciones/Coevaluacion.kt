package com.example.model.evaluaciones

import com.example.model.usuarios.Alumnos
import org.jetbrains.exposed.sql.Table
import java.sql.Date

data class Coevaluacion(
    val idCoevaluacion: Int,
    val idAlumno: Int,
    val idCoevaluador: Int,
    val fechaEva: Date,
    val puntuacion: Int,
    val comentarios: String,
)

object Coevaluaciones: Table("coevaluacion"){
    val idCoevaluacion = integer("idevaluacion").autoIncrement()
    val idAlumno = integer("idalumno").references(Alumnos.idAlumno)
    val idCoevaluador = integer("idcoevaluador").references(Alumnos.idAlumno)
    val fechaEva = varchar("fechaeva", 55)
    val puntuacion = integer("puntuacion")
    val comentarios = varchar("comentarios", 1000)
}