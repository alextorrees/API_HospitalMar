package com.example.dao.usuarios.alumno

import com.example.dao.DataBaseConnection.dbQuery
import com.example.model.Competencias
import com.example.model.Modulos
import com.example.model.Valoraciones
import com.example.model.evaluaciones.*
import com.example.model.usuarios.Alumno
import com.example.model.usuarios.Alumnos
import com.example.routes.validarCredencialesAlumno
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.security.MessageDigest
import java.text.SimpleDateFormat
import kotlin.reflect.jvm.internal.ReflectProperties.Val


class DAOAlumnoImpl: DAOAlumno {
    private fun resultToRowAlumno (row: ResultRow) = Alumno(
        idAlumno = row[Alumnos.idAlumno],
        nombre = row[Alumnos.nombre],
        apellido1= row[Alumnos.apellido1],
        apellido2 = row[Alumnos.apellido2],
        dni = row[Alumnos.dni],
        correo = row[Alumnos.correo],
        identificador = row[Alumnos.identificador],
        idModulo = row[Alumnos.idModulo],
        codCiclo = row[Alumnos.codCiclo],
        contrasenya = row[Alumnos.contrasenya],
        idProfesor = row[Alumnos.idProfesor]
    )

    override suspend fun allAlumno(): List<Alumno> = dbQuery {
        Alumnos.selectAll().map(::resultToRowAlumno)
    }


    override suspend fun alumno(idAlumno: Int): Alumno? = dbQuery{
        Alumnos.select{Alumnos.idAlumno eq idAlumno}.map(::resultToRowAlumno).singleOrNull()
    }

    override suspend fun selectAlumnoPorCorreo(correo: String): Alumno? = dbQuery {
        Alumnos.select { Alumnos.correo eq correo }.map(::resultToRowAlumno).singleOrNull()
    }

    override suspend fun selectAlumnoPorProfesor(idProfesor: Int): List<Alumno> = dbQuery{
        Alumnos.select { Alumnos.idProfesor eq idProfesor }.map(::resultToRowAlumno)
    }

    override suspend fun insertNuevoAlumno(nombre: String, apellido1: String, apellido2: String, dni: String, correo: String, identificador: String, codCiclo: Int): Alumno? = dbQuery {
        val insertStatement = Alumnos.insert {
            it[Alumnos.nombre] = nombre
            it[Alumnos.apellido1] = apellido1
            it[Alumnos.apellido2] = apellido2
            it[Alumnos.dni] = dni
            it[Alumnos.correo] = correo
            it[Alumnos.identificador] = identificador
            it[Alumnos.codCiclo] = codCiclo
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultToRowAlumno)
    }

    fun hashPassword(password: String): String {
        val bytes = password.toByteArray()
        val digest = MessageDigest.getInstance("SHA-256")
        val hashedBytes = digest.digest(bytes)
        return hashedBytes.joinToString("") { "%02x".format(it) }
    }

    override suspend fun updateContrasenya(idAlumno: Int, contrasenya: String): Boolean = dbQuery {
        val contrasenyaEncriptada = hashPassword(contrasenya) // Encriptar la nueva contraseña
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