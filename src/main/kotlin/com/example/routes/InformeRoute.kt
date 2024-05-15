package com.example.routes

import com.example.dao.daoInforme
import com.example.dao.daoNota
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.informeRouting() {
//    authenticate("myAuth"){
    route("/informe") {
        get {
            val informeList = daoInforme.selectAllInformes()
            if (informeList.isNotEmpty()) {
                call.respond(informeList)
            } else {
                call.respondText("No se han encontrado informes.")
            }
        }
        get("/{idalumno?}/{idmodulo?}/{idcompetencia?}") {
            val idalumno = call.parameters["idalumno"]?.toIntOrNull()
            val idmodulo = call.parameters["idmodulo"]?.toIntOrNull()
            val idcompetencia = call.parameters["idcompetencia"]?.toIntOrNull()

            if (idalumno != null && idmodulo != null && idcompetencia != null ){
                val informeList = daoInforme.selectInformePorId(idalumno, idmodulo, idcompetencia)
                if (informeList.isNotEmpty()){
                    call.respond(informeList)
                }else{
                    call.respondText("No se han encontrado informes con $idalumno, $idmodulo, $idcompetencia")
                }
            }else{
                call.respondText("Los parámetros són inválidos")
            }
        }
        get("/notas/{idinforme?}") {
            val idinforme = call.parameters["idinforme"]?.toIntOrNull()

            if (idinforme != null){
                val notasList = daoNota.selectNotasPorId(idinforme)
                if (notasList.isNotEmpty()){
                    call.respond(notasList)
                }else{
                 call.respondText("No se han encontrado notas con $idinforme")
                }
            }else{
                call.respondText("El parámetro idinforme es inválido")
            }
        }
    }
//    }
}

