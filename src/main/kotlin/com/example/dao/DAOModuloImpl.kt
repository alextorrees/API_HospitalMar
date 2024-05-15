package com.example.dao

import com.example.dao.DataBaseConnection.dbQuery
import com.example.model.Modulo
import com.example.model.Modulos
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll

class DAOModuloImpl: DAOModulo {
    private fun resultToRowModulo (row: ResultRow) = Modulo(
        idModulo = row[Modulos.idModulo],
        codCiclo = row[Modulos.codCiclo],
        codCompleto = row[Modulos.codCompleto],
        nombreCiclo = row[Modulos.nombreCiclo],
        turno = row[Modulos.turno],
        grupo = row[Modulos.grupo],
        numModulo = row[Modulos.numModulo],
        nombreModulo = row[Modulos.nombreModulo],
    )

    override suspend fun selectAllModuls(): List<Modulo> = dbQuery {
        Modulos.selectAll().map(::resultToRowModulo)
    }

    override suspend fun selectModulsPorCiclo(etiqueta: String): List<Modulo> {
        val primerosCuatro = etiqueta.take(4)
        return dbQuery {
            Modulos.select { Modulos.codCompleto.like("$primerosCuatro%") }.map(::resultToRowModulo)
        }
    }
}

val daoModulo: DAOModulo = DAOModuloImpl().apply{}

