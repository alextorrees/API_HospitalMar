package com.example.routes


import com.example.dao.usuarios.alumno.daoAlumno
import com.example.zextras.getMd5DigestForPassword
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.alumnoRouting() {
        route("/alumno") {
            /**
             * Obtener todos los alumnos.
             */
            get {
                val alumnoList = daoAlumno.allAlumno()
                if (alumnoList.isNotEmpty()) {
                    call.respond(alumnoList)
                } else {
                    call.respondText("No se han encontrado usuarios.")
                }
            }
            /**
             * Obtener un alumno por su identificador.
             */
            get("/{identificador?}") {
                val identificador = call.parameters["identificador"] ?: return@get call.respondText(
                    "Identificador incorrecto",
                    status = HttpStatusCode.BadRequest
                )
                val alumno = daoAlumno.selectAlumnoPorIdentificador(identificador) ?: return@get call.respondText(
                    "No hay alumno con este identificador",
                    status = HttpStatusCode.NotFound
                )
                call.respond(alumno)
            }
            /**
             *  Obtener los alumnos asociados a un profesor por su ID.
             */
            get("/profesor/{idProfesor}/alumnos") {
                val idProfesor = call.parameters["idProfesor"]?.toIntOrNull()
                if (idProfesor != null) {
                    val alumnoList = daoAlumno.selectAlumnoPorProfesor(idProfesor)
                    if (alumnoList.isNotEmpty()) {
                        call.respond(alumnoList)
                    } else {
                        call.respondText("No se han encontrado alumnos para el profesor con id $idProfesor.")
                    }
                } else {
                    call.respondText("El parámetro idProfesor es inválido.")
                }
            }
            /**
             * Actualizar la contraseña de un alumno.
             */
            put("/update/contrasenya/{idAlumno}/{contrasenya}") {
                val idAlumno: Int
                val contrasenya: String
                try {
                    idAlumno = call.parameters["idAlumno"]!!.toInt()
                    contrasenya = call.parameters["contrasenya"]!!

                    // Hashear la nueva contraseña
                    val contrasenyaHasheada = getMd5DigestForPassword(contrasenya)

                    // Actualizar la contraseña hasheada en la base de datos
                    if (daoAlumno.updateContrasenya(idAlumno, contrasenyaHasheada)) {
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
            /**
             * Eliminar un alumno por su ID
             */
            delete("({idalumno?})") {
                val idAlumno = call.parameters["idalumno"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
                if (daoAlumno.deleteAlumno(idAlumno.toInt())) {
                    call.respondText("Jugador borrado correctamente", status = HttpStatusCode.Accepted)
                }
            }
        }
}

