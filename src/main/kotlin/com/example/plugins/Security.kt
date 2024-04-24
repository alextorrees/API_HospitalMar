package com.example.plugins

import com.example.model.DigestUserTable
import com.example.model.myRealm
import com.example.model.userTable
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
    }
}