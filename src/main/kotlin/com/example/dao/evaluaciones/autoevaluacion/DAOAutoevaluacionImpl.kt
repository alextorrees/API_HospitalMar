package com.example.dao.evaluaciones.autoevaluacion

import com.example.model.evaluaciones.AutoEvaluacion
import com.example.model.evaluaciones.AutoEvaluaciones
import com.example.model.evaluaciones.EvaluacionesProfesor
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import java.text.SimpleDateFormat

class DAOAutoevaluacionImpl: DAOAutoevaluacion {
    private fun resultToRowEvaluacionProfesor(row: ResultRow): AutoEvaluacion {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd") // ajusta el formato según cómo se almacenen las fechas en la base de datos
        val fechaEvaString = row[EvaluacionesProfesor.fechaEva]
        val fechaEva = dateFormat.parse(fechaEvaString)

        return AutoEvaluacion(
            idAutoEvaluacion = row[AutoEvaluaciones.idAutoEvaluacion],
            idAlumno = row[EvaluacionesProfesor.idAlumno],
            fechaEva = fechaEva,
            puntuacion = row[EvaluacionesProfesor.puntuacion],
            comentarios = row[EvaluacionesProfesor.comentarios]
        )
    }
    override suspend fun anadirAutoevaluacion(idAlumno: Int, puntuacion: Int, fechaEva: String, comentarios: String) {
        val insertStatement = EvaluacionesProfesor.insert {
            it[EvaluacionesProfesor.idAlumno] = idAlumno
            it[EvaluacionesProfesor.fechaEva] = fechaEva
            it[EvaluacionesProfesor.puntuacion] = puntuacion
            it[EvaluacionesProfesor.comentarios] = comentarios
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultToRowEvaluacionProfesor)
    }
}