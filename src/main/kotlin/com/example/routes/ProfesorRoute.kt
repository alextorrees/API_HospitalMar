package com.example.routes

import com.example.dao.usuarios.profesor.daoProfesor
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.profesorRouting() {
//    authenticate("myAuthProf") {
        route("/profesor") {
            get {
                val profesorList = daoProfesor.allProfesor()
                if (profesorList.isNotEmpty()) {
                    call.respond(profesorList)
                } else {
                    call.respondText("No se han encontrado usuarios.")
                }
            }
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
            put("/update/contrasenya/{idProfesor}/{contrasenya}") {
                val idProfesor: Int
                val contrasenya: String
                try {
                    idProfesor = call.parameters["idProfesor"]!!.toInt()
                    contrasenya = call.parameters["contrasenya"]!!

                    // Hashear la nueva contraseña
//                    val contrasenyaHasheada = getMd5DigestForPassword(contrasenya)

                    // Actualizar la contraseña hasheada en la base de datos
                    if (daoAlumno.updateContrasenya(idProfesor, contrasenya)) {
                        call.respondText("Se ha cambiado la contraseña correctamente", status = HttpStatusCode.OK)
                    } else {
                        call.respondText(
                            "No se ha podido actualizar la contraseña",
                            status = HttpStatusCode.InternalServerError
                        )
                    }

                } catch (e: NumberFormatException) {
                    call.respondText("[ERROR] en el parámetro idAlumno.", status = HttpStatusCode.BadRequest)
                }
            }
        }
//    }
}