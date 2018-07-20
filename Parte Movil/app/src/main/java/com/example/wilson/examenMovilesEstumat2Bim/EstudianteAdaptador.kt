package com.example.wilson.examenMovilesEstumat2Bim

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class EstudianteAdaptador(private val myDataset: ArrayList<Estudiante>, var context: Context): RecyclerView.Adapter<EstudianteAdaptador.ViewHolder>() {

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnCreateContextMenuListener{

        var textView_nombres: TextView
        var textView_apellidos: TextView
        var textView_semestre: TextView
        var btn_detalle_fila: Button

        lateinit var estudiante: Estudiante
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        init {
            textView_nombres = view.findViewById(R.id.txtView_nombreest) as TextView
            textView_apellidos = view.findViewById(R.id.txtView_apellidoest) as TextView
            textView_semestre = view.findViewById(R.id.txtView_semestreest) as TextView
            btn_detalle_fila = view.findViewById(R.id.btn_detalleest) as Button

            btn_detalle_fila.setOnClickListener {
                irActividadDetalleEst()
            }

            view.setOnCreateContextMenuListener(this)
        }

        override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
            val menuInflater = MenuInflater(context)
            menuInflater.inflate(R.menu.context_menu, menu)

            menu?.findItem(R.id.editar)?.setOnMenuItemClickListener {
                irActividadCreacionEst()
                true
            }

            menu?.findItem(R.id.eliminar)?.setOnMenuItemClickListener {
                crearDialogo()
                true
            }
        }


        fun crearDialogo(){
            builder.setMessage("¿Eliminar datos?")
                    .setPositiveButton("Sí",  { dialog, which ->
                        eliminarEst(estudiante)
                        true
                    })
                    .setNegativeButton("No", { dialog, which ->
                        true
                    })
            val dialogo = builder.create()
            dialogo.show()
        }

        fun irActividadCreacionEst(){
            val intent = Intent(context, CreateEstActivity::class.java)
            intent.putExtra("EST", estudiante)
            context.startActivity(intent)
        }

        fun eliminarEst(estudiante: Estudiante){
            HttpFuel.requestHTTP("DELETE", "/Estudiante/${estudiante.id}", callback = { error, datos ->
                if(error){
                    Toast.makeText(context, "Error al eliminar el estudiante", Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(context, "Estudiante eliminado", Toast.LENGTH_LONG).show()
                    myDataset.remove(estudiante)
                    notifyDataSetChanged()
                }
            })
        }

        fun irActividadDetalleEst(){
            val intent = Intent(context, EstDetailActivity::class.java)
            intent.putExtra("ES", estudiante)
            context.startActivity(intent)
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val estudianteActual: Estudiante = myDataset[position]
        holder.textView_nombres.text = estudianteActual.nombres
        holder.textView_semestre.text = estudianteActual.semestreActual.toString()
        holder.textView_apellidos.text = estudianteActual.apellidos
        holder.estudiante = estudianteActual

    }

    override fun getItemCount(): Int {
        return myDataset.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_row, parent, false)
        return ViewHolder(itemView)
    }
}

