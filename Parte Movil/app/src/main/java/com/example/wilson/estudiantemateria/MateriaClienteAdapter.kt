package com.example.wilson.estudiantemateria

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.Button
import android.widget.TextView

class MateriaClienteAdapter(private val materiaList: List<Materia>) :  RecyclerView.Adapter<MateriaClienteAdapter.MyViewHolder>(){

    private var position: Int = 0

    fun getPosition(): Int {
        return position
    }

    fun setPosition(position: Int) {
        this.position = position
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnCreateContextMenuListener {

        var codigo: TextView
        var descripcion : TextView
        var activo: TextView
        var detalles: Button

        lateinit var materiaM1: Materia

        init {
            codigo = view.findViewById(R.id.txtNombreEstudiante) as TextView
            descripcion = view.findViewById(R.id.txtApellidoEstudiante) as TextView
            activo = view.findViewById(R.id.txtFechaNacimientoEstudiante) as TextView
            detalles = view.findViewById(R.id.btnDetallesEstudiant) as Button
            view.setOnCreateContextMenuListener(this)
        }

        override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_estudiante_layout, parent, false)

        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val materiaM= materiaList[position]
        holder.codigo.text = materiaM.codigo
        holder.descripcion.text = materiaM.descripcion
        holder.activo.text = materiaM.activo
        holder.materiaM1 = materiaM
        holder.detalles.setOnClickListener{
            v: View ->
            val intent = Intent(v.context, DetallesMateriaClienteActivity::class.java)
            intent.putExtra("detallesMateriaCliente", materiaM)
            v.context.startActivity(intent)
        }
        holder.itemView.setOnLongClickListener {
            setPosition(holder.adapterPosition)
            false
        }
    }

    override fun getItemCount(): Int {
        return materiaList.size
    }


}