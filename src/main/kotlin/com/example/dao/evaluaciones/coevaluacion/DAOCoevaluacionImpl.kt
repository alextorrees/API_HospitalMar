package com.example.dao.evaluaciones.coevaluacion

import com.example.model.evaluaciones.Coevaluacion
import com.example.model.evaluaciones.Coevaluaciones
import org.jetbrains.exposed.sql.ResultRow
import java.text.SimpleDateFormat

class DAOCoevaluacionImpl: DAOCoevaluacion {
    fun resultToRowCoevaluacion (row: ResultRow): Coevaluacion {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd") // ajusta el formato según cómo se almacenen las fechas en la base de datos
        val fechaEvaString = row[Coevaluaciones.fechaEva]
        val fechaEva = dateFormat.parse(fechaEvaString)

        return Coevaluacion(
            idCoevaluacion = row[Coevaluaciones.idCoevaluacion],
            idAlumno = row[Coevaluaciones.idAlumno],
            idCoevaluador = row[Coevaluaciones.idCoevaluador],
            fechaEva = fechaEva,
            puntuacion = row[Coevaluaciones.puntuacion],
            comentarios = row[Coevaluaciones.comentarios]
        )

    }
}


