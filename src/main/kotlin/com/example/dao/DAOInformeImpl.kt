package com.example.dao

import com.example.model.Informe
import com.example.model.Infromes
import com.example.model.Modulos
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class DAOInformeImpl: DAOInforme {
    private fun resultToRowInforme (row: ResultRow) = Informe(
        idInforme = row[Infromes.idInforme],
        idAlumno = row[Infromes.idAlumno],
        idModulo = row[Infromes.idModulo],
        idCompetencia = row[Infromes.idCompetencia],
        fechaGeneracion = row[Infromes.fechaGeneracion],
        notaFinal = row[Infromes.notaFinal],
    )

    override suspend fun selectAllInformes(): List<Informe> = DataBaseConnection.dbQuery {
        Modulos.selectAll().map(::resultToRowInforme)
    }

    override suspend fun selectInformePorId(idAlumno: Int, idModulo: Int, idCompetencia: Int): List<Informe> = DataBaseConnection.dbQuery {
        Infromes.select {
            (Infromes.idAlumno eq idAlumno) and
                    (Infromes.idModulo eq idModulo) and
                    (Infromes.idCompetencia eq idCompetencia)
        }.map(::resultToRowInforme)
    }

    override suspend fun insertInforme(informe: Informe) {
        transaction {
            Infromes.insert {
                it[idAlumno] = informe.idAlumno
                it[idModulo] = informe.idModulo
                it[idCompetencia] = informe.idCompetencia
                it[fechaGeneracion] = informe.fechaGeneracion
                it[notaFinal] = informe.notaFinal
            }
        }
    }

}

val daoInforme: DAOInforme = DAOInformeImpl().apply{}