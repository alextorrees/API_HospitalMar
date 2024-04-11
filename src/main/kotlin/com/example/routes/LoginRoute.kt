package com.example.routes

import com.example.model.Alumno
import com.example.model.Alumnos
import com.example.model.Profesor
import com.example.model.Profesores
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction


fun Route.LoginRoute(){
    route("user"){
        post("/login/alumno") {
            val alumno = call.receive<Alumno>()
            val esValido = validarCredencialesAlumno(alumno.correo, alumno.contrasenya)

            if (esValido) {
                call.respondText("Login correcto", status = HttpStatusCode.Accepted)
            } else {
                call.respondText("Login incorrecto", status = HttpStatusCode.Conflict)
            }
        }
        post("/login/profesor") {
            val profesor = call.receive<Profesor>()
            val esValido = validarCredencialesProfesor(profesor.correo, profesor.contrasenya)

            if (esValido) {
                call.respondText("Login correcto", status = HttpStatusCode.Accepted)
            } else {
                call.respondText("Login incorrecto", status = HttpStatusCode.Conflict)
            }
        }
    }
}
fun validarCredencialesAlumno(correo: String, contrasenya: String): Boolean {
    return transaction {

        val alumno = Alumnos.select { Alumnos.correo eq correo }.singleOrNull() ?: return@transaction false

        return@transaction alumno[Alumnos.contrasenya] == contrasenya
    }
}
fun validarCredencialesProfesor(correo: String, contrasenya: String): Boolean {
    return transaction {

        val profesor = Alumnos.select { Profesores.correo eq correo }.singleOrNull() ?: return@transaction false

        return@transaction profesor[Profesores.contrasenya] == contrasenya
    }
}