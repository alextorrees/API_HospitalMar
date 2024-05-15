package com.example.model


import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class Nota(
    val idNota: Int,
    val idInforme: Int,
    val nota: Int,
    val comentario: String
)

object Notas: Table("nota") {
    val idNota = integer("idnota").autoIncrement()
    val idInforme = integer("idinforme").references(Infromes.idInforme)
    val nota = Infromes.integer("nota")
    val comentario = varchar("comentario", 1000)
}