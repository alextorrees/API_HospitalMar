package com.example.routes

import com.example.dao.competencias.daoCompetencia
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.competenciaRouting() {
    route("/competencia") {
        /**
         * Obtener todas las competencias.
         */
        get {
            val competenciaList = daoCompetencia.selectAllCompetencias()
            if (competenciaList.isNotEmpty()) {
                call.respond(competenciaList)
            } else {
                call.respondText("No se han encontrado competencias.")
            }
        }
    }
}