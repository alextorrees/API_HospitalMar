package com.example.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table
@Serializable
data class Modulo(
    val idModulo: Int,
    val codCiclo: String,
    val codCompleto: String,
    val nombreCiclo: String,
    val turno: String,
    val grupo: String,
    val numModulo: String,
    val nombreModulo: String
)



object Modulos: Table("modulo"){
    val idModulo = integer("idmodulo").autoIncrement()
    val codCiclo = varchar("codciclo", 55)
    val codCompleto = varchar("codcompleto", 55)
    val nombreCiclo = varchar("nombreciclo", 255)
    val turno = varchar("nombreciclo", 55)
    val grupo = varchar("grupo", 55)
    val numModulo = varchar("nummodulo", 55)
    val nombreModulo = varchar("nombremodulo", 255)
}