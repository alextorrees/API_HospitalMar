package com.example.dao

import com.example.model.Informe
import com.example.model.Infromes
import com.example.model.Modulos
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll

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

}

val daoInforme: DAOInforme = DAOInformeImpl().apply{}