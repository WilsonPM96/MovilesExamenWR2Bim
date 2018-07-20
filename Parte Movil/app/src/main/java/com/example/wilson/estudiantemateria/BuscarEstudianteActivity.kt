package com.example.wilson.estudiantemateria

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_buscar_estudiante.*

class BuscarEstudianteActivity : AppCompatActivity() {

    lateinit var adaptador: EstudianteClienteAdapter
    lateinit var estudiantes: ArrayList<Estudiante>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buscar_estudiante)


        btnBuscarEstudianteServer.setOnClickListener { v: View? ->
            consultarDatos()
        }


    }

    fun consultarDatos(){
        if (txtBuscarEstudiante.equals("")){
            Toast.makeText(this,"Ingrese parametro de busqueda",Toast.LENGTH_SHORT).show()
        }else{
            var datoBusqueda:String = txtBuscarEstudiante.text.toString()
            

            estudiantes = BaseDatosEstudiante.buscarEstudiante(datoBusqueda)

            //Toast.makeText(this,datoBusqueda,Toast.LENGTH_SHORT).show()

            val layoutManager = LinearLayoutManager(this)
            adaptador = EstudianteClienteAdapter(estudiantes)
            recyclerView_Resultados_Estudiante.layoutManager = layoutManager
            recyclerView_Resultados_Estudiante.itemAnimator = DefaultItemAnimator()
            recyclerView_Resultados_Estudiante.adapter = adaptador
            adaptador.notifyDataSetChanged()

            registerForContextMenu(recyclerView_Resultados_Estudiante)

        }
    }
}
