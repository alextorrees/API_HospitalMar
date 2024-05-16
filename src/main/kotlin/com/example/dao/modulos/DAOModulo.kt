package com.example.dao.modulos

import com.example.model.modulos.Modulo

interface DAOModulo {
    suspend fun selectAllModuls(): List<Modulo>

    suspend fun selectModulsPorCiclo(etiqueta: String): List<Modulo>
}