package com.example.dao.evaluaciones.autoevaluacion

import com.example.model.evaluaciones.AutoEvaluacion
import com.example.model.evaluaciones.AutoEvaluaciones
import com.example.model.evaluaciones.EvaluacionesProfesor
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import java.text.SimpleDateFormat

class DAOAutoevaluacionImpl: DAOAutoevaluacion {
    private fun resultToRowAutoevaluacion(row: ResultRow): AutoEvaluacion {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd") // ajusta el formato según cómo se almacenen las fechas en la base de datos
        val fechaEvaString = row[AutoEvaluaciones.fechaEva]
        val fechaEva = dateFormat.parse(fechaEvaString)

        return AutoEvaluacion(
            idAutoEvaluacion = row[AutoEvaluaciones.idAutoEvaluacion],
            idAlumno = row[AutoEvaluaciones.idAlumno],
            fechaEva = fechaEva,
            puntuacion = row[AutoEvaluaciones.puntuacion],
            comentarios = row[AutoEvaluaciones.comentarios]
        )
    }
    override suspend fun anadirAutoevaluacion(idAlumno: Int, puntuacion: Int, fechaEva: String, comentarios: String) {
        val insertStatement = AutoEvaluaciones.insert {
            it[AutoEvaluaciones.idAlumno] = idAlumno
            it[AutoEvaluaciones.fechaEva] = fechaEva
            it[AutoEvaluaciones.puntuacion] = puntuacion
            it[AutoEvaluaciones.comentarios] = comentarios
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultToRowAutoevaluacion)
    }
}