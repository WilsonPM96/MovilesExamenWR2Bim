package com.example.wilson.estudiantemateria

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_listar_estudiantes.*

class ListarEstudiantesActivity : AppCompatActivity() {

    lateinit var adaptador: EstudianteAdapter
    lateinit var estudiantes: ArrayList<Estudiante>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar_estudiantes)

        estudiantes = BaseDatosEstudiante.getList()


        val layoutManager = LinearLayoutManager(this)
        adaptador = EstudianteAdapter(estudiantes)
        recyclerViewEstudiante.layoutManager = layoutManager
        recyclerViewEstudiante.itemAnimator = DefaultItemAnimator()
        recyclerViewEstudiante.adapter = adaptador
        adaptador.notifyDataSetChanged()

        registerForContextMenu(recyclerViewEstudiante)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        var position = adaptador.getPosition()
        var estudiante = estudiantes[position]

        when (item.itemId) {

            R.id.item_menu_editar -> {
                val intent = Intent(this, EstudianteActivity::class.java)
                intent.putExtra("tipo", "Edit")
                intent.putExtra("estudiante", estudiante)
                startActivity(intent)
                return true
            }
            R.id.item_menu_eliminar -> {
                val builder = AlertDialog.Builder(this)
                builder.setMessage("Esta seguro de eliminar?")
                        .setPositiveButton("Si", { dialog, which ->
                            BaseDatosEstudiante.eliminarEstudiante(estudiante.id)
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
