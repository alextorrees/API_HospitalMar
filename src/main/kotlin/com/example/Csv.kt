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

    val csvFileAlumnos = File("/home/sjo/Escriptori/DADES/Pol Agustina/extras/CSVs/usuaris_dades_alumnes.csv")
    val csvFileProfesor = File("/home/sjo/Escriptori/DADES/Pol Agustina/extras/CSVs/usuaris_dades_profes_i_pas.csv")

    val connection = DriverManager.getConnection(url, user, password)

    println("Leyendo datos del archivo CSV 1:")
    leerDatosCSVAlumnos(connection, csvFileAlumnos)
    leerDatosCSVProfesores(connection, csvFileProfesor)


    connection.close()
}

fun ByteArray.toHex(): String {
    return joinToString("") { "%02x".format(it) }
}

fun leerDatosCSVAlumnos(connection: Connection, csvFile: File) {
    val reader = BufferedReader(FileReader(csvFile))
    val header = reader.readLine()?.split(",") ?: return

    // Imprimir el encabezado
    println("Encabezado:")
    println(header.joinToString())

    // Leer y mostrar cada fila de datos
    var lineNumber = 2
    while (true) {
        val line = reader.readLine() ?: break
        println(line)
        val data = line.split(";")
        println(data)

        val codi = data[0].replace("\"", "")
        val etiqueta = data[1].replace("\"", "")
        val nom = data[2].replace("\"", "")
        val cognoms = data[3].replace("\"", "")
        val especialitat = data[4].replace("\"", "")
        val grups = data[5].replace("\"", "")
        val correu = data[6].replace("\"", "")


        println("Fila $lineNumber:")
        println("Codi: $codi")
        println("Etiqueta: $etiqueta")
        println("Nom: $nom")
        println("Cognoms: $cognoms")
        println("Especialitat: $especialitat")
        println("Grups: $grups")
        println("Correu-e: $correu")

        val csvline = CsvLine(codi, etiqueta, nom, cognoms, especialitat, grups, correu)

//        insertarFilaAlumnoEnBD(connection, csvline)

        lineNumber++
    }
    reader.close()
}

fun leerDatosCSVProfesores(connection: Connection, csvFile: File) {
    val reader = BufferedReader(FileReader(csvFile))
    val header = reader.readLine()

    // Imprimir el encabezado
    println("Encabezado:")
    println(header)

    // Leer y mostrar cada fila de datos
    var lineNumber = 2
    while (true) {
        val line = reader.readLine() ?: break
//        println(line)
        val data = line.split("\t")
//        println(data)

        val codi = data[0].replace("\"", "")
        val etiqueta = data[1].replace("\"", "")
        val nom = data[2].replace("\"", "")
        val cognoms = data[3].replace("\"", "")
        val categoria = data[4].replace("\"", "")
        val grups = data[5].replace("\"", "")
        val correu = data[6].replace("\"", "")


        println("Fila $lineNumber:")
        println("Codi: $codi")
        println("Etiqueta: $etiqueta")
        println("Nom: $nom")
        println("Cognoms: $cognoms")
        println("Categoria: $categoria")
        println("Grups: $grups")
        println("Correu-e: $correu")

        val csvline = CsvLine(codi, etiqueta, nom, cognoms, categoria, grups, correu)

//        insertarFilaProfesorEnBD(connection, csvline)


        lineNumber++
    }

    reader.close()
}

fun insertarFilaAlumnoEnBD(connection: Connection, csvline: CsvLine) {
    val insertAlumnoQuery = "INSERT INTO Alumno (nombre, apellidos, correo, identificador, etiqueta, especialidad, grupos, contrasenya, idprofesor) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"

    val insertAlumnoStatement = connection.prepareStatement(insertAlumnoQuery)
    insertAlumnoStatement.setString(1, csvline.Nom)
    insertAlumnoStatement.setString(2, csvline.Cognoms)
    insertAlumnoStatement.setString(3, csvline.Correu)
    insertAlumnoStatement.setString(4, csvline.Codi)
    insertAlumnoStatement.setString(5, csvline.Etiqueta)
    insertAlumnoStatement.setString(6, csvline.EspecialitatCategoria)
    insertAlumnoStatement.setString(7, csvline.Grups)
    insertAlumnoStatement.setString(8, "Contrasenya")
    insertAlumnoStatement.setInt(9, 1)

    insertAlumnoStatement.executeUpdate()

    insertAlumnoStatement.close()
}

fun insertarFilaProfesorEnBD(connection: Connection, csvline: CsvLine) {
    val insertProfesorQuery = "INSERT INTO Profesor (nombre, apellidos, correo, identificador, etiqueta, categoria, grupos, contrasenya, tutor, admin) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"



    val insertProfesorStatement = connection.prepareStatement(insertProfesorQuery)
    insertProfesorStatement.setString(1, csvline.Nom)
    insertProfesorStatement.setString(2, csvline.Cognoms)
    insertProfesorStatement.setString(3, csvline.Correu)
    insertProfesorStatement.setString(4, csvline.Codi)
    insertProfesorStatement.setString(5, csvline.Etiqueta)
    insertProfesorStatement.setString(6, csvline.EspecialitatCategoria)
    insertProfesorStatement.setString(7, csvline.Grups)
    insertProfesorStatement.setString(8, "Contrasenya")
    insertProfesorStatement.setBoolean(9, false)
    insertProfesorStatement.setBoolean(10, false)

    insertProfesorStatement.executeUpdate()

    insertProfesorStatement.close()
}

class CsvLine (
    val Codi: String,
    val Etiqueta: String,
    val Nom: String,
    val Cognoms: String,
    val EspecialitatCategoria: String,
    val Grups: String,
    val Correu: String
)
