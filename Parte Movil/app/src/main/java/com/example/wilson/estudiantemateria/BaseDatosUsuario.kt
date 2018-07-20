package com.example.wilson.estudiantemateria
import android.os.StrictMode
import android.util.Log

import com.beust.klaxon.*
import com.github.kittinunf.fuel.*

class BaseDatosUsuario{
    companion object {

        fun insertarUsuario(usuario: Usuario) {
            "http://192.168.100.189:1337/Usuario".httpPost(listOf("rol" to usuario.rol, "username" to usuario.username, "password" to usuario.password))
                    .responseString { request, _, result ->
                        Log.d("http-ejemplo", request.toString())
                    }
        }

        fun getList(): ArrayList<Usuario> {
            val usuarios: ArrayList<Usuario> = ArrayList()
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
            val (request, response, result) = "http://192.168.100.189:1337/Usuario".httpGet().responseString()
            val jsonStringEstudiante = result.get()

            val parser = Parser()
            val stringBuilder = StringBuilder(jsonStringEstudiante)
            val array = parser.parse(stringBuilder) as JsonArray<JsonObject>

            array.forEach {
                val id = it["id"] as Int
                val rol = it["rol"] as String
                val username = it["username"] as String
                val password = it["password"] as String
                val usuario = Usuario(rol,username,password, 0, 0)
                usuarios.add(usuario)
            }
            return usuarios
        }
    }


}
