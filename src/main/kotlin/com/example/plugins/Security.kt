package com.example.plugins

import com.example.model.digests.DigestProfesorTable
import com.example.model.digests.DigestUserTable
import com.example.model.digests.profesorTable
import com.example.model.digests.userTable
import io.ktor.server.application.*
import io.ktor.server.auth.*

fun Application.configureSecurity() {
    install(Authentication) {
        digest("myAuth") {
            digestProvider { userName, _ ->
                userTable[userName]
            }
            validate { credentials ->
                if (credentials.userName.isNotEmpty()) {
                    DigestUserTable(credentials.userName, credentials.realm)
                } else {
                    null
                }
            }
        }
        digest("myAuthProf") {
            digestProvider { userName, _ ->
                profesorTable[userName]
            }
            validate { credentials ->
                if (credentials.userName.isNotEmpty()) {
                    DigestProfesorTable(credentials.userName, credentials.realm)
                } else {
                    null
                }
            }
        }
    }
}