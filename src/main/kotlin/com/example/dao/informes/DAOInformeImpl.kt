package com.example.dao.informes

import com.example.dao.DataBaseConnection
import com.example.model.informes.Informe
import com.example.model.informes.Infromes
import com.example.model.informes.InsertInforme
import com.example.model.modulos.Modulos
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class DAOInformeImpl: DAOInforme {

    /**
     * Convierte una fila de resultado en un objeto Informe.
     * @param row Fila de resultado.
     * @return Objeto Informe.
     */
    private fun resultToRowInforme(row: ResultRow) = Informe(
        idInforme = row[Infromes.idInforme],
        idAlumno = row[Infromes.idAlumno],
        idModulo = row[Infromes.idModulo],
        idCompetencia = row[Infromes.idCompetencia],
        fechaGeneracion = row[Infromes.fechaGeneracion],
        notaFinal = row[Infromes.notaFinal]
    )

    /**
     * Obtiene todos los informes.
     * @return Lista de informes.
     */
    override suspend fun selectAllInformes(): List<Informe> = DataBaseConnection.dbQuery {
        Modulos.selectAll().map(::resultToRowInforme)
    }

    /**
     * Obtiene informes por identificador de alumno, módulo y competencia.
     * @param idAlumno Identificador del alumno.
     * @param idModulo Identificador del módulo.
     * @param idCompetencia Identificador de la competencia.
     * @return Lista de informes que coinciden con los criterios de búsqueda.
     */
    override suspend fun selectInformePorId(idAlumno: Int, idModulo: Int, idCompetencia: Int): List<Informe> {
        return transaction {
            try {
                Infromes.select {
                    (Infromes.idAlumno eq idAlumno) and
                            (Infromes.idModulo eq idModulo) and
                            (Infromes.idCompetencia eq idCompetencia)
                }.map(::resultToRowInforme)
            } catch (e: Exception) {
                // Manejar la excepción según sea necesario, por ejemplo, registrándola o lanzándola de nuevo
                emptyList()
            }
        }
    }

    /**
     * Inserta un nuevo informe en la base de datos.
     * @param informe Informe a insertar.
     */
    override suspend fun insertInforme(informe: InsertInforme) {
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

    /**
     * Obtiene el último ID insertado en la tabla de informes.
     * @return El último ID insertado.
     */
    override suspend fun selectUltimoId(): Int? = DataBaseConnection.dbQuery {
        Infromes.slice(Infromes.idInforme.max())
            .selectAll()
            .singleOrNull()
            ?.getOrNull<Int?>(Infromes.idInforme.max())
    }
}


val daoInforme: DAOInforme = DAOInformeImpl().apply{}