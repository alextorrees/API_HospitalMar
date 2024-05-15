package com.example.model

import com.example.model.evaluaciones.AutoEvaluaciones
import com.example.model.evaluaciones.Coevaluaciones
import com.example.model.evaluaciones.EvaluacionesProfesor
import com.example.model.usuarios.Alumnos
import org.jetbrains.exposed.sql.Table
import java.util.Date

data class Informe(
    val idInforme: Int,
    val idAlumno: Int,
    val fechaGeneracion: Date,
    val idEvaluacionProfesor: Int,
    val puntuacionEvaProf: Int,
    val notaFinal: Int,
    val puntuacion: Int
)


object Infromes: Table("informe") {
    val idInforme = integer("idinforme").autoIncrement()
    val idAlumno = integer("idalumno").references(Alumnos.idAlumno)
    val fechaGeneracion = varchar("fechageneracion", 55)
    val idEvaluacionProfesor = integer("idevaluacionprofesor").references(EvaluacionesProfesor.idEvaluacionProfesor)
    val puntuacionEvaProf = integer("puntuacionevaprof")
    val notaFinal = integer("notafinal")
    val puntuacion = integer("puntuacion")
}