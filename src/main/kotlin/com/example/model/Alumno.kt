package com.example.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table


@Serializable
data class Alumno (
    val idAlumno: Int,
    val nombre: String,
    val apellido1: String,
    val apellido2: String,
    val dni: String,
    val correo: String,
    val identificador: String,
    val idModulo: Int,
    val codCiclo: Int,
    val contrasenya: String,
    val idProfesor: Int,
)

object Alumnos: Table("alumno"){
    val idAlumno = integer("idalumno").autoIncrement()
    val nombre = varchar("nombre", 255)
    val apellido1 = varchar("apellido1", 55)
    val apellido2 = varchar("apellido2", 55)
    val dni = varchar("dni", 55)
    val correo = varchar("correo", 255)
    val identificador = varchar("identificador", 255)
    val idModulo = integer("idmodul").references(Modulos.idModulo)
    val codCiclo = integer("codciclo")
    val contrasenya = varchar("contrasenya", 255)
    val idProfesor = integer("idprofesor").references(Profesores.idProfesor)

    override val primaryKey = PrimaryKey(idAlumno)

}

