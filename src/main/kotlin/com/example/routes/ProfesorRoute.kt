package com.example.routes

import com.example.dao.usuarios.alumno.daoAlumno
import com.example.dao.usuarios.profesor.daoProfesor
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.ProfesorRouting() {
    authenticate("myAuthProf") {
        route("/profesor") {
            get {
                val profesorList = daoProfesor.allProfesor()
                if (profesorList.isNotEmpty()) {
                    call.respond(profesorList)
                } else {
                    call.respondText("No se han encontrado usuarios.")
                }
            }
        }
    }
}