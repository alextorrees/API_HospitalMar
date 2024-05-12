package com.example


import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.sql.Connection
import java.sql.DriverManager
import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook

fun main() {
//    val url = "jdbc:postgresql://jelani.db.elephantsql.com/fnlkzeoc"
//    val user = "fnlkzeoc"
//    val password = "b5he8YaQqUJ8TjiDhggfaSP408cYKsMR"
    val url = "jdbc:postgresql://stampy.db.elephantsql.com/ntifyrpv"
    val user = "ntifyrpv"
    val password = "W-YxsDdEWHospGKHVPxSVONr0-ey_WVr"

    val csvFileProfesor = File("/home/sjo/Escriptori/DADES/Pol Agustina/extras/CSVs/usuaris_dades_profes_i_pas.csv")
    val csvFileAlumnos = File("/home/sjo/Escriptori/DADES/Pol Agustina/extras/CSVs/usuaris_dades_alumnes.csv")
    val xlsxModulos = File("/home/sjo/Escriptori/DADES/Pol Agustina/extras/CSVs/moduls_professionals.xlsx")

    val connection = DriverManager.getConnection(url, user, password)

    println("Leyendo datos del archivo CSV 1:")
//    leerDatosCSVProfesores(connection, csvFileProfesor)
//    leerDatosCSVAlumnos(connection, csvFileAlumnos)
//    leerDatosXlsxModulos(connection, xlsxModulos)

//    val contra = getMd5DigestForPassword("")
//    println(contra)


    connection.close()
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

        insertarFilaProfesorEnBD(connection, csvline)


        lineNumber++
    }

    reader.close()
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

        insertarFilaAlumnoEnBD(connection, csvline)

        lineNumber++
    }
    reader.close()
}

fun leerDatosXlsxModulos(connection: Connection, xlsxFile: File) {
    val workbook = XSSFWorkbook(xlsxFile.inputStream())
    val sheet = workbook.getSheetAt(0)

    for (i in 2 until sheet.physicalNumberOfRows) {
        val row = sheet.getRow(i) ?: continue

        val codiCicle = row.getCell(1).stringCellValue
        val codiComplert = row.getCell(2).stringCellValue
        val nomCicle = row.getCell(3).stringCellValue
        val torn = row.getCell(4).stringCellValue
        val grup = row.getCell(5).stringCellValue
        val numModul = row.getCell(6).stringCellValue
        val nomModul = row.getCell(7).stringCellValue


        println("codiCicle: $codiCicle")
        println("codiComplert: $codiComplert")
        println("nomCicle: $nomCicle")
        println("torn: $torn")
        println("grup: $grup")
        println("numModul: $numModul")
        println("nomModul: $nomModul")
        println("")

        val xlsxline = XlsxLine(codiCicle, codiComplert, nomCicle, torn, grup, numModul, nomModul)

        // Insertar los datos en la base de datos
        insertarModuloEnBD(connection, xlsxline)
    }

    workbook.close()
}



fun insertarFilaProfesorEnBD(connection: Connection, csvline: CsvLine) {
    val insertProfesorQuery = "INSERT INTO Profesor (nombre, apellidos, correo, identificador, etiqueta, categoria, grupos, contrasenya, tutor, admin) VALUES (?, ?, ?, ?, ?, ?, ?, ?, B'0', B'0')"

    val correoSinDominio = csvline.Correu.substringBefore('@')
    val contra = getMd5DigestForPassword(correoSinDominio)

    val insertProfesorStatement = connection.prepareStatement(insertProfesorQuery)
    insertProfesorStatement.setString(1, csvline.Nom)
    insertProfesorStatement.setString(2, csvline.Cognoms)
    insertProfesorStatement.setString(3, csvline.Correu)
    insertProfesorStatement.setString(4, csvline.Codi)
    insertProfesorStatement.setString(5, csvline.Etiqueta)
    insertProfesorStatement.setString(6, csvline.EspecialitatCategoria)
    insertProfesorStatement.setString(7, csvline.Grups)
    insertProfesorStatement.setString(8, contra)

    insertProfesorStatement.executeUpdate()

    insertProfesorStatement.close()
}

fun insertarFilaAlumnoEnBD(connection: Connection, csvline: CsvLine) {
    val insertAlumnoQuery = "INSERT INTO Alumno (nombre, apellidos, correo, identificador, etiqueta, especialidad, grupos, contrasenya, idprofesor) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"

    val correoSinDominio = csvline.Correu.substringBefore('@')
    val contra = getMd5DigestForPassword(correoSinDominio)

    val insertAlumnoStatement = connection.prepareStatement(insertAlumnoQuery)
    insertAlumnoStatement.setString(1, csvline.Nom)
    insertAlumnoStatement.setString(2, csvline.Cognoms)
    insertAlumnoStatement.setString(3, csvline.Correu)
    insertAlumnoStatement.setString(4, csvline.Codi)
    insertAlumnoStatement.setString(5, csvline.Etiqueta)
    insertAlumnoStatement.setString(6, csvline.EspecialitatCategoria)
    insertAlumnoStatement.setString(7, csvline.Grups)
    insertAlumnoStatement.setString(8, contra)
    insertAlumnoStatement.setInt(9, 1)

    insertAlumnoStatement.executeUpdate()

    insertAlumnoStatement.close()
}

fun insertarModuloEnBD(connection: Connection, xlsxline: XlsxLine) {
    val insertModuloQuery = "INSERT INTO Modulo (codciclo, codcompleto, nombreciclo, turno, grupo, nummodulo, nombremodulo) VALUES (?, ?, ?, ?, ?, ?, ?)"

    val insertModuloStatement = connection.prepareStatement(insertModuloQuery)
    insertModuloStatement.setString(1, xlsxline.codiCicle)
    insertModuloStatement.setString(2, xlsxline.codiComplert)
    insertModuloStatement.setString(3, xlsxline.nomCicle)
    insertModuloStatement.setString(4, xlsxline.torn)
    insertModuloStatement.setString(5, xlsxline.grup)
    insertModuloStatement.setString(6, xlsxline.numModul)
    insertModuloStatement.setString(7, xlsxline.nomModul)

    insertModuloStatement.executeUpdate()

    insertModuloStatement.close()

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

class XlsxLine (
    val  codiCicle: String,
    val  codiComplert: String,
    val  nomCicle: String,
    val  torn: String,
    val  grup: String,
    val  numModul: String,
    val  nomModul: String,
)
