package com.example.wilson.estudiantemateria

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.util.Base64
import android.view.View
import kotlinx.android.synthetic.main.activity_materia.*
import java.io.ByteArrayOutputStream


class MateriaActivity : AppCompatActivity() {

    var materia: Materia? = null
    var estudianteId: Int = 0
    private lateinit var imageBitmap: Bitmap
    var tipo = false
    lateinit var myBase64Image:String
    lateinit var myBitmapAgain:Bitmap


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_materia)

        estudianteId = intent.getIntExtra("estudianteId", 0)

        val type = intent.getStringExtra("tipo")

        if (type.equals("Edit")) {
            textViewMateria.text = "Editar Materia"
            materia = intent.getParcelableExtra("materia")
            fillFields()
            tipo = true
        }

        btnGuardarMateria.setOnClickListener { v: View? ->
            crearMateria()
        }

        btnTomarFoto.setOnClickListener{v: View? ->
            tomarFoto()

        }
    }

    fun fillFields() {
        txtNombreMateria.setText(materia?.nombre.toString())
        txtCodigoMateria.setText(materia?.codigo)
        txtDescripcionMateria.setText(materia?.descripcion)
        txtActivaMateria.setText(materia?.activo)
        txtFechaCreacionMateria.setText(materia?.fechaCreacion)
        txtHorasMateria.setText(materia?.numeroHorasPorSemana.toString())
    }

    fun crearMateria(){
        var nombre = txtNombreMateria.text.toString().toInt()
        var codigo = txtCodigoMateria.text.toString()
        var descripcion = txtDescripcionMateria.text.toString()
        var activo = txtActivaMateria.text.toString()
        var fechaCreacion = txtFechaCreacionMateria.text.toString()
        var numeroHorasPorSemana = txtHorasMateria.text.toString().toInt()
        var imagenMateria = myBase64Image

        if (!tipo){
            var materia = Materia(0,nombre,codigo,descripcion,activo,fechaCreacion,numeroHorasPorSemana,imagenMateria,estudianteId,0,0)
            BaseDatosMateria.insertarMateria(materia)

        }else{
            var materia2 = Materia(materia?.id!!,nombre,codigo,descripcion,activo,fechaCreacion,numeroHorasPorSemana,imagenMateria,estudianteId,0,0)
            BaseDatosMateria.actualizarMateria(materia2)
        }

        finish()

    }

    val REQUEST_IMAGE_CAPTURE = 1

    private fun tomarFoto() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }

    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
       super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val extras = data.extras
            imageBitmap = extras!!.get("data") as Bitmap

            myBase64Image = encodeToBase64(imageBitmap, Bitmap.CompressFormat.JPEG, 100)
            myBitmapAgain = decodeBase64(myBase64Image)

            imageViewMateria.setImageBitmap(myBitmapAgain)


        }

    }

    fun encodeToBase64(image: Bitmap, compressFormat: Bitmap.CompressFormat, quality: Int): String {
        val byteArrayOS = ByteArrayOutputStream()
        image.compress(compressFormat, quality, byteArrayOS)
        return android.util.Base64.encodeToString(byteArrayOS.toByteArray(), android.util.Base64.DEFAULT)

    }

    fun decodeBase64(input: String): Bitmap {
        val decodedBytes =  Base64.decode(input,0)
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    }



}

