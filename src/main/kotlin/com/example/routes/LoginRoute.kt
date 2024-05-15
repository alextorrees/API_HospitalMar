package com.example.routes

import com.example.model.*
import com.example.model.usuarios.Alumno
import com.example.model.usuarios.Profesor
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Route.loginRoute(){
    route("user"){
        post("/login/alumno") {
            val alumno = call.receive<Alumno>()
            val userTable = uploadAlumno()

            val userCurrentHashedPassword = userTable[alumno.identificador]

            if (userCurrentHashedPassword != null && userCurrentHashedPassword == alumno.contrasenya) {
                call.respondText("Login correcte", status = HttpStatusCode.Accepted)
            } else {
                call.respondText("Login incorrecte", status = HttpStatusCode.Conflict)
            }
        }
        post("/login/profesor") {
            val profesor = call.receive<Profesor>()
            val profesorTable = uploadProfesor()

            val userCurrentHashedPassword = profesorTable[profesor.identificador]

            if (userCurrentHashedPassword != null && userCurrentHashedPassword == profesor.contrasenya) {
                call.respondText("Login correcte", status = HttpStatusCode.Accepted)
            } else {
                call.respondText("Login incorrecte", status = HttpStatusCode.Conflict)
            }
        }
    }
}