package com.example.routes

import com.example.dao.daoModulo
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.moduloRouting() {
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
            get("/{etiqueta?}"){
                val etiqueta = call.parameters["etiqueta"]

                if (etiqueta != null){
                    val moduloList = daoModulo.selectModulsPorCiclo(etiqueta)
                    if (moduloList.isNotEmpty()){
                        call.respond(moduloList)
                    }else{
                        call.respondText("No se han encontrado modulos con codigo $etiqueta")
                    }
                }else{
                    call.respondText("El parámetro etiqueta es inválido")
                }
            }
        }
//    }
}