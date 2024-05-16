package com.example.dao.informes

import com.example.model.informes.InsertNota
import com.example.model.informes.Nota

interface DAONota {
    suspend fun selectAllNotas(): List<Nota>
    suspend fun selectNotasPorId(idInfomre: Int): List<Nota>
    suspend fun insertNota(notaType: InsertNota)
}