package com.example.dao

import com.example.model.*
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class DAONotaImpl: DAONota {
    private fun resultToRowNota (row: ResultRow) = Nota(
        idNota = row[Notas.idNota],
        idInforme = row[Notas.idInforme],
        nota = row[Notas.nota],
        comentario = row[Notas.comentario],
    )

    override suspend fun selectAllNotas(): List<Nota> = DataBaseConnection.dbQuery {
        Notas.selectAll().map(::resultToRowNota)
    }

    override suspend fun selectNotasPorId(idInfomre: Int): List<Nota> = DataBaseConnection.dbQuery {
        Notas.select {
            (Notas.idInforme eq idInfomre)
        }.map(::resultToRowNota)
    }

    override suspend fun insertNota(notaType: Nota) {
        transaction {
            Notas.insert {
                it[idInforme] = notaType.idInforme
                it[nota] = notaType.nota
                it[comentario] = notaType.comentario

            }
        }
    }
}

val daoNota: DAONota = DAONotaImpl().apply{}