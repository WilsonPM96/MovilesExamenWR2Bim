package com.example.wilson.examenMovilesEstumat2Bim

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_estdetail.*
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import java.text.SimpleDateFormat

class EstDetailActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private var estudiante: Estudiante? = null
    var materias: ArrayList<Materia>? = ArrayList()
    private val dateFormat = SimpleDateFormat("dd-MM-yyyy")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estdetail)

        viewManager = LinearLayoutManager(this)
        viewAdapter = MateriaAdaptador(materias as ArrayList<Materia>, this)

        recyclerView = recyclerView_detalle_est.apply {
            setHasFixedSize(true)
            itemAnimator = DefaultItemAnimator()
            layoutManager = viewManager
            adapter = viewAdapter
        }

        estudiante = intent.getParcelableExtra("ES")

        estudiante?.let {
            HttpFuel.requestHTTP(metodo = "GET", ruta = "/Estudiante/${estudiante!!.id}/Materias", callback = { error, datos ->
                if(error){
                    Toast.makeText(this, "Error al obtener materias", Toast.LENGTH_LONG).show()
                }else{
                    async(UI) {
                            val materiasDef: Deferred<ArrayList<Materia>?> = bg {
                                JsonParser.jsonToMatList(datos)
                            }
                            visualizarDatos(materiasDef.await() as ArrayList<Materia>)
                        }
                }
            }
            )
        }

        btn_nueva_materia.setOnClickListener {
            irActividadCreateMat()
        }

        btn_mapa.setOnClickListener{
            irActividadMapa()
        }
    }

    fun llenarVistaEst(estudiante: Estudiante){
        txtView_nombre_detalle.text = estudiante.nombres
        txtView_apellidos_detalle.text = estudiante.apellidos
        txtView_semestre_detalle.text  = estudiante.semestreActual.toString()
        txtView_graduado_detalle.text = if(estudiante.graduado) "Sí" else "No"
        txtView_fecha_detalle.text = dateFormat.format(estudiante.fechaNacimiento)

    }

    fun irActividadCreateMat(){
        val intent = Intent(this, CreateMatActivity::class.java)
        intent.putExtra("es_id", estudiante!!.id)
        startActivity(intent)
    }

    fun irActividadMapa(){
        val intent = Intent(this,GoogleMapsActivity::class.java)
        startActivity(intent)
    }


    fun visualizarDatos(materiasRec: ArrayList<Materia>){
        llenarVistaEst(estudiante as Estudiante)
        materias!!.addAll(materiasRec)
        viewAdapter.notifyDataSetChanged()
    }


}
