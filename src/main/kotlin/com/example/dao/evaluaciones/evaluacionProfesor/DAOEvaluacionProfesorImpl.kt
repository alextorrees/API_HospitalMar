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

class DAOEvaluacionProfesorImpl: DAOEvaluacionProfesor {
    private fun resultToRowEvaluacionProfesor(row: ResultRow)= EvaluacionProfesor (
            idEvaluacionProfesor = row[EvaluacionesProfesor.idEvaluacionProfesor],
            idProfesor = row[EvaluacionesProfesor.idProfesor],
            idAlumno = row[EvaluacionesProfesor.idAlumno],
            fechaEva = row[EvaluacionesProfesor.fechaEva],
            puntuacion = row[EvaluacionesProfesor.puntuacion],
            comentarios = row[EvaluacionesProfesor.comentarios]
    )

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

    override suspend fun selectEvaluacionesProfesor(idAlumno: Int): List<EvaluacionProfesor> = dbQuery {
        EvaluacionesProfesor.select { EvaluacionesProfesor.idAlumno eq idAlumno }.map(::resultToRowEvaluacionProfesor)
    }

}

val daoEvaluacionProfesor: DAOEvaluacionProfesor = DAOEvaluacionProfesorImpl().apply{}