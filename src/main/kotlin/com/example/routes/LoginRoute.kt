package com.example.routes

import com.example.model.getMd5Digest
import com.example.model.myRealm
import com.example.model.uploadUser
import com.example.model.userTable
import com.example.model.usuarios.Alumno
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Route.LoginRoute(){
    route("user"){
        post("/login") {
            val alumno = call.receive<Alumno>()
            userTable = uploadUser()
            val userRequestHidden = getMd5Digest("${alumno.correo}:$myRealm:${alumno.contrasenya}")
            val userCurrentHidden = userTable[alumno.correo]
            if (userTable.containsKey(alumno.correo) && userCurrentHidden.contentEquals(userRequestHidden)) {
                call.respondText("Login correcte", status = HttpStatusCode.Accepted)
                return@post
            }
            else {
                call.respondText("Login incorrecte", status = HttpStatusCode.Conflict)
            }
        }
    }
}