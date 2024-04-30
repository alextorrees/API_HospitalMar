package com.example.model.usuarios

import org.jetbrains.exposed.sql.Table

data class Profesor (
    val idPorfesor: Int,
    val nombre: String,
    val apellidos: String,
    val correo: String,
    val identificador: String,
    val etiqueta: String,
    val categoria: String,
    val grupos: String,
    val contrasenya: String,
    val tutor: Boolean,
    val admin: Boolean
)


object Profesores: Table("profesor"){
    val idProfesor = integer("idprofesor").autoIncrement()
    val nombre = varchar("nombre", 255)
    val apellidos = varchar("apellidos", 255)
    val correo = varchar("correo", 255)
    val identificador = varchar("identificador", 255)
    val etiqueta = varchar("etiqueta", 255)
    val categoria = varchar("categoria", 255)
    val grupos = varchar("grupos", 255)
    val contrasenya = varchar("contrasenya", 255)
    val tutor = bool("tutor")
    val admin = bool("admin")
}