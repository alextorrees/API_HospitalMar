package com.example.dao

import com.example.dao.DataBaseConnection.dbQuery
import com.example.model.Competencia
import com.example.model.Competencias
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.selectAll

class DAOCompetenciaImpl: DAOCompetencia {

    private fun resultToRowCompetencia(row: ResultRow) = Competencia (
        idCompetencia = row[Competencias.idCompetencia],
        nombreCompetencia = row[Competencias.nombreCompetencia],
        pregunta1 = row[Competencias.pregunta1],
        pregunta2 = row[Competencias.pregunta2],
        pregunta3 = row[Competencias.pregunta3],
        pregunta4 = row[Competencias.pregunta4],
        descripcion = row[Competencias.descripcion]
    )

    override suspend fun selectAllCompetencias(): List<Competencia> = dbQuery {
        Competencias.selectAll().map(::resultToRowCompetencia)
    }

}

val daoCompetencia: DAOCompetencia = DAOCompetenciaImpl().apply{}
