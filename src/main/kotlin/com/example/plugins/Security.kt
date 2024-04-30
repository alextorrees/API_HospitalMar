package com.example.plugins

import com.example.model.*
import io.ktor.server.application.*
import io.ktor.server.auth.*

fun Application.configureSecurity() {
    install(Authentication) {
        digest("myAuth") {
            realm = myRealm
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
            realm = myRealmProf
            digestProvider { userName, _ ->
                ProfesorTable[userName]
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