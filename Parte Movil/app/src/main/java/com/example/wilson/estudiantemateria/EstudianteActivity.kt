package com.example.wilson.estudiantemateria

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_estudiantes.*

class EstudianteActivity : AppCompatActivity() {

    var estudiante: Estudiante? = null
    var tipo = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estudiantes)

        val type = intent.getStringExtra("tipo")

        if (type.equals("Edit")) {
            textViewEntreador.text = "Editar Estudiante"
            estudiante = intent.getParcelableExtra("estudiante")
            fillFields()
            tipo = true
        }

        btnGuardarEstudiante.setOnClickListener { v: View? ->
            crearEstudiante()
        }
    }

    fun fillFields() {
        txtNombreEstudiante.setText(estudiante?.nombres)
        txtApellidoEstudiante.setText(estudiante?.apellidos)
        txtFechaNacimientoEstudiante.setText(estudiante?.fechaNacimiento)
        txtSemestreEstudiante.setText(estudiante?.semestreActual.toString())
        if (estudiante?.graduado == 1) {
            switchGraduadoEstudiante.toggle()
        }
    }

    fun crearEstudiante(){
        var nombres = txtNombreEstudiante.text.toString()
        var apellidos = txtApellidoEstudiante.text.toString()
        var fecha = txtFechaNacimientoEstudiante.text.toString()
        var semestreActual = txtSemestreEstudiante.text.toString().toInt()
        var graduado = if (switchGraduadoEstudiante.isChecked) 1 else 0


        if (!tipo){

            var estudiante = Estudiante(0, nombres, apellidos, fecha, semestreActual, graduado,0,0)
            BaseDatosEstudiante.insertarEstudiante(estudiante)

        }else{
            var estudiante = Estudiante(estudiante?.id!!, nombres, apellidos, fecha, semestreActual, graduado,0,0)
            BaseDatosEstudiante.actualizarEstudiante(estudiante)
        }
        iraActividadEstudiante()

    }

    fun iraActividadEstudiante(){
        val intent = Intent(this, RegistrarUsuarios::class.java)
        intent.putExtra("valorRol","VENDEDOR")
        startActivity(intent)
    }
}
