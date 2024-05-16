package com.example.dao.informes

import com.example.model.informes.Informe
import com.example.model.informes.InsertInforme

interface DAOInforme {
    suspend fun selectAllInformes(): List<Informe>
    suspend fun selectInformePorId(idAlumno: Int, idModulo: Int, idCompetencia: Int): List<Informe>
    suspend fun insertInforme(informe: InsertInforme)
    suspend fun selectUltimoId(): Int?
}