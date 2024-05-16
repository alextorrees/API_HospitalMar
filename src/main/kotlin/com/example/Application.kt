package com.example

import com.example.dao.DataBaseConnection
import com.example.plugins.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.cors.routing.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "192.168.1.25", module = Application::module)
            .start(wait = true)
}

fun Application.module() {
    DataBaseConnection.init()
    configureSecurity()
    configureSerialization()
    configureRouting()
    install(CORS) {
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Patch)
        allowMethod(HttpMethod.Delete)
        allowHeader("Content-Type")
        allowHeader("Authorization")
        anyHost()
    }
}
