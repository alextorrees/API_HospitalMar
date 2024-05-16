package com.example.routes

import com.example.dao.informes.daoInforme
import com.example.dao.informes.daoNota
import com.example.model.informes.Informe
import com.example.model.informes.InsertInforme
import com.example.model.informes.Nota
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.time.LocalDate

fun Route.informeRouting() {
    route("/informe") {
        /**
         * Obtener todos los informes.
         */
        get {
            val informeList = daoInforme.selectAllInformes()
            if (informeList.isNotEmpty()) {
                call.respond(informeList)
            } else {
                call.respondText("No se han encontrado informes.")
            }
        }
        /**
         * Obtener informes por id de alumno, id de módulo y id de competencia.
         */
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
        /**
         * Obtener notas por id de informe.
         */
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
        /**
         * Maneja las solicitudes para obtener el último ID insertado en la tabla de informes.
         */
        get("/ultimoIdInforme") {
            val ultimoId = daoInforme.selectUltimoId()
            if (ultimoId != null) {
                call.respondText(ultimoId.toString())
            } else {
                call.respondText("No se ha podido obtener el último ID insertado", status = HttpStatusCode.InternalServerError)
            }
        }
        /**
         * Insertar un nuevo informe.
         */
        post("/insertar") {
            var nuevoInforme = call.receive<InsertInforme>()

            if (nuevoInforme != null) {
                daoInforme.insertInforme(nuevoInforme)
                call.respondText("Informe insertado correctamente", status = HttpStatusCode.Created)
            } else {
                call.respondText("Parámetros inválidos para insertar un informe", status = HttpStatusCode.BadRequest)
            }
        }
        /**
         * Insertar una nueva nota.
         */
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
}

