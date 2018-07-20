package com.example.wilson.examenMovilesEstumat2Bim

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_estlist.*
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import kotlin.collections.ArrayList

class EstListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    var estudiantes = ArrayList<Estudiante>()
    private lateinit var estudiantesDef: Deferred<ArrayList<Estudiante>?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estlist)


        viewManager = LinearLayoutManager(this)
        viewAdapter = EstudianteAdaptador(estudiantes, this)

        recyclerView = est_recycler.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            itemAnimator = DefaultItemAnimator()
            adapter = viewAdapter
        }

        HttpFuel.requestHTTP("GET", "/Estudiante", callback = { error, datos ->
            if(error){
                Toast.makeText(this, "Error al obtener los datos", Toast.LENGTH_LONG).show()
            }else{
                async(UI){
                    estudiantesDef = bg {
                        JsonParser.jsonToOSList(datos)
                    }
                    visualizarDatos(estudiantesDef.await() as ArrayList<Estudiante>)
                }
            }
        })
    }

    fun visualizarDatos(estudiantesRec: ArrayList<Estudiante>){
        estudiantes.addAll(estudiantesRec)
        viewAdapter.notifyDataSetChanged()
    }

}
