package com.example.dao

import com.example.model.Informe

interface DAOInforme {
    suspend fun selectAllInformes(): List<Informe>
    suspend fun selectInformePorId(idAlumno: Int, idModulo: Int, idCompetencia: Int): List<Informe>
}