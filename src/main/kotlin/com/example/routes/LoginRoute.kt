package com.example.routes

import com.example.model.getMd5Digest
import com.example.model.myRealm
import com.example.model.uploadUser
import com.example.model.userTable
import com.example.model.usuarios.Alumno
import com.example.model.usuarios.Alumnos
import com.example.model.usuarios.Profesor
import com.example.model.usuarios.Profesores
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction


fun Route.LoginRoute(){
    route("user"){
        post("/login/alumno") {
            val alumno = call.receive<Alumno>()
            userTable = uploadUser()
            val userHidden = getMd5Digest("${alumno.correo}:$myRealm:${alumno.contrasenya}")
            if (userTable.containsKey(alumno.correo) && userTable[alumno.correo]?.contentEquals(userHidden) == true) {
                call.respondText("Login correcte", status = HttpStatusCode.Accepted)
                return@post
            }
            else {
                call.respondText("Login incorrecte", status = HttpStatusCode.Conflict)
            }
        }
        post("/login/profesor") {
            val profesor = call.receive<Profesor>()
            val esValido = validarCredencialesProfesor(profesor.correo, profesor.contrasenya)

            if (esValido) {
                call.respondText("Login correcto", status = HttpStatusCode.Accepted)
            } else {
                call.respondText("Login incorrecto", status = HttpStatusCode.Conflict)
            }
        }
    }
}
fun validarCredencialesAlumno(correo: String, contrasenya: String): Boolean {
    return transaction {

        val alumno = Alumnos.select { Alumnos.correo eq correo }.singleOrNull() ?: return@transaction false

        return@transaction alumno[Alumnos.contrasenya] == contrasenya
    }
}
fun validarCredencialesProfesor(correo: String, contrasenya: String): Boolean {
    return transaction {

        val profesor = Alumnos.select { Profesores.correo eq correo }.singleOrNull() ?: return@transaction false

        return@transaction profesor[Profesores.contrasenya] == contrasenya
    }
}