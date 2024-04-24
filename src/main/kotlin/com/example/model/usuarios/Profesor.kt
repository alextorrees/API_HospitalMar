package com.example.model.usuarios

import org.jetbrains.exposed.sql.Table

data class Profesor (
    val idPorfesor: Int,
    val contrasenya: String,
    val identificador: Int,
    val correo: String,
    val nombre: String,
    val apellido1: String,
    val apellido2: String,
    val dni: String,
    val admin: Boolean
)


object Profesores: Table("profesor"){
    val idProfesor = integer("idprofesor").autoIncrement()
    val nombre = varchar("nombre", 255)
    val apellido1 = varchar("apellido1", 55)
    val apellido2 = varchar("apellido2", 55)
    val dni = varchar("dni", 55)
    val correo = varchar("correo", 255)
    val identificador = varchar("identificador", 255)
    val contrasenya = varchar("contrasenya", 255)
    val tutor = bool("tutor")
    val admin = bool("admin")
}