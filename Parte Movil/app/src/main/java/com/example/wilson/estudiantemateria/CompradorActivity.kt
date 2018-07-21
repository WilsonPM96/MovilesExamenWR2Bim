package com.example.wilson.estudiantemateria

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.tapadoo.alerter.Alerter
import kotlinx.android.synthetic.main.activity_datos_comprador.*
import android.content.Intent



class CompradorActivity : AppCompatActivity() {

    lateinit var idMaterias:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_datos_comprador)

       idMaterias = intent.getStringExtra("idMateria")


        btnEnviarDatosComprador.setOnClickListener { v: View? ->
            crearOrden()
        }
    }

    fun crearOrden(){
        var cedula = txtCedulaComprador.text.toString().toInt()
        var sector = txtSector.text.toString()
        var idMateria = idMaterias.toInt()
        var ordenCompra = Ordenes(0,cedula,sector,idMateria)
        BaseDatosOrdenes.insertarOrden(ordenCompra)

        Alerter.create(this)
                .setTitle("Datos Enviados a DELIVERY")
                .setText("Su peticion ha sido enviada satisfactoriamente")
                .setDuration(10000)
                .enableSwipeToDismiss()
                .setOnClickListener(View.OnClickListener {
                    irAPaginaPrincipal()
                }).show()


    }

    fun irAPaginaPrincipal(){
        val intent = Intent(this,RegistrarUsuarios::class.java)
        intent.putExtra("valorRol", "CLIENTE")
        startActivity(intent)
    }
}
