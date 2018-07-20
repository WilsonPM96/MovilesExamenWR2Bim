package com.example.wilson.estudiantemateria

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import kotlinx.android.synthetic.main.activity_detalles_materia_cliente.*

class DetallesMateriaClienteActivity : AppCompatActivity() {

    var materia: Materia? = null
    lateinit var myBitmapAgain: Bitmap
    lateinit var idMateria:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles_materia_cliente)

        materia = intent.getParcelableExtra("detallesMateriaCliente")



        txtShowNombreMateriaM.text = materia?.nombre.toString()
        txtShowCodigoMateriaM.text = materia?.codigo
        txtShowDescripcionM.text = materia?.descripcion
        txtShowActivoM.text = materia?.activo
        txtShowFechaCreacionM.text = materia?.fechaCreacion
        txtShowHorasM.text = materia?.numeroHorasPorSemana.toString()



        myBitmapAgain = decodeBase64(materia?.imagenMateria!!)
        showImageViewMateriaM.setImageBitmap(myBitmapAgain)

        btnAdquirirMateria.setOnClickListener { v ->
            irActividadDatosComprador()
        }


    }

    fun decodeBase64(input: String): Bitmap {
        val decodedBytes =  Base64.decode(input,0)
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    }

    fun irActividadDatosComprador(){
        val intent = Intent(this, CompradorActivity::class.java)
        idMateria = materia?.id.toString()
        intent.putExtra("idMateria",idMateria)
        startActivity(intent)
    }


}
