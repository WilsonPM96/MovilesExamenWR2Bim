package com.example.wilson.estudiantemateria

import android.os.StrictMode
import android.util.Log
import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import com.github.kittinunf.fuel.*

class BaseDatosEstudiante{

    companion object {

        fun insertarEstudiante(estudiante:Estudiante){
            "http://192.168.100.189:1337/Estudiante".httpPost(listOf("nombres" to estudiante.nombres, "apellidos" to estudiante.apellidos, "fechaNacimiento" to estudiante.fechaNacimiento, "semestreActual" to estudiante.semestreActual, "graduado" to estudiante.graduado))
                    .responseString { request, _, result ->
                        Log.d("http-ejemplo", request.toString())
                    }
        }

        fun eliminarEstudiante(id: Int) {
            "http://192.168.100.189:1337/Estudiante/$id".httpDelete()
                    .responseString { request, response, result ->
                        Log.d("http-ejemplo", request.toString())
                    }
        }

        fun actualizarEstudiante(estudiante: Estudiante) {
            "http://192.168.100.189:1337/Estudiante/${estudiante.id}".httpPut(listOf("nombres" to estudiante.nombres, "apellidos" to estudiante.apellidos, "fechaNacimiento" to estudiante.fechaNacimiento, "semestreActual" to estudiante.semestreActual, "graduado" to estudiante.graduado))
                    .responseString { request, _, result ->
                        Log.d("http-ejemplo", request.toString())
                    }
        }

        fun getList(): ArrayList<Estudiante> {
            val estudiantes: ArrayList<Estudiante> = ArrayList()
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
            val (request, response, result) = "http://192.168.100.189:1337/Estudiante".httpGet().responseString()
            val jsonStringEstudiante = result.get()

            val parser = Parser()
            val stringBuilder = StringBuilder(jsonStringEstudiante)
            val array = parser.parse(stringBuilder) as JsonArray<JsonObject>

            array.forEach {
                val id = it["id"] as Int
                val nombres = it["nombres"] as String
                val apellidos = it["apellidos"] as String
                val fechaNacimiento = it["fechaNacimiento"] as String
                val semestreActual = it["semestreActual"] as Int
                val graduado = it["graduado"] as Int
                val estudiante = Estudiante(id, nombres, apellidos, fechaNacimiento, semestreActual, graduado, 0, 0)
                estudiantes.add(estudiante)
            }
            return estudiantes
        }

        fun buscarEstudiante(nombre:String): ArrayList<Estudiante> {
            val estudiantes: ArrayList<Estudiante> = ArrayList()
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
            val (request, response, result) = "http://192.168.100.189:1337/Estudiante?nombres=${nombre}".httpGet().responseString()
            val jsonStringEstudiante = result.get()

            val parser = Parser()
            val stringBuilder = StringBuilder(jsonStringEstudiante)
            val array = parser.parse(stringBuilder) as JsonArray<JsonObject>

            array.forEach {
                val id = it["id"] as Int
                val nombres = it["nombres"] as String
                val apellidos = it["apellidos"] as String
                val fechaNacimiento = it["fechaNacimiento"] as String
                val semestreActual = it["semestreActual"] as Int
                val graduado = it["graduado"] as Int
                val estudiante = Estudiante(id, nombres, apellidos, fechaNacimiento, semestreActual, graduado, 0, 0)
                estudiantes.add(estudiante)
            }
            return estudiantes
        }

    }
}