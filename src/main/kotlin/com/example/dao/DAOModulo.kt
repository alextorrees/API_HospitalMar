package com.example.dao

import com.example.model.Modulo

interface DAOModulo {
    suspend fun selectAllModuls(): List<Modulo>
    suspend fun selectModulsPorCiclos(idModulo: Int, codCiclo: Int): List<Modulo>
}