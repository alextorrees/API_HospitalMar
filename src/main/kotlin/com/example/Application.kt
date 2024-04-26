package com.example

import com.example.dao.DataBaseConnection
import com.example.plugins.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "172.23.6.124", module = Application::module)
            .start(wait = true)
}

fun Application.module() {
    DataBaseConnection.init()
    configureSecurity()
    configureSerialization()
    configureRouting()
//    install(CORS) {
//        allowMethod(HttpMethod.Options)
//        allowMethod(HttpMethod.Put)
//        allowMethod(HttpMethod.Post)
//        allowMethod(HttpMethod.Patch)
//        allowMethod(HttpMethod.Delete)
//        allowHeader("Content-Type")
//        allowHeader("Authorization")
//        anyHost() // @TODO: Don't do this in production if possible. Try to limit it.
//    }
}
