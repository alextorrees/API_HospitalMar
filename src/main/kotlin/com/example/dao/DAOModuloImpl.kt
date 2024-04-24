package com.example.dao

import com.example.dao.DataBaseConnection.dbQuery
import com.example.model.Modulo
import com.example.model.Modulos
import com.example.model.usuarios.Alumno
import com.example.model.usuarios.Alumnos
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll

class DAOModuloImpl: DAOModulo {
    private fun resultToRowModulo (row: ResultRow) = Modulo(
        idModulo = row[Modulos.idModulo],
        cursoAcademico = row[Modulos.cursoAcademico],
        codCiclo =  row[Modulos.codCiclo],
        grupo = row[Modulos.grupo],
        numModulo = row[Modulos.numModulo],
        nombreModul = row[Modulos.nombreModulo]
    )

    override suspend fun selectAllModuls(): List<Modulo> = dbQuery {
        Modulos.selectAll().map(::resultToRowModulo)
    }

    override suspend fun selectModulsPorCiclos(idModulo: Int, codCiclo: Int) = dbQuery{
        Modulos.select(Modulos.codCiclo eq codCiclo).map(::resultToRowModulo)
    }
}