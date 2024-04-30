package com.example.dao.evaluaciones.evaluacionProfesor

import com.example.dao.DataBaseConnection.dbQuery
import com.example.dao.DataBaseConnection
import com.example.dao.usuarios.alumno.DAOAlumno
import com.example.dao.usuarios.alumno.DAOAlumnoImpl
import com.example.model.evaluaciones.EvaluacionProfesor
import com.example.model.evaluaciones.EvaluacionesProfesor
import com.example.model.usuarios.Alumno
import com.example.model.usuarios.Alumnos
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import java.text.SimpleDateFormat
import java.time.LocalDate

/**
 * Implementación de la interfaz [DAOEvaluacionProfesor] que se encarga de realizar operaciones
 * relacionadas con las evaluaciones de profesores en la base de datos.
 */
class DAOEvaluacionProfesorImpl: DAOEvaluacionProfesor {

    /**
     * Convierte una fila [ResultRow] en un objeto [EvaluacionProfesor].
     * @param row Fila resultante de una consulta SQL.
     * @return Objeto [EvaluacionProfesor] creado a partir de la fila.
     */
    private fun resultToRowEvaluacionProfesor(row: ResultRow)= EvaluacionProfesor (
            idEvaluacionProfesor = row[EvaluacionesProfesor.idEvaluacionProfesor],
            idProfesor = row[EvaluacionesProfesor.idProfesor],
            idAlumno = row[EvaluacionesProfesor.idAlumno],
            fechaEva = row[EvaluacionesProfesor.fechaEva],
            puntuacion = row[EvaluacionesProfesor.puntuacion],
            comentarios = row[EvaluacionesProfesor.comentarios]
    )

    /**
     * Añade una nueva evaluación de profesor a la base de datos.
     * @param idProfesor ID del profesor asociado a la evaluación.
     * @param idAlumno ID del alumno asociado a la evaluación.
     * @param fechaEva Fecha de la evaluación.
     * @param puntuacion Puntuación asignada en la evaluación.
     * @param comentarios Comentarios asociados a la evaluación.
     */
    override suspend fun anadirEvaluacionProfesor(idProfesor: Int, idAlumno: Int, fechaEva: LocalDate, puntuacion: Int, comentarios: String) {
        val insertStatement = EvaluacionesProfesor.insert {
            it[EvaluacionesProfesor.idProfesor] = idProfesor
            it[EvaluacionesProfesor.idAlumno] = idAlumno
            it[EvaluacionesProfesor.fechaEva] = fechaEva
            it[EvaluacionesProfesor.puntuacion] = puntuacion
            it[EvaluacionesProfesor.comentarios] = comentarios
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultToRowEvaluacionProfesor)
    }

    /**
     * Selecciona todas las evaluaciones de profesor asociadas a un alumno específico.
     * @param idAlumno ID del alumno para el cual se seleccionarán las evaluaciones de profesor.
     * @return Lista de [EvaluacionProfesor] asociadas al alumno especificado.
     */
    override suspend fun selectEvaluacionesProfesor(idAlumno: Int): List<EvaluacionProfesor> = dbQuery {
        EvaluacionesProfesor.select { EvaluacionesProfesor.idAlumno eq idAlumno }.map(::resultToRowEvaluacionProfesor)
    }

}

/**
 * Objeto de acceso a datos (DAO) para las evaluaciones de profesor.
 */
val daoEvaluacionProfesor: DAOEvaluacionProfesor = DAOEvaluacionProfesorImpl().apply{}