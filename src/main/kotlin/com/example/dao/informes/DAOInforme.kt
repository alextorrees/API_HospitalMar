package com.example.dao.informes

import com.example.model.informes.Informe

interface DAOInforme {
    suspend fun selectAllInformes(): List<Informe>
    suspend fun selectInformePorId(idAlumno: Int, idModulo: Int, idCompetencia: Int): List<Informe>
    suspend fun insertInforme(informe: Informe)
    suspend fun selectUltimoId(): Int?
}