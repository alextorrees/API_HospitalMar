package com.example.dao.usuarios.profesor


import com.example.dao.DataBaseConnection.dbQuery
import com.example.model.usuarios.Profesor
import com.example.model.usuarios.Profesores
import com.example.zextras.getMd5DigestForPassword
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update

class DAOProfesorImpl: DAOProfesor {

    /**
     * Convierte una fila de resultado en un objeto Profesor.
     * @param row Fila de resultado.
     * @return Objeto Profesor.
     */
    private fun resultToRowProfesor(row: ResultRow) = Profesor(
        idPorfesor = row[Profesores.idProfesor],
        nombre = row[Profesores.nombre],
        apellidos = row[Profesores.apellidos],
        correo = row[Profesores.correo],
        identificador = row[Profesores.identificador],
        etiqueta = row[Profesores.etiqueta],
        categoria = row[Profesores.categoria],
        grupos = row[Profesores.grupos],
        contrasenya = row[Profesores.contrasenya],
        tutor = row[Profesores.tutor],
        admin = row[Profesores.admin]
    )

    /**
     * Obtiene todos los profesores.
     * @return Lista de profesores.
     */
    override suspend fun allProfesor(): List<Profesor> = dbQuery {
        Profesores.selectAll().map(::resultToRowProfesor)
    }

    /**
     * Obtiene un profesor por su identificador.
     * @param identificador Identificador del profesor.
     * @return Objeto Profesor si se encuentra, o null si no se encuentra.
     */
    override suspend fun selectAlumnoPorIdentificador(identificador: String): Profesor? = dbQuery {
        Profesores.select { Profesores.identificador eq identificador }.map(::resultToRowProfesor).singleOrNull()
    }
    /**
     * Actualiza la contraseña de un profesor.
     * @param idProfesor Identificador del profesor.
     * @param contrasenya Nueva contraseña del profesor.
     * @return true si la actualización fue exitosa, false si falló.
     */
    override suspend fun updateContrasenyaProfesor(idProfesor: Int, contrasenya: String): Boolean = dbQuery {
        val contrasenyaEncriptada = getMd5DigestForPassword(contrasenya) // Encriptar la nueva contraseña
        Profesores.update({ Profesores.idProfesor eq idProfesor }) {
            it[Profesores.contrasenya] = contrasenyaEncriptada // Almacenar la contraseña encriptada en la base de datos
        } < 0
    }

}


val daoProfesor: DAOProfesor = DAOProfesorImpl().apply{}