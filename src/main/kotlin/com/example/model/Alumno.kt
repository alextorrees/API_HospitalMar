package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class Alumno (
    val idUsuario: Int,
    val contrasenya: String,
    val codCiclo: Int,
    val numModulo: Int,
    val identificador: String,
    val correo: Int,
    val nombre: String,
    val apellido1: String,
    val apellido2: String,
    val dni: String
)

