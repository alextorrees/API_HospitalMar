package com.example.routes

import com.example.dao.usuarios.alumno.daoAlumno
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.security.MessageDigest

fun Route.AlumnoRouting() {
    route("/alumno") {
        get {
            val alumnoList = daoAlumno.allAlumno()
            if (alumnoList.isNotEmpty()) {
                call.respond(alumnoList)
            } else {
                call.respondText("No se han encontrado usuarios.")
            }
        }
        get("/{correo?}") {
            val correo = call.parameters["correo"] ?: return@get call.respondText(
                "Correo incorrecto",
                status = HttpStatusCode.BadRequest
            )
            val alumno = daoAlumno.selectAlumnoPorCorreo(correo) ?: return@get call.respondText(
                "No hay alumno con este correo",
                status = HttpStatusCode.NotFound
            )
            call.respond(alumno)
        }
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

        get("/evaluacionesPorAlumno/{idAlumno}/{fechaInicio}/{fechaFin}") {
            val idAlumno =
                call.parameters["idAlumno"]?.toIntOrNull() ?: throw IllegalArgumentException("ID de alumno inválido")
            val fechaInicio =
                call.parameters["fechaInicio"] ?: throw IllegalArgumentException("Falta la fecha de inicio")
            val fechaFin = call.parameters["fechaFin"] ?: throw IllegalArgumentException("Falta la fecha de fin")

            val evaluaciones = daoEvaluacionAlumno.selectEvaluacionesPorAlumno(idAlumno, fechaInicio, fechaFin)
            call.respond(HttpStatusCode.OK, evaluaciones)
        }

        put("/update/contrasenya/{idAlumno}/{contrasenya}") {
            val idAlumno: Int
            val contrasenya: String
            try {
                idAlumno = call.parameters["idAlumno"]!!.toInt()
                contrasenya = call.parameters["contrasenya"]!!

                // Hashear la nueva contraseña
                val contrasenyaHasheada = hashPassword(contrasenya)

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
        delete("({idalumno?})") {
            val idAlumno = call.parameters["usu_id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
            val alumno = daoAlumno.alumno(idAlumno.toInt()) ?: return@delete call.respondText(
                "No hay ningun jugador con id $idAlumno",
                status = HttpStatusCode.NotFound
            )
            if (daoAlumno.deleteAlumno(idAlumno.toInt())) {
                call.respondText("Jugador borrado correctamente", status = HttpStatusCode.Accepted)
            }
        }
    }
}
fun hashPassword(password: String): String {
    val bytes = password.toByteArray()
    val digest = MessageDigest.getInstance("SHA-256")
    val hashedBytes = digest.digest(bytes)
    return hashedBytes.joinToString("") { "%02x".format(it) }
}