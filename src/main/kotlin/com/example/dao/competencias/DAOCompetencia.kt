package com.example.dao.competencias

import com.example.model.competencias.Competencia


interface DAOCompetencia {
    suspend fun selectAllCompetencias(): List<Competencia>


}