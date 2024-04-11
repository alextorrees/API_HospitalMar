package com.example.dao

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

object DataBaseConnection {
    fun init(){
        val driverClassName = "org.postgresql.Driver"
        val url = "jdbc:postgresql://34.192.34.148:5432/hmar"
        val user = "postgres"
        val password = ""
        try {
            Database.connect(url, driverClassName, user, password)
            println("Conexión a la base de datos establecida correctamente.")
        } catch (e: Exception) {
            println("Error al conectar a la base de datos: ${e.message}")
        }
    }
    suspend fun <T> dbQuery(block: suspend  () -> T): T {
        return newSuspendedTransaction(Dispatchers.IO) {
            block()
        }
    }
}