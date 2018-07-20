package com.example.wilson.estudiantemateria

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.onesignal.OneSignal
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    lateinit var usuarios: ArrayList<Usuario>
    var estadoIngresoSistema = 0
    lateinit var valorRol:String
    lateinit var usuarioActual:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.DEBUG, OneSignal.LOG_LEVEL.DEBUG)

        btn_login.setOnClickListener { v: View? ->
            irActividadRgistrar()
        }

    }

    fun irActividadRgistrar(){

        val rolUsuario = spinnerRol.selectedItem.toString()
        val usernameUsuario = txtUsername.text.toString()
        val passwordUsuario = txtPassword.text.toString()


        usuarios = BaseDatosUsuario.getList()

        for (datos in usuarios){

            if (datos.rol.equals(rolUsuario,true) && datos.username.equals(usernameUsuario,true) && datos.password.equals(passwordUsuario,true)){
                estadoIngresoSistema = 1
                valorRol = datos.rol
                usuarioActual = datos.username
            }

        }


        if (estadoIngresoSistema==1){
            Toast.makeText(this,"Bienvenido: $usuarioActual",Toast.LENGTH_SHORT).show()
            val intent = Intent(this, RegistrarUsuarios::class.java)
            intent.putExtra("valorRol",valorRol)
            startActivity(intent)
        }else{
            Toast.makeText(this,"Datos o Usuario Incorrectos",Toast.LENGTH_SHORT).show()
        }

    }
}



