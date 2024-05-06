package com.example.routes

import com.example.dao.daoModulo
import com.example.dao.usuarios.alumno.daoAlumno
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.ModuloRouting() {
//    authenticate("myAuth"){
        route("/modulo") {
            get {
                val moduloList = daoModulo.selectAllModuls()
                if (moduloList.isNotEmpty()) {
                    call.respond(moduloList)
                } else {
                    call.respondText("No se han encontrado modulos.")
                }
            }
            get("/{idModulo?}/{codCiclo?}") {
                val idModulo = call.parameters["idModulo"] ?.toIntOrNull()
                val codCiclo = call.parameters["codCiclo"] ?.toIntOrNull()
                if (idModulo != null && codCiclo != null){
                    val moduloList = daoModulo.selectModulsPorCiclos(idModulo, codCiclo)
                    if (moduloList.isNotEmpty()) {
                        call.respond(moduloList)
                    }else{
                        call.respondText("No se han encontrado modulos con id $idModulo y codigo ciclo $codCiclo.")
                    }
                }else{
                    call.respondText("El parámetro idModulo o codCiclo es inválido")
                }
            }
        }
//    }
}