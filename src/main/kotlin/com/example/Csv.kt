package com.example

import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.sql.Connection
import java.sql.DriverManager

fun main() {
    val url = "jdbc:postgresql://jelani.db.elephantsql.com/fnlkzeoc"
    val user = "fnlkzeoc"
    val password = "b5he8YaQqUJ8TjiDhggfaSP408cYKsMR"

    val csvFile1 = File("/home/sjo/Escriptori/DADES/Pol Agustina/extras/CSVs/usuaris_dades_alumnes.csv")
//    val csvFile2 = File("/home/sjo/Escriptori/DADES/Pol Agustina/extras/CSVs/usuaris_dades_profes_i_pas.csv")

    val connection = DriverManager.getConnection(url, user, password)

    println("Leyendo datos del archivo CSV 1:")
    leerDatosDesdeCSV(csvFile1)

//    insertarDatosDesdeCSV(connection, csvFile1)
//    insertarDatosDesdeCSV(connection, csvFile2)

    connection.close()
}

fun leerDatosDesdeCSV(csvFile: File) {
    val reader = BufferedReader(FileReader(csvFile))
    val header = reader.readLine()?.split(",") ?: return

    // Imprimir el encabezado
    println("Encabezado:")
    println(header.joinToString())

    // Leer y mostrar cada fila de datos
    var lineNumber = 2
    while (true) {
        val line = reader.readLine() ?: break
        val data = line.split(",")

        println("Fila $lineNumber:")
        println(data.joinToString())
        lineNumber++
    }

    reader.close()
}

fun insertarDatosDesdeCSV(connection: Connection, csvFile: File) {
    val reader = BufferedReader(FileReader(csvFile))
    val header = reader.readLine()?.split(",") ?: return

    while (true) {
        val line = reader.readLine() ?: break
        val data = line.split(",")


        insertarFilaAlumnoEnBD(connection, header, data)
//        insertarFilaProfesorEnBD(connection, header, data)
    }

    reader.close()
}

fun insertarFilaAlumnoEnBD(connection: Connection, columnas: List<String>, datos: List<String>) {
    val insertQuery = "INSERT INTO tu_tabla (${columnas.joinToString()}) VALUES (${datos.joinToString { "'" + it + "'" }})"

    val statement = connection.createStatement()
    statement.executeUpdate(insertQuery)

    statement.close()
}

fun insertarFilaProfesorEnBD(connection: Connection, columnas: List<String>, datos: List<String>) {
    val insertQuery = "INSERT INTO tu_tabla (${columnas.joinToString()}) VALUES (${datos.joinToString { "'" + it + "'" }})"

    val statement = connection.createStatement()
    statement.executeUpdate(insertQuery)

    statement.close()
}