package com.example.wilson.estudiantemateria

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import kotlinx.android.synthetic.main.activity_detalles_estudiante.*

class DetallesEstudianteClienteActivity : AppCompatActivity() {

    var estudiante: Estudiante? = null
    lateinit var materia: ArrayList<Materia>
    lateinit var adaptador: MateriaClienteAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles_estudiante_cliente)

        estudiante = intent.getParcelableExtra("detallesEstudianteCliente")

        txtShowEstudianteId.text = estudiante?.id.toString()
        txtShowNombreEstudiante.text = estudiante?.nombres
        txtShowApellidoEstudiante.text = estudiante?.apellidos
        txtShowFechaNacEstudiante.text = estudiante?.fechaNacimiento
        txtShowSemestreEstudiante.text = estudiante?.semestreActual.toString()
        txtShowGraduadoEstudiante.text = if(estudiante?.graduado == 1) "Si" else "No"



        materia = BaseDatosMateria.getMateriaList(estudiante?.id!!)
        Log.d("resultado",materia.toString())

        val layoutManager = LinearLayoutManager(this)
        adaptador = MateriaClienteAdapter(materia)
        recycler_view_materia.layoutManager = layoutManager
        recycler_view_materia.itemAnimator = DefaultItemAnimator()
        recycler_view_materia.adapter = adaptador
        adaptador.notifyDataSetChanged()

        registerForContextMenu(recycler_view_materia)
    }
}
