package com.example.plugins

import com.example.routes.AlumnoRouting
import com.example.routes.LoginRoute
import com.example.routes.ModuloRouting
import com.example.routes.ProfesorRouting
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        LoginRoute()
        AlumnoRouting()
        ProfesorRouting()
        ModuloRouting()
    }
}
