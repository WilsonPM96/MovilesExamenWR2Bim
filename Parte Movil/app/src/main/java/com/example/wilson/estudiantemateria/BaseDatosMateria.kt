package com.example.wilson.estudiantemateria

import android.os.StrictMode
import android.util.Log
import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import com.github.kittinunf.fuel.httpDelete
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.httpPut

class BaseDatosMateria{
    companion object {

        fun insertarMateria(materia:Materia){
            "http://192.168.100.189:1337/Materia".httpPost(listOf("nombre" to materia.nombre, "codigo" to materia.codigo, "descripcion" to materia.descripcion, "activo" to materia.activo, "fechaCreacion" to materia.fechaCreacion,"numeroHorasPorSemana" to materia.numeroHorasPorSemana,"imagenMateria" to materia.imagenMateria,"estudianteId" to materia.estudianteId ))
                    .responseString { request, _, result ->
                        Log.d("http-ejemplo", request.toString())
                    }
        }

        fun eliminarMateria(id: Int) {
            "http://192.168.100.189:1337/Materia/$id".httpDelete()
                    .responseString { request, response, result ->
                        Log.d("http-ejemplo", request.toString())
                    }
        }

        fun actualizarMateria(materia: Materia) {
            "http://192.168.100.189:1337/Materia/${materia.id}".httpPut(listOf("nombre" to materia.nombre, "codigo" to materia.codigo, "descripcion" to materia.descripcion, "activo" to materia.activo, "fechaCreacion" to materia.fechaCreacion, "numeroHorasPorSemana" to materia.numeroHorasPorSemana))
                    .responseString { request, _, result ->
                        Log.d("http-ejemplo", request.toString())
                    }
        }

        fun getMateriaList(estudianteId: Int): ArrayList<Materia> {
            val materia: ArrayList<Materia> = ArrayList()
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
            val (request, response, result) = "http://192.168.100.189:1337/Materia?estudianteId=$estudianteId".httpGet().responseString()
            val jsonStringMateria = result.get()

            val parser = Parser()
            val stringBuilder = StringBuilder(jsonStringMateria)
            val array = parser.parse(stringBuilder) as JsonArray<JsonObject>

            array.forEach {
                val id = it["id"] as Int
                val nombre = it["nombre"] as Int
                val codigo = it["codigo"] as String
                val descripcion = it["descripcion"] as String
                val activo = it["activo"] as String
                val fechaCreacion = it["fechaCreacion"] as String
                val numeroHorasPorSemana = it["numeroHorasPorSemana"] as Int
                val imagenMateria = it["imagenMateria"] as String
                val materias = Materia(id,nombre,codigo,descripcion,activo,fechaCreacion,numeroHorasPorSemana,imagenMateria,estudianteId,0,0)
                materia.add(materias)
            }
            return materia
        }




    }
}