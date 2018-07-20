package com.example.wilson.estudiantemateria

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.Button
import android.widget.TextView

class OrdenesPendientesAdaptador(private val ordenesList: List<Ordenes>) :  RecyclerView.Adapter<OrdenesPendientesAdaptador.MyViewHolder>(){

    private var position: Int = 0

    fun getPosition(): Int {
        return position
    }

    fun setPosition(position: Int) {
        this.position = position
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnCreateContextMenuListener {

        var cedula: TextView
        var sector : TextView
        var idMateria: TextView
        var detalles: Button

        lateinit var ordenn: Ordenes

        init {
            cedula = view.findViewById(R.id.txtNombreEstudiante) as TextView
            sector = view.findViewById(R.id.txtApellidoEstudiante) as TextView
            idMateria = view.findViewById(R.id.txtFechaNacimientoEstudiante) as TextView
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
        val orden = ordenesList[position]
        holder.cedula.text = orden.cedulaComprador.toString()
        holder.sector.text = orden.sector
        holder.idMateria.text = orden.idMateria.toString()
        holder.ordenn = orden
        holder.detalles.setOnClickListener{
            v: View ->
            val intent = Intent(v.context, DetalleOrdenesActivity::class.java)
            intent.putExtra("detallesOrden", orden)

            v.context.startActivity(intent)
        }
        holder.itemView.setOnLongClickListener {
            setPosition(holder.adapterPosition)
            false
        }
    }

    override fun getItemCount(): Int {
        return ordenesList.size
    }


}