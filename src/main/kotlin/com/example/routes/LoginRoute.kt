package com.example.routes

import com.example.model.*
import com.example.model.usuarios.Alumno
import com.example.model.usuarios.Profesor
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Route.LoginRoute(){
    route("user"){
        post("/login/alumno") {
            val alumno = call.receive<Alumno>()
            userTable = uploadAlumno()
            val userRequestHidden = getMd5Digest("${alumno.identificador}:$myRealm:${alumno.contrasenya}")
            val userCurrentHidden = userTable[alumno.identificador]
            if (userTable.containsKey(alumno.identificador) && userCurrentHidden.contentEquals(userRequestHidden)) {
                call.respondText("Login correcte", status = HttpStatusCode.Accepted)
                return@post
            }
            else {
                call.respondText("Login incorrecte", status = HttpStatusCode.Conflict)
            }
        }
        post("/login/profesor") {
            val profesor = call.receive<Profesor>()
            ProfesorTable = uploadProfesor()
            val userRequestHidden = getMd5DigestProfesor("${profesor.identificador}:$myRealm:${profesor.contrasenya}")
            val userCurrentHidden = ProfesorTable[profesor.identificador]
            if (ProfesorTable.containsKey(profesor.identificador) && userCurrentHidden.contentEquals(userRequestHidden)) {
                call.respondText("Login correcte", status = HttpStatusCode.Accepted)
                return@post
            }
            else {
                call.respondText("Login incorrecte", status = HttpStatusCode.Conflict)
            }
        }
    }
}