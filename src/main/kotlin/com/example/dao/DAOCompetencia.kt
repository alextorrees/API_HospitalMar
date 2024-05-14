package com.example.dao

import com.example.model.Competencia


interface DAOCompetencia {
    suspend fun selectAllCompetencias(): List<Competencia>


}