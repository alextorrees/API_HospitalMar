package com.example.model.usuarios

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class Alumno (
    val idAlumno: Int,
    val nombre: String,
    val apellidos: String,
    val correo: String,
    val identificador: String,
    val etiqueta: String,
    val especialidad: String,
    val grupos: String,
    val contrasenya: String,
    val idProfesor: Int,
)

object Alumnos: Table("alumno"){
    val idAlumno = integer("idalumno").autoIncrement()
    val nombre = varchar("nombre", 255)
    val apellidos = varchar("apellidos", 55)
    val correo = varchar("correo", 255)
    val identificador = varchar("identificador", 255)
    val etiqueta = varchar("etiqueta", 255)
    val especialidad = varchar("especialidad", 255)
    val grupos = varchar("grupos", 55)
    val contrasenya = varchar("contrasenya", 5000)
    val idProfesor = integer("idprofesor").references(Profesores.idProfesor)

    override val primaryKey = PrimaryKey(idAlumno)

}

