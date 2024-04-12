package com.example.dao

import com.example.dao.DataBaseConnection.dbQuery
import com.example.model.Alumno
import com.example.model.Alumnos
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.update
import java.security.MessageDigest

class DaoAlumnoImpl: DAOAlumno {
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

    override suspend fun añadirNuevoAlumno(nombre: String, apellido1: String, apellido2: String, dni: String, correo: String, identificador: String, codCiclo: Int): Alumno? = dbQuery {
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
    override suspend fun cambiarContrasenya(idAlumno: Int, contrasenya: String): Boolean = dbQuery {
        val contrasenyaEncriptada = hashPassword(contrasenya) // Encriptar la nueva contraseña
        Alumnos.update({ Alumnos.idAlumno eq idAlumno }) {
            it[Alumnos.contrasenya] = contrasenyaEncriptada // Almacenar la contraseña encriptada en la base de datos
        } < 0
    }

    override suspend fun deleteAlumno(idAlumno: Int): Boolean = dbQuery {
        Alumnos.deleteWhere { Alumnos.idAlumno eq idAlumno } < 0
    }
}

val daoAlumno: DAOAlumno = DaoAlumnoImpl().apply{}