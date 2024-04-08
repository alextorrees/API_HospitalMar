package com.example.model

import io.ktor.http.*
import org.jetbrains.exposed.sql.Table
import java.util.Date

data class Informe(
    val idInforme: Int,
    val idAlumno: Int,
    val fechaGeneracion: Date,
    val idAutoEvaluacion: Int,
    val idCoevaluacion: Int,
    val idEvaluacionProfesor: Int,
    val puntuacionAutoEva: Int,
    val puntuacionCoEva: Int,
    val puntuacionEvaProf: Int,
    val notaFinal: Int,
    val puntuacion: Int
)


object Infromes: Table("informe") {
    val idInforme = integer("idinforme").autoIncrement()
    val idAlumno = integer("idalumno").references(Alumnos.idAlumno)
    val fechaGeneracion = varchar("fechageneracion", 55)
    val idAutoEvaluacion = integer("idautoevaluacion").references(AutoEvaluaciones.idAutoEvaluacion)
    val idCoevaluacion = integer("idcoevaluacion").references(Coevaluaciones.idCoevaluacion)
    val idEvaluacionProfesor = integer("idevaluacionprofesor").references(EvaluacionesProfesor.idEvaluacionProfesor)
    val puntuacionAutoEva = integer("puntuacionautoeva")
    val puntuacionCoEva = integer("puntuacioncoeva")
    val puntuacionEvaProf = integer("puntuacionevaprof")
    val notaFinal = integer("notafinal")
    val puntuacion = integer("puntuacion")
}