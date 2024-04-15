package com.example.model

import org.jetbrains.exposed.sql.Table

data class Valoracion(
    val idValoracion: Int,
    val puntuacionMin: Int,
    val idModulo: Int,
    val idCompetencia: Int
)

object Valoraciones: Table("valoracion"){
    val idValoracion = integer("idvaloracion").autoIncrement()
    val puntuacionMin = integer("puntuacionmin")
    val idModulo = integer("idmodulo").references(Modulos.idModulo)
    val idCompetencia = integer("idcompetencia").references(Competencias.idCompetencia)
}