package com.example.dao.usuarios.profesor

import com.example.dao.DataBaseConnection
import com.example.dao.DataBaseConnection.dbQuery
import com.example.model.usuarios.Alumnos

import com.example.model.usuarios.Profesor
import com.example.model.usuarios.Profesores
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll

class DAOProfesorImpl: DAOProfesor {
    private fun resultToRowProfesor(row: ResultRow) = Profesor(
        idPorfesor = row[Profesores.idProfesor],
        nombre = row[Profesores.nombre],
        apellidos = row[Profesores.apellidos],
        correo = row[Profesores.correo],
        identificador = row[Profesores.identificador],
        etiqueta = row[Profesores.etiqueta],
        categoria = row[Profesores.categoria],
        grupos = row[Profesores.grupos],
        contrasenya = row[Profesores.contrasenya],
        tutor = row[Profesores.tutor],
        admin = row[Profesores.admin]
    )

    override suspend fun allProfesor(): List<Profesor> = dbQuery {
        Profesores.selectAll().map(::resultToRowProfesor)
    }

    override suspend fun selectAlumnoPorIdentificador(identificador: String): Profesor? = dbQuery {
        Profesores.select { Profesores.identificador eq identificador }.map(::resultToRowProfesor).singleOrNull()
    }

}

val daoProfesor: DAOProfesor = DAOProfesorImpl().apply{}