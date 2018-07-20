package com.example.wilson.examenMovilesEstumat2Bim

import com.beust.klaxon.Klaxon
import java.text.SimpleDateFormat

class JsonParser {
    companion object {
        private val dateFormat = SimpleDateFormat("dd-MM-yyyy")
        private val klaxon = Klaxon()

        fun jsonToOSList(arrayOSjson: String): ArrayList<Estudiante>? {
            val sistemasOperativos = klaxon.fieldConverter(KlaxonDate::class, DateFieldConverter).parseArray<Estudiante>(arrayOSjson)
            return sistemasOperativos as ArrayList<Estudiante>
        }

        fun usuarioToJson(usuario: Usuario): String {
            val usuarioJson = """{
                    "username":"${usuario.username}",
                    "password":"${usuario.password}"
                }""".trimIndent().trim()
            return usuarioJson
        }

        fun jsonToUsuario(usuarioJson: String): Usuario? {
            val usuario = klaxon.parse<Usuario>(usuarioJson)
            return usuario
        }

        fun osToJson(estudiante: Estudiante): String {
            val estudianteJson = """{
                "nombres": "${estudiante.nombres}",
                "apellidos": "${estudiante.apellidos}",
                "fechaNacimiento": "${dateFormat.format(estudiante.fechaNacimiento)}",
                "semestreActual": "${estudiante.semestreActual}",
                "graduado": "${estudiante.graduado}"
                }""".trimIndent().trim()
            return estudianteJson
        }

        fun appToJson(materia: Materia): String {
            val materiaJson = """{
                "nombre":"${materia.nombre}",
                "codigo":"${materia.codigo}",
                "numeroHorasPorSemana":"${materia.numeroHorasPorSemana}",
                "fechaCreacion":"${dateFormat.format(materia.fechaCreacion)}",
                "descripcion": "${materia.descripcion}",
                "estudiante": "${materia.estudiante}"
            }""".trimIndent().trim()
            return materiaJson
        }

        fun jsonToMat(jsonApp: String): Materia?{
            val materia = klaxon.fieldConverter(KlaxonDate:: class, DateFieldConverter).parse<Materia>(jsonApp)
            return materia
        }

        fun jsonToMatList(arrayAppJson: String): ArrayList<Materia>? {
            val materias = klaxon.fieldConverter(KlaxonDate::class, DateFieldConverter).parseArray<Materia>(arrayAppJson)
            return materias as ArrayList<Materia>
        }

        fun fotoToJson(foto: Foto): String{
            val fotoJson = """{
                    "datos": "${foto.datos}",
                    "extension": "${foto.extension}",
                    "materia": "${foto.materia}"
                }""".trimIndent().trim()
            return fotoJson
        }

        fun jsonToFoto(jsonFoto: String): Foto?{
            val foto = klaxon.parse<Foto>(jsonFoto)
            return foto
        }


    }
}