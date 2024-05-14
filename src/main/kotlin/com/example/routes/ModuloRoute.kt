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