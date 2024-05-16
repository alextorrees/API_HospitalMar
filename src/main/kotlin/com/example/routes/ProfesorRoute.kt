package com.example.routes

import com.example.dao.usuarios.profesor.daoProfesor
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.profesorRouting() {
        route("/profesor") {
            /**
             * Manejar la solicitud para obtener todos los profesores
             */
            get {
                val profesorList = daoProfesor.allProfesor()
                if (profesorList.isNotEmpty()) {
                    call.respond(profesorList)
                } else {
                    call.respondText("No se han encontrado usuarios.")
                }
            }
            /**
             * Manejar la solicitud para obtener un profesor por identificador
             */
            get("/{identificador?}") {
                val identificador = call.parameters["identificador"] ?: return@get call.respondText(
                    "Identificador incorrecto",
                    status = HttpStatusCode.BadRequest
                )
                val profesor = daoProfesor.selectAlumnoPorIdentificador(identificador) ?: return@get call.respondText(
                    "No hay alumno con este identificador",
                    status = HttpStatusCode.NotFound
                )
                call.respond(profesor)
            }
        }
}