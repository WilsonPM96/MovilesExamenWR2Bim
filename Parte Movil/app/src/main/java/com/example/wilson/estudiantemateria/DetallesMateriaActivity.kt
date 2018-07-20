package com.example.wilson.estudiantemateria

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import kotlinx.android.synthetic.main.activity_detalles_materia.*


class DetallesMateriaActivity : AppCompatActivity() {

    var materia: Materia? = null
    lateinit var myBitmapAgain:Bitmap


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles_materia)

        materia = intent.getParcelableExtra("detallesPokemon")


        txtShowNombreMateria.text = materia?.nombre.toString()
        txtShowCodigoMateria.text = materia?.codigo
        txtShowDescripcion.text = materia?.descripcion
        txtShowActivo.text = materia?.activo
        txtShowFechaCreacion.text = materia?.fechaCreacion
        txtShowHoras.text = materia?.numeroHorasPorSemana.toString()

        myBitmapAgain = decodeBase64(materia?.imagenMateria.toString()!!)
        showImageViewMateria.setImageBitmap(myBitmapAgain)

    }

    fun decodeBase64(input: String): Bitmap {
        val decodedBytes =  Base64.decode(input,0)
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    }


}
