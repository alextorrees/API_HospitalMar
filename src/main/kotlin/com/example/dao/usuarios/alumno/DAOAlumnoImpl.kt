package com.example.dao.usuarios.alumno

import com.example.dao.DataBaseConnection.dbQuery
import com.example.zextras.getMd5DigestForPassword
import com.example.model.usuarios.Alumno
import com.example.model.usuarios.Alumnos
import org.jetbrains.exposed.sql.*



class DAOAlumnoImpl: DAOAlumno {

    /**
     * Convierte una fila de resultados en un objeto Alumno.
     * @param row Fila de resultado.
     * @return Objeto Alumno.
     */
    private fun resultToRowAlumno(row: ResultRow) = Alumno(
        idAlumno = row[Alumnos.idAlumno],
        nombre = row[Alumnos.nombre],
        apellidos= row[Alumnos.apellidos],
        correo = row[Alumnos.correo],
        identificador = row[Alumnos.identificador],
        etiqueta = row[Alumnos.etiqueta],
        especialidad = row[Alumnos.especialidad],
        grupos = row[Alumnos.grupos],
        contrasenya = row[Alumnos.contrasenya],
        idProfesor = row[Alumnos.idProfesor]
    )

    /**
     * Obtiene todos los alumnos.
     * @return Lista de alumnos.
     */
    override suspend fun allAlumno(): List<Alumno> = dbQuery {
        Alumnos.selectAll().map(::resultToRowAlumno)
    }

    /**
     * Obtiene un alumno por su identificador.
     * @param idAlumno Identificador del alumno.
     * @return Objeto Alumno si se encuentra, o null si no se encuentra.
     */
    override suspend fun alumno(idAlumno: Int): Alumno? = dbQuery{
        Alumnos.select{Alumnos.idAlumno eq idAlumno}.map(::resultToRowAlumno).singleOrNull()
    }

    /**
     * Obtiene un alumno por su identificador.
     * @param identificador Identificador del alumno.
     * @return Objeto Alumno si se encuentra, o null si no se encuentra.
     */
    override suspend fun selectAlumnoPorIdentificador(identificador: String): Alumno? = dbQuery {
        Alumnos.select { Alumnos.identificador eq identificador }.map(::resultToRowAlumno).singleOrNull()
    }

    /**
     * Obtiene alumnos por el identificador del profesor al que pertenecen.
     * @param idProfesor Identificador del profesor.
     * @return Lista de alumnos que pertenecen al profesor especificado.
     */
    override suspend fun selectAlumnoPorProfesor(idProfesor: Int): List<Alumno> = dbQuery{
        Alumnos.select { Alumnos.idProfesor eq idProfesor }.map(::resultToRowAlumno)
    }

    /**
     * Actualiza la contraseña de un alumno.
     * @param idAlumno Identificador del alumno.
     * @param contrasenya Nueva contraseña del alumno.
     * @return true si la actualización fue exitosa, false si falló.
     */
    override suspend fun updateContrasenya(idAlumno: Int, contrasenya: String): Boolean = dbQuery {
        val contrasenyaEncriptada = getMd5DigestForPassword(contrasenya) // Encriptar la nueva contraseña
        Alumnos.update({ Alumnos.idAlumno eq idAlumno }) {
            it[Alumnos.contrasenya] = contrasenyaEncriptada // Almacenar la contraseña encriptada en la base de datos
        } < 0
    }

    /**
     * Elimina un alumno de la base de datos.
     * @param idAlumno Identificador del alumno.
     * @return true si la eliminación fue exitosa, false si falló.
     */
    override suspend fun deleteAlumno(idAlumno: Int): Boolean = dbQuery {
        Alumnos.deleteWhere { Alumnos.idAlumno eq idAlumno } < 0
    }

}

val daoAlumno: DAOAlumno = DAOAlumnoImpl().apply{}