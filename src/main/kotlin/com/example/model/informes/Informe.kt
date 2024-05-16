package com.example.model.informes

import com.example.model.competencias.Competencias
import com.example.model.LocalDateSerializer
import com.example.model.modulos.Modulos
import com.example.model.usuarios.Alumnos
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date
import java.time.LocalDate

@Serializable
data class Informe(
    val idInforme: Int?,
    val idAlumno: Int,
    val idModulo: Int,
    val idCompetencia: Int,
    val fechaGeneracion: String,
    val notaFinal: Int
)

@Serializable
data class InsertInforme(
    val idAlumno: Int,
    val idModulo: Int,
    val idCompetencia: Int,
    val fechaGeneracion: String,
    val notaFinal: Int
)

object Infromes: Table("informe") {
    val idInforme = integer("idinforme").autoIncrement()
    val idAlumno = integer("idalumno").references(Alumnos.idAlumno)
    val idModulo = integer("idmodulo").references(Modulos.idModulo)
    val idCompetencia = integer("idcompetencia").references(Competencias.idCompetencia)
    val fechaGeneracion = varchar("fechageneracion", 50)
    val notaFinal = integer("notafinal")
}