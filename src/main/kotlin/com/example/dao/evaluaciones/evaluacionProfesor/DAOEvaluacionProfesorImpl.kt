package com.example.dao.evaluaciones.evaluacionProfesor

import com.example.model.evaluaciones.EvaluacionProfesor
import com.example.model.evaluaciones.EvaluacionesProfesor
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import java.text.SimpleDateFormat

class DAOEvaluacionProfesorImpl: DAOEvaluacionProfesor {

    fun resultToRowEvaluacionProfesor(row: ResultRow): EvaluacionProfesor {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd") // ajusta el formato según cómo se almacenen las fechas en la base de datos
        val fechaEvaString = row[EvaluacionesProfesor.fechaEva]
        val fechaEva = dateFormat.parse(fechaEvaString)

        return EvaluacionProfesor(
            idEvaluacionProfesor = row[EvaluacionesProfesor.idEvaluacionProfesor],
            idProfesor = row[EvaluacionesProfesor.idProfesor],
            idAlumno = row[EvaluacionesProfesor.idAlumno],
            fechaEva = fechaEva,
            puntuacion = row[EvaluacionesProfesor.puntuacion],
            comentarios = row[EvaluacionesProfesor.comentarios]
        )
    }
    override suspend fun anadirEvaluacionProfesor(idProfesor: Int, idAlumno: Int, fechaEva: String, puntuacion: Int, comentarios: String) {
        val insertStatement = EvaluacionesProfesor.insert {
            it[EvaluacionesProfesor.idProfesor] = idProfesor
            it[EvaluacionesProfesor.idAlumno] = idAlumno
            it[EvaluacionesProfesor.fechaEva] = fechaEva
            it[EvaluacionesProfesor.puntuacion] = puntuacion
            it[EvaluacionesProfesor.comentarios] = comentarios
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultToRowEvaluacionProfesor)
    }
}