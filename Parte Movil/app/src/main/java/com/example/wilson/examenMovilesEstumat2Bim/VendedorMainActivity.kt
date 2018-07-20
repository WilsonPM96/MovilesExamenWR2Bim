package com.example.wilson.examenMovilesEstumat2Bim

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_vendedor_main.*

class VendedorMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vendedor_main)

        btn_crear_estudiante.setOnClickListener{
            irActividadCrearEst()
        }

        btn_listar_estudiantes.setOnClickListener{
            irActividadListarEst()
        }

    }

    fun irActividadCrearEst(){
        val intent = Intent(this, CreateEstActivity::class.java)
        startActivity(intent)
    }

    fun irActividadListarEst(){
        val intent = Intent(this, EstListActivity::class.java)
        startActivity(intent)
    }


}
