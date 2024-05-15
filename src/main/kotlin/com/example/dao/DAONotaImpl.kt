package com.example.dao

import com.example.model.*
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll

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

}

val daoNota: DAONota = DAONotaImpl().apply{}