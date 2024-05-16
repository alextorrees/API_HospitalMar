package com.example.dao.informes

import com.example.dao.DataBaseConnection
import com.example.model.informes.InsertNota
import com.example.model.informes.Nota
import com.example.model.informes.Notas
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class DAONotaImpl: DAONota {

    /**
     * Convierte una fila de resultado en un objeto Nota.
     * @param row Fila de resultado.
     * @return Objeto Nota.
     */
    private fun resultToRowNota(row: ResultRow) = Nota(
        idNota = row[Notas.idNota],
        idInforme = row[Notas.idInforme],
        nota = row[Notas.nota],
        comentario = row[Notas.comentario]
    )

    /**
     * Obtiene todas las notas.
     * @return Lista de notas.
     */
    override suspend fun selectAllNotas(): List<Nota> = DataBaseConnection.dbQuery {
        Notas.selectAll().map(::resultToRowNota)
    }

    /**
     * Obtiene notas por identificador de informe.
     * @param idInfomre Identificador del informe.
     * @return Lista de notas que pertenecen al informe especificado por el identificador.
     */
    override suspend fun selectNotasPorId(idInfomre: Int): List<Nota> = DataBaseConnection.dbQuery {
        Notas.select {
            (Notas.idInforme eq idInfomre)
        }.map(::resultToRowNota)
    }

    /**
     * Inserta una nueva nota en la base de datos.
     * @param notaType Nota a insertar.
     */
    override suspend fun insertNota(notaType: InsertNota) {
        transaction {
            Notas.insert {
                it[idInforme] = notaType.idInforme
                it[nota] = notaType.nota
                it[comentario] = notaType.comentario.removeSurrounding("[", "]")
            }
        }
    }
}


val daoNota: DAONota = DAONotaImpl().apply{}