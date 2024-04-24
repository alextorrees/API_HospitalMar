package com.example.model

import org.jetbrains.exposed.sql.Table

data class Modulo(
    val idModulo: Int,
    val cursoAcademico: Int,
    val codCiclo: Int,
    val grupo: String,
    val numModulo: String,
    val nombreModul: String
    //TODO (NO HACE FALTA METER LAS COMPETENCIAS QUE TIENE CADA CICLO?)
)


object Modulos: Table("modulo"){
    val idModulo = integer("idmodulo").autoIncrement()
    val cursoAcademico = integer("cursoacademico")
    val codCiclo = integer("codciclo")
    val grupo = varchar("grupo", 55)
    val numModulo = varchar("nummodulo", 55)
    val nombreModulo = varchar("nombremodulo", 55)
}