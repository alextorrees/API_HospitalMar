package com.example.model

import org.jetbrains.exposed.sql.Table

data class Competencia(
    val idCompetencia: Int,
    val nombreCompetencia: String,
    val pregunta1: String,
    val pregunta2: String,
    val pregunta3: String,
    val pregunta4: String,
)

object Competencias: Table("competencia"){
    val idCompetencia = integer("idcompetencia").autoIncrement()
    val nombreCompetencia = varchar("nombrecompetencia", 255)
    val pregunta1 = varchar("pregunta1", 1000)
    val pregunta2 = varchar("pregunta2", 1000)
    val pregunta3 = varchar("pregunta3", 1000)
    val pregunta4 = varchar("pregunta4", 1000)
}