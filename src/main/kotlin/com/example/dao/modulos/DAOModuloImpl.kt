package com.example.dao.modulos

import com.example.dao.DataBaseConnection.dbQuery
import com.example.model.modulos.Modulo
import com.example.model.modulos.Modulos
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll

class DAOModuloImpl: DAOModulo {

    /**
     * Convierte una fila de resultado en un objeto Modulo.
     * @param row Fila de resultado.
     * @return Objeto Modulo.
     */
    private fun resultToRowModulo(row: ResultRow) = Modulo(
        idModulo = row[Modulos.idModulo],
        codCiclo = row[Modulos.codCiclo],
        codCompleto = row[Modulos.codCompleto],
        nombreCiclo = row[Modulos.nombreCiclo],
        turno = row[Modulos.turno],
        grupo = row[Modulos.grupo],
        numModulo = row[Modulos.numModulo],
        nombreModulo = row[Modulos.nombreModulo]
    )

    /**
     * Obtiene todos los módulos.
     * @return Lista de módulos.
     */
    override suspend fun selectAllModuls(): List<Modulo> = dbQuery {
        Modulos.selectAll().map(::resultToRowModulo)
    }

    /**
     * Obtiene módulos por ciclo.
     * @param etiqueta Etiqueta del ciclo.
     * @return Lista de módulos que pertenecen al ciclo especificado por la etiqueta.
     */
    override suspend fun selectModulsPorCiclo(etiqueta: String): List<Modulo> {
        val primerosCuatro = etiqueta.take(4)
        return dbQuery {
            // Selecciona los módulos cuyo código completo comienza con los primeros cuatro caracteres de la etiqueta
            Modulos.select { Modulos.codCompleto.like("$primerosCuatro%") }.map(::resultToRowModulo)
        }
    }
}


val daoModulo: DAOModulo = DAOModuloImpl().apply{}

