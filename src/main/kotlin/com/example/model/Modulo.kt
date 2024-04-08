package com.example.model

import org.jetbrains.exposed.sql.Table

data class Modulo(
    val idModulo: Int,
    val cursoAcademico: Int,
    val codCiclo: Int,
    val grup: Int,
    val numModulo: Int,
    val nombreModul: String
)


object Modulos: Table("modulo"){
    val idModulo = integer("idmodulo").autoIncrement()
    val cursoAcademico = integer("cursoacademico")
    val codCiclo = integer("codciclo")
    val grupo = integer("grupo")
    val numModulo = integer("nummodulo")
    val nombreModul = varchar("nombremodulo", 55)
}