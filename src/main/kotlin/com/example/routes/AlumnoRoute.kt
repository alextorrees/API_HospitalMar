package com.example.routes

import com.example.dao.evaluaciones.daoEvaluacionAlumno
import com.example.dao.evaluaciones.evaluacionProfesor.daoEvaluacionProfesor
import com.example.dao.usuarios.alumno.daoAlumno
import com.example.getMd5DigestForPassword
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.AlumnoRouting() {
//   authenticate("myAuth"){
        route("/alumno") {
            get {
                val alumnoList = daoAlumno.allAlumno()
                if (alumnoList.isNotEmpty()) {
                    call.respond(alumnoList)
                } else {
                    call.respondText("No se han encontrado usuarios.")
                }
            }
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

            get("/evaluacionProfesor/{idAlumno}") {
                val idAlumno = call.parameters["idAlumno"]?.toIntOrNull()
                if (idAlumno != null){
                    val evaluacioneProfesorList = daoEvaluacionProfesor.selectEvaluacionesProfesor(idAlumno)

                    if (evaluacioneProfesorList.isNotEmpty()){
                        call.respond(evaluacioneProfesorList)
                    }else{
                        call.respondText("No se han encontrado evaluaciones del profesor para el alumno con id $idAlumno.")
                    }
                }else{
                    call.respondText("El parámetro idAlumno es inválido.")
                }
            }

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

            delete("({idalumno?})") {
                val idAlumno = call.parameters["idalumno"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
                val alumno = daoAlumno.alumno(idAlumno.toInt()) ?: return@delete call.respondText(
                    "No hay ningun jugador con id $idAlumno",
                    status = HttpStatusCode.NotFound
                )
                if (daoAlumno.deleteAlumno(idAlumno.toInt())) {
                    call.respondText("Jugador borrado correctamente", status = HttpStatusCode.Accepted)
                }
            }
        }
//   }
}

