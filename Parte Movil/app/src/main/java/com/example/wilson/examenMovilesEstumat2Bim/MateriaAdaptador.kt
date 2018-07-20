package com.example.wilson.examenMovilesEstumat2Bim

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class MateriaAdaptador(private val myDataset: ArrayList<Materia>, var context: Context): RecyclerView.Adapter<MateriaAdaptador.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnCreateContextMenuListener {
        var txtView_nombre: TextView
        var txtView_codigo: TextView
        var imgView_foto: ImageView
        var btn_detalle_mat: Button

        lateinit var materia: Materia
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)

        init {
            txtView_nombre = view.findViewById(R.id.txtView_nombreest) as TextView
            txtView_codigo = view.findViewById(R.id.txtView_apellidoest) as TextView
            imgView_foto = view.findViewById(R.id.imgView_foto_materia) as ImageView

            btn_detalle_mat = view.findViewById(R.id.btn_detalleest) as Button

            btn_detalle_mat.setOnClickListener {
                irActividadCreateMat()
            }
            view.setOnCreateContextMenuListener(this)

        }

        override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
            val menuInflater: MenuInflater = MenuInflater(context)
            menuInflater.inflate(R.menu.context_menu, menu)

            menu?.findItem(R.id.editar)?.setOnMenuItemClickListener {
                irActividadCreateMat()
                true
            }

            menu?.findItem(R.id.eliminar)?.setOnMenuItemClickListener {
                crearDialogo()
                true
            }
        }


        fun crearDialogo(){
            builder.setMessage("¿Eliminar datos?")
                    .setPositiveButton("Sí", { dialog, which ->
                        eliminarMateria(materia)
                        true
                    })
                    .setNegativeButton("No", { dialog, which ->
                        true
                    })
            val dialogo = builder.create()
            dialogo.show()
        }

        fun irActividadCreateMat() {
            val intent = Intent(context, CreateMatActivity::class.java)
            intent.putExtra("MAT", materia)
            context.startActivity(intent)
        }


        fun eliminarMateria(materia: Materia){
            HttpFuel.requestHTTP("DELETE", "/Materia/${materia.id}", callback = { error, datos ->
                if(error){
                    Toast.makeText(context, "Error al eliminar la materia", Toast.LENGTH_LONG)
                }else{
                    HttpFuel.requestHTTP("DELETE", "/Foto/${materia.foto!!.id}", callback = { error, datos ->
                        if(error){
                            Toast.makeText(context, "Error al eliminar la materia", Toast.LENGTH_LONG)
                        }else{
                            Toast.makeText(context, "Materia eliminada", Toast.LENGTH_LONG)
                            myDataset.remove(materia)
                            notifyDataSetChanged()
                        }
                    })
                }
            })
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val materiaActual = myDataset[position]
        holder.txtView_nombre.text = materiaActual.nombre
        holder.txtView_codigo.text = materiaActual.codigo
        holder.imgView_foto.setImageBitmap(ImageHandler.base64ToBitmap(materiaActual.foto!!.datos))
        holder.materia = materiaActual
    }

    override fun getItemCount(): Int {
        return myDataset.size
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_mat_row, parent, false)

        return ViewHolder(itemView)
    }

}