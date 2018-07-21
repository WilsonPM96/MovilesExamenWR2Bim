package com.example.wilson.estudiantemateria

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_registrar_usuarios.*


class RegistrarUsuarios : AppCompatActivity() {

    lateinit var rol:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_usuarios)


        rol = intent.getStringExtra("valorRol")


        if (rol.equals("VENDEDOR",true)){
            btnBuscarEstudiantes.visibility = View.INVISIBLE

        }else if (rol.equals("DELIVERY",true)){
            btnBuscarEstudiantes.visibility = View.INVISIBLE
            btnCrearEstudiante.visibility = View.INVISIBLE
            btnListarEstudiante.visibility = View.INVISIBLE
            irActividadDelivery()

        }else{
            btnCrearEstudiante.visibility = View.INVISIBLE
            btnListarEstudiante.visibility = View.INVISIBLE
        }


      btnCrearEstudiante.setOnClickListener { v: View? ->
          irEstudianteActivity()
      }

        btnListarEstudiante.setOnClickListener { v: View? ->
            irListarEstudianteActivity()
        }

        btnBuscarEstudiantes.setOnClickListener { v: View? ->
            irBuscarEstudianteActivity()
        }

        btn_salirsistema.setOnClickListener { v: View? ->
            irMainActivity()
        }


    }

    fun irEstudianteActivity(){
        val intent = Intent(this, EstudianteActivity::class.java)
        intent.putExtra("tipo", "Create")
        startActivity(intent)
    }

    fun irListarEstudianteActivity(){
        val intent = Intent(this, ListarEstudiantesActivity::class.java)
        startActivity(intent)
    }

    fun irActividadDelivery(){
        val intent = Intent(this,DeliveryActivity::class.java)
        startActivity(intent)

    }

    fun irBuscarEstudianteActivity(){
        val intent = Intent(this,BuscarEstudianteActivity::class.java)
        startActivity(intent)

    }

    fun irMainActivity(){
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }


}
