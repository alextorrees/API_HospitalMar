package com.example.dao.competencias

import com.example.dao.DataBaseConnection.dbQuery
import com.example.model.competencias.Competencia
import com.example.model.competencias.Competencias
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.selectAll

class DAOCompetenciaImpl: DAOCompetencia {

    /**
     * Convierte una fila de resultado en un objeto Competencia.
     * @param row Fila de resultado.
     * @return Objeto Competencia.
     */
    private fun resultToRowCompetencia(row: ResultRow) = Competencia (
        idCompetencia = row[Competencias.idCompetencia],
        nombreCompetencia = row[Competencias.nombreCompetencia],
        pregunta1 = row[Competencias.pregunta1],
        pregunta2 = row[Competencias.pregunta2],
        pregunta3 = row[Competencias.pregunta3],
        pregunta4 = row[Competencias.pregunta4],
        descripcion = row[Competencias.descripcion]
    )

    /**
     * Obtiene todas las competencias.
     * @return Lista de competencias.
     */
    override suspend fun selectAllCompetencias(): List<Competencia> = dbQuery {
        Competencias.selectAll().map(::resultToRowCompetencia)
    }

}


val daoCompetencia: DAOCompetencia = DAOCompetenciaImpl().apply{}
