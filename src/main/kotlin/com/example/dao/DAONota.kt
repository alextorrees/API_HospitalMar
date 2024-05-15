package com.example.dao

import com.example.model.Nota

interface DAONota {
    suspend fun selectAllNotas(): List<Nota>
    suspend fun selectNotasPorId(idInfomre: Int): List<Nota>
}