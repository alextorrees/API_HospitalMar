package com.example.dao.usuarios.profesor

import com.example.dao.DataBaseConnection
import com.example.dao.usuarios.alumno.DAOAlumno
import com.example.dao.usuarios.alumno.DAOAlumnoImpl
import com.example.model.usuarios.Profesor
import com.example.model.usuarios.Profesores
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.selectAll

class DAOProfesorImpl: DAOProfesor {
    private fun resultToRowProfesor(row: ResultRow) = Profesor(
        idPorfesor = row[Profesores.idProfesor],
        nombre = row[Profesores.nombre],
        apellido1 = row[Profesores.apellido1],
        apellido2 = row[Profesores.apellido2],
        dni = row[Profesores.dni],
        correo = row[Profesores.correo],
        identificador = row[Profesores.identificador],
        contrasenya = row[Profesores.contrasenya],
        tutor = row[Profesores.tutor],
        admin = row[Profesores.admin]
    )

    override suspend fun allProfesor(): List<Profesor> = DataBaseConnection.dbQuery {
        Profesores.selectAll().map(::resultToRowProfesor)
    }
}

val daoProfesor: DAOProfesor = DAOProfesorImpl().apply{}