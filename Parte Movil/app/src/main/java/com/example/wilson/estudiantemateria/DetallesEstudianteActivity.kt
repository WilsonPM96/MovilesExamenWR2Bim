package com.example.wilson.estudiantemateria

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_detalles_estudiante.*

class DetallesEstudianteActivity : AppCompatActivity() {

    var estudiante: Estudiante? = null
    lateinit var materia: ArrayList<Materia>
    lateinit var adaptador: MateriaAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles_estudiante)



        estudiante = intent.getParcelableExtra("detallesEstudiante")

        txtShowEstudianteId.text = estudiante?.id.toString()
        txtShowNombreEstudiante.text = estudiante?.nombres
        txtShowApellidoEstudiante.text = estudiante?.apellidos
        txtShowFechaNacEstudiante.text = estudiante?.fechaNacimiento
        txtShowSemestreEstudiante.text = estudiante?.semestreActual.toString()
        txtShowGraduadoEstudiante.text = if(estudiante?.graduado == 1) "Si" else "No"



        materia = BaseDatosMateria.getMateriaList(estudiante?.id!!)
       Log.d("resultado",materia.toString())

       val layoutManager = LinearLayoutManager(this)
        adaptador = MateriaAdapter(materia)
        recycler_view_materia.layoutManager = layoutManager
        recycler_view_materia.itemAnimator = DefaultItemAnimator()
        recycler_view_materia.adapter = adaptador
        adaptador.notifyDataSetChanged()

        registerForContextMenu(recycler_view_materia)

        btnNuevaMateria.setOnClickListener { v: View? ->
            irActividdadCrearMateria()
        }
    }

    fun irActividdadCrearMateria(){
        val intent = Intent(this, MateriaActivity::class.java)
        intent.putExtra("tipo", "Create")
        intent.putExtra("estudianteId", estudiante?.id!!)
        startActivity(intent)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        var position = adaptador.getPosition()
        var materias = materia[position]

        when (item.itemId) {

            R.id.item_menu_editar -> {
                val intent = Intent(this, MateriaActivity::class.java)
                intent.putExtra("tipo", "Edit")
                intent.putExtra("materia", materias)
                startActivity(intent)
                return true
            }
            R.id.item_menu_eliminar -> {
                val builder = AlertDialog.Builder(this)
                builder.setMessage("Esta seguro de eliminar?")
                        .setPositiveButton("Si", { dialog, which ->
                            BaseDatosMateria.eliminarMateria(materias.id)
                            finish()
                            startActivity(intent)
                        }
                        )
                        .setNegativeButton("No", null)
                val dialogo = builder.create()
                dialogo.show()
                return true
            }
            else -> {
                Log.i("menu", "Todos los demas")
                return super.onOptionsItemSelected(item)
            }
        }
    }

}
