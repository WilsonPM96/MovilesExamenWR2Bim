package com.example.wilson.estudiantemateria

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_listar_estudiantes_cliente.*

class ListarEstudianteClienteActivity : AppCompatActivity() {

    lateinit var adaptador: EstudianteClienteAdapter
    lateinit var estudiantes: ArrayList<Estudiante>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar_estudiantes_cliente)

        estudiantes = BaseDatosEstudiante.getList()


        val layoutManager = LinearLayoutManager(this)
        adaptador = EstudianteClienteAdapter(estudiantes)
        recyclerView_listarEstudiante_Clientes.layoutManager = layoutManager
        recyclerView_listarEstudiante_Clientes.itemAnimator = DefaultItemAnimator()
        recyclerView_listarEstudiante_Clientes.adapter = adaptador
        adaptador.notifyDataSetChanged()

        registerForContextMenu(recyclerView_listarEstudiante_Clientes)
    }
}
