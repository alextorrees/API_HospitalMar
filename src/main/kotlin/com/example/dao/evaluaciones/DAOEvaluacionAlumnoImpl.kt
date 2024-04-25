package com.example.dao.evaluaciones

import com.example.dao.DataBaseConnection.dbQuery
import com.example.dao.evaluaciones.autoevaluacion.DAOAutoevaluacionImpl
import com.example.dao.evaluaciones.coevaluacion.DAOCoevaluacionImpl
import com.example.dao.evaluaciones.evaluacionProfesor.DAOEvaluacionProfesor
import com.example.dao.evaluaciones.evaluacionProfesor.DAOEvaluacionProfesorImpl
import com.example.model.evaluaciones.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.greaterEq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.lessEq

class DAOEvaluacionAlumnoImpl: DAOEvaluacionAlumno{

    private fun resultToRowEvaluacionAlumno (row: ResultRow): EvaluacionAlumno{
        val  autoevaluacion = DAOAutoevaluacionImpl().resultToRowAutoevaluacion(row)
        val coevaluacion = DAOCoevaluacionImpl().resultToRowCoevaluacion(row)
//        val evaluacionProfesor = DAOEvaluacionProfesorImpl().resultToRowEvaluacionProfesor(row)

        return EvaluacionAlumno(autoevaluacion, coevaluacion)
    }

    override suspend fun selectEvaluacionesPorAlumno(idAlumno: Int, fechaInicio: String, fechaFin: String): List<EvaluacionAlumno> = dbQuery {
            AutoEvaluaciones.join(Coevaluaciones, JoinType.INNER, AutoEvaluaciones.idAlumno, Coevaluaciones.idAlumno)
                .join(EvaluacionesProfesor, JoinType.INNER, AutoEvaluaciones.idAlumno, EvaluacionesProfesor.idAlumno)
                .select {
                    (AutoEvaluaciones.fechaEva greaterEq  fechaInicio) and  (AutoEvaluaciones.fechaEva lessEq  fechaFin) and
                            (Coevaluaciones.fechaEva greaterEq  fechaInicio) and  (Coevaluaciones.fechaEva lessEq fechaFin) and
                            (EvaluacionesProfesor.fechaEva greaterEq  fechaInicio) and  (EvaluacionesProfesor.fechaEva lessEq fechaFin)
                }
                .map(::resultToRowEvaluacionAlumno)
    }
}



val daoEvaluacionAlumno: DAOEvaluacionAlumno = DAOEvaluacionAlumnoImpl().apply{}
