package com.example

import com.example.dao.DataBaseConnection
import com.example.plugins.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "172.23.6.122", module = Application::module)
            .start(wait = true)
}

fun Application.module() {
    DataBaseConnection.init()
    configureSecurity()
    configureSerialization()
    configureRouting()
}
