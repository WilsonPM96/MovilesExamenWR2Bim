package com.example.wilson.estudiantemateria

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.tapadoo.alerter.Alerter
import kotlinx.android.synthetic.main.activity_detalles_ordenes.*


class DetalleOrdenesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        var oredenes: Ordenes? = null


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles_ordenes)

        oredenes = intent.getParcelableExtra("detallesOrden")

        txtShowCedula.text = oredenes?.cedulaComprador.toString()
        txtShowSector.text = oredenes?.sector
        txtShowIdPokemon.text = oredenes?.idMateria.toString()

        btnGuardarDatosOrden.setOnClickListener { v: View? ->
            guardarDatosOrdenDetalles()

        }

    }

    fun guardarDatosOrdenDetalles(){
        val fechaEnvio = txtFechaEnvio.text.toString()
        val costoPokemon = txtPrecioPokemon.text.toString().toInt()
        val idMateria = txtShowIdPokemon.text.toString().toInt()
        val ordenDetalles = DetallesOrden(0,fechaEnvio,costoPokemon,idMateria)
        BaseDatosOrdenes.insertarOrdenDetalles(ordenDetalles)
        Alerter.create(this)
                .setTitle("Orden enviada a CLIENTE")
                .setText("La solicitud fue enviada exitosamente")
                .enableSwipeToDismiss()
                .show()
    }
}
