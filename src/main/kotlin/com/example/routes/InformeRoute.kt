package com.example.routes

import com.example.dao.daoInforme
import com.example.dao.daoNota
import com.example.model.Informe
import com.example.model.Nota
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.time.LocalDate

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

        post("/insertar") {
            val parameters = call.receiveParameters()
            val idAlumno = parameters["idalumno"]?.toIntOrNull()
            val idModulo = parameters["idmodulo"]?.toIntOrNull()
            val idCompetencia = parameters["idcompetencia"]?.toIntOrNull()
            val fechaGeneracion = parameters["fechageneracion"]?.let { LocalDate.parse(it) }
            val notaFinal = parameters["notafinal"]?.toIntOrNull()

            if (idAlumno != null && idModulo != null && idCompetencia != null && fechaGeneracion != null && notaFinal != null) {
                val nuevoInforme = Informe(
                    idInforme = null,
                    idAlumno = idAlumno,
                    idModulo = idModulo,
                    idCompetencia = idCompetencia,
                    fechaGeneracion = fechaGeneracion,
                    notaFinal = notaFinal
                )
                daoInforme.insertInforme(nuevoInforme)
                call.respondText("Informe insertado correctamente", status = HttpStatusCode.Created)
            } else {
                call.respondText("Parámetros inválidos para insertar un informe", status = HttpStatusCode.BadRequest)
            }
        }

        post("/insertar/nota") {
            val  parameters = call.receiveParameters()
            val idInforme = parameters["idinforme"]?.toIntOrNull()
            val nota = parameters["nota"]?.toIntOrNull()
            val comentario = parameters["comentario"]

            if (idInforme != null && nota != null && comentario != null){
                val nuevaNota = Nota(
                    idNota = null,
                    idInforme = idInforme,
                    nota = nota,
                    comentario = comentario
                )

                daoNota.insertNota(nuevaNota)
                call.respondText("Nota insertada correctamente", status = HttpStatusCode.Created)
            }else{
                call.respondText("Parámetros inválidos para insertar una nota", status = HttpStatusCode.BadRequest)
            }
        }
    }
//    }
}

