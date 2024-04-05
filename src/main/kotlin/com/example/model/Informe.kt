package com.example.model

import io.ktor.http.*
import java.util.Date

data class Informe(
    val idInforme: Int,
    val idAlumno: Int,
    val fecha: Date,
    val texto: String,
    val grafico: Url
)
