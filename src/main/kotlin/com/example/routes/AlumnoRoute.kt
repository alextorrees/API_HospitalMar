package com.example.routes

import com.example.dao.usuarios.alumno.daoAlumno
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.security.MessageDigest

fun Route.AlumnoRouting(){
    route("/alumno") {
        get {
                val alumnoList = daoAlumno.allAlumno()
                if (alumnoList.isNotEmpty()) {
                    call.respond(alumnoList)
                } else {
                    call.respondText ("No se han encontrado usuarios.")
                }
            }
    }
    get("/profesor/{idProfesor}/alumnos") {
        val idProfesor = call.parameters["idProfesor"]?.toIntOrNull()
        if (idProfesor != null) {
            val alumnoList = daoAlumno.selectAlumnoPorProfesor(idProfesor)
            if (alumnoList.isNotEmpty()) {
                call.respond(alumnoList)
            } else {
                call.respondText ("No se han encontrado alumnos para el profesor con id $idProfesor.")
            }
        } else {
            call.respondText ("El parámetro idProfesor es inválido.")
        }
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
                call.respondText("No se ha podido actualizar la contraseña", status = HttpStatusCode.InternalServerError)
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
// Función para encriptar la contraseña utilizando SHA-256
fun hashPassword(password: String): String {
    val bytes = password.toByteArray()
    val digest = MessageDigest.getInstance("SHA-256")
    val hashedBytes = digest.digest(bytes)
    return hashedBytes.joinToString("") { "%02x".format(it) }
}