package com.example.dao.usuarios.alumno

import com.example.dao.DataBaseConnection.dbQuery
import com.example.getMd5DigestForPassword
import com.example.model.Competencias
import com.example.model.Modulos
import com.example.model.Valoraciones
import com.example.model.usuarios.Alumno
import com.example.model.usuarios.Alumnos
import org.jetbrains.exposed.sql.*



class DAOAlumnoImpl: DAOAlumno {
    private fun resultToRowAlumno (row: ResultRow) = Alumno(
        idAlumno = row[Alumnos.idAlumno],
        nombre = row[Alumnos.nombre],
        apellidos= row[Alumnos.apellidos],
        correo = row[Alumnos.correo],
        identificador = row[Alumnos.identificador],
        etiqueta = row[Alumnos.etiqueta],
        especialidad = row[Alumnos.especialidad],
        grupos = row[Alumnos.grupos],
        contrasenya = row[Alumnos.contrasenya],
        idProfesor = row[Alumnos.idProfesor]
    )

    override suspend fun allAlumno(): List<Alumno> = dbQuery {
        Alumnos.selectAll().map(::resultToRowAlumno)
    }


    override suspend fun alumno(idAlumno: Int): Alumno? = dbQuery{
        Alumnos.select{Alumnos.idAlumno eq idAlumno}.map(::resultToRowAlumno).singleOrNull()
    }

    override suspend fun selectAlumnoPorIdentificador(identificador: String): Alumno? = dbQuery {
        Alumnos.select { Alumnos.identificador eq identificador }.map(::resultToRowAlumno).singleOrNull()
    }

    override suspend fun selectAlumnoPorProfesor(idProfesor: Int): List<Alumno> = dbQuery{
        Alumnos.select { Alumnos.idProfesor eq idProfesor }.map(::resultToRowAlumno)
    }

    override suspend fun insertNuevoAlumno(nombre: String, apellidos: String, correo: String, identificador: String, etiqueta: String, especialidad: String, grupos: String, contrasenya: String, idProfesor: Int): Alumno? = dbQuery {
        val insertStatement = Alumnos.insert {
            it[Alumnos.nombre] = nombre
            it[Alumnos.apellidos] = apellidos
            it[Alumnos.correo] = correo
            it[Alumnos.identificador] = identificador
            it[Alumnos.etiqueta] = identificador
            it[Alumnos.especialidad] = identificador
            it[Alumnos.grupos] = identificador
            it[Alumnos.contrasenya] = contrasenya
            it[Alumnos.idProfesor] = idProfesor
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultToRowAlumno)
    }

    override suspend fun updateContrasenya(idAlumno: Int, contrasenya: String): Boolean = dbQuery {
        val contrasenyaEncriptada = getMd5DigestForPassword(contrasenya) // Encriptar la nueva contraseña
        Alumnos.update({ Alumnos.idAlumno eq idAlumno }) {
            it[Alumnos.contrasenya] = contrasenyaEncriptada // Almacenar la contraseña encriptada en la base de datos
        } < 0
    }

    override suspend fun deleteAlumno(idAlumno: Int): Boolean = dbQuery {
        Alumnos.deleteWhere { Alumnos.idAlumno eq idAlumno } < 0
    }

    override suspend fun selectJoin(): List<Alumno> = dbQuery {
        Valoraciones.join(Modulos, JoinType.INNER, Valoraciones.idModulo, Modulos.idModulo)
            .join(Competencias, JoinType.INNER, Valoraciones.idCompetencia, Valoraciones.idCompetencia)
            .selectAll()
            .map(::resultToRowAlumno)
    }

//    override suspend fun selectEvaluacionesPorAlumno(idAlumno: Int, fechaInicio: String, fechaFin: String): List<EvaluacionAlumno> {
//        return dbQuery {
//            (AutoEvaluaciones innerJoin Coevaluaciones innerJoin EvaluacionesProfesor)
//                .slice(
//                AutoEvaluaciones.idAutoEvaluacion,
//                AutoEvaluaciones.idAlumno,
//                AutoEvaluaciones.fechaEva,
//                AutoEvaluaciones.puntuacion,
//                AutoEvaluaciones.comentarios,
//                Coevaluaciones.idCoevaluacion,
//                Coevaluaciones.idCoevaluador,
//                Coevaluaciones.fechaEva,
//                Coevaluaciones.puntuacion,
//                Coevaluaciones.comentarios,
//                EvaluacionesProfesor.idEvaluacionProfesor,
//                EvaluacionesProfesor.idProfesor,
//                EvaluacionesProfesor.fechaEva,
//                EvaluacionesProfesor.puntuacion,
//                EvaluacionesProfesor.comentarios
//            ).select {
//                (AutoEvaluaciones.idAlumno eq idAlumno) and
//                        (Coevaluaciones.idAlumno eq idAlumno) and
//                        (EvaluacionesProfesor.idAlumno eq idAlumno) and
//                        (AutoEvaluaciones.fechaEva.between(fechaInicio, fechaFin)) and
//                        (Coevaluaciones.fechaEva.between(fechaInicio, fechaFin)) and
//                        (EvaluacionesProfesor.fechaEva.between(fechaInicio, fechaFin))
//            }.map { row ->
//                val dateFormat = SimpleDateFormat("yyyy-MM-dd")
//                val autoEvaluacionFechaEvaString = row[AutoEvaluaciones.fechaEva]
//                val autoEvaluacionFechaEva = dateFormat.parse(autoEvaluacionFechaEvaString)
//
//                val coevaluacionFechaEvaString = row[Coevaluaciones.fechaEva]
//                val coevaluacionFechaEva = dateFormat.parse(coevaluacionFechaEvaString)
//
//                val evaluacionProfesorFechaEvaString = row[EvaluacionesProfesor.fechaEva]
//                val evaluacionProfesorFechaEva = dateFormat.parse(evaluacionProfesorFechaEvaString)
//
//                EvaluacionAlumno(
//                    autoevaluacion = AutoEvaluacion(
//                        idAutoEvaluacion = row[AutoEvaluaciones.idAutoEvaluacion],
//                        idAlumno = row[AutoEvaluaciones.idAlumno],
//                        fechaEva = autoEvaluacionFechaEva,
//                        puntuacion = row[AutoEvaluaciones.puntuacion],
//                        comentarios = row[AutoEvaluaciones.comentarios]
//                    ),
//                    coevaluacion = Coevaluacion(
//                        idCoevaluacion = row[Coevaluaciones.idCoevaluacion],
//                        idAlumno = row[AutoEvaluaciones.idAlumno],
//                        idCoevaluador = row[Coevaluaciones.idCoevaluador],
//                        fechaEva = coevaluacionFechaEva,
//                        puntuacion = row[Coevaluaciones.puntuacion],
//                        comentarios = row[Coevaluaciones.comentarios]
//                    ),
//                    evaluacionProfesor = EvaluacionProfesor(
//                        idEvaluacionProfesor = row[EvaluacionesProfesor.idEvaluacionProfesor],
//                        idProfesor = row[EvaluacionesProfesor.idProfesor],
//                        idAlumno = row[EvaluacionesProfesor.idAlumno],
//                        fechaEva = evaluacionProfesorFechaEva,
//                        puntuacion = row[EvaluacionesProfesor.puntuacion],
//                        comentarios = row[EvaluacionesProfesor.comentarios]
//                    )
//                )
//            }
//        }
//    }

}

val daoAlumno: DAOAlumno = DAOAlumnoImpl().apply{}