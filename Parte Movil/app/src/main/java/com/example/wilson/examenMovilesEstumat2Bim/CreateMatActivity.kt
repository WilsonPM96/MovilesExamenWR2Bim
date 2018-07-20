package com.example.wilson.examenMovilesEstumat2Bim

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_create_mat.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class CreateMatActivity : AppCompatActivity() {

    private val dateFormat = SimpleDateFormat("dd-MM-yyyy")
    private var imagePath = ""
    private lateinit var imageBitmap: Bitmap
    private var materia: Materia? = null
    private var es_id = 0
    companion object {
        val REQUEST_IMAGE_CAPTURE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_mat)

        materia = intent.getParcelableExtra("MAT")
        es_id = intent.getIntExtra("es_id", 0)

        materia?.let {
            llenarFormulario(materia as Materia)
        }

        btn_tomar_foto.setOnClickListener {
            tomarFoto()
        }

        btn_guardar_mat.setOnClickListener {
            if(materia == null){
                val nuevaMateria = crearMateria()
                val appJson = JsonParser.appToJson(nuevaMateria)

                HttpFuel.requestHTTP("POST", "/Materia", appJson, { error, datos ->
                    if(error){
                        Toast.makeText(this, "Error al crear materia", Toast.LENGTH_LONG).show()
                    }else{
                        val materiaCreada = JsonParser.jsonToMat(datos)
                        nuevaMateria.foto!!.materia = materiaCreada!!.id
                        val fotoJson = JsonParser.fotoToJson(nuevaMateria.foto as Foto)
                        HttpFuel.requestHTTP("POST", "/Foto", fotoJson, { error, datos ->
                            if(error){
                                Toast.makeText(this, "Error al crear foto", Toast.LENGTH_LONG).show()
                            }else{
                                Toast.makeText(this, "Materia creada", Toast.LENGTH_LONG).show()
                            }
                        })
                    }
                })
            }else{
                actualizarMateria(materia!!)
                val appJson = JsonParser.appToJson(materia as Materia)
                HttpFuel.requestHTTP("PUT", "/Materia/${materia!!.id}", appJson, { error, datos ->
                    if(error){
                        Toast.makeText(this, "Error al actualizar", Toast.LENGTH_LONG).show()
                    }else{
                        val fotoJson = JsonParser.fotoToJson(materia!!.foto as Foto)
                        HttpFuel.requestHTTP("PUT", "/Foto/${materia!!.foto!!.id}", fotoJson, { error, datos ->
                            if(error){
                                Toast.makeText(this, "Error al actualizar", Toast.LENGTH_LONG).show()
                            }else{
                                Toast.makeText(this, "Materia actualizada", Toast.LENGTH_LONG).show()
                            }
                        })
                    }
                })
            }
        }
    }

    fun actualizarMateria(materia: Materia){
        val nombre = editText_nombre_mat.text.toString()
        val codigo = editText_codigo_mat.text.toString()
        val desc = editText_desc_mat.text.toString()
        val horas = editText_horas_mat.text.toString().toInt()
        val fechaCreacion = dateFormat.parse(editText_fecha_mat.text.toString())
        val fotoEncoded = ImageHandler.bitmapToB64String(imageBitmap)
        materia.nombre = nombre
        materia.codigo = codigo
        materia.descripcion = desc
        materia.numeroHorasPorSemana = horas
        materia.fechaCreacion = fechaCreacion
        materia.foto!!.datos = fotoEncoded
    }

    fun crearMateria(): Materia {
        val nombre = editText_nombre_mat.text.toString()
        val codigo = editText_codigo_mat.text.toString()
        val desc = editText_desc_mat.text.toString()
        val horas = editText_horas_mat.text.toString().toInt()
        val fechaCreacion = dateFormat.parse(editText_fecha_mat.text.toString())
        val fotoEncoded = ImageHandler.bitmapToB64String(imageBitmap)
        val foto = Foto(datos = fotoEncoded, extension = "jpg")

        val materia = Materia(
                nombre = nombre,
                codigo = codigo,
                descripcion = desc,
                numeroHorasPorSemana = horas,
                fechaCreacion = fechaCreacion,
                foto = foto,
                estudiante = es_id
        )
        return materia
    }

    fun llenarFormulario(materia: Materia){
        editText_nombre_mat.append(materia.nombre)
        editText_horas_mat.append(materia.numeroHorasPorSemana.toString())
        editText_desc_mat.append(materia.descripcion)
        editText_codigo_mat.append(materia.codigo)
        editText_fecha_mat.append(dateFormat.format(materia.fechaCreacion))
        imageBitmap = ImageHandler.base64ToBitmap(materia.foto!!.datos)
        imgView_foto_mat.setImageBitmap(imageBitmap)
    }

    fun tomarFotoIntent(file: File){
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        imagePath = file.absolutePath
        val photoUri: Uri = FileProvider.getUriForFile(
                this,
                "com.example.wilson.examenMovilesEstumat2Bim.fileprovider",
                file
        )
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
        if(intent.resolveActivity(packageManager) != null){
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK){
            Log.i("IMAGE_PATH", imagePath)
            if(materia != null)
                imageBitmap.recycle()
            afterImageRotationCompleted(ImageHandler.bitmapFromFileRotation(File(imagePath)))
            /*async(UI){
                val imageRotated: Deferred<Bitmap> = bg{
                    ImageHandler.bitmapFromFileRotation(File(imagePath))
                }
                afterImageRotationCompleted(imageRotated.await())
            }*/
        }
    }


    fun afterImageRotationCompleted(bitmap: Bitmap){
        imageBitmap = bitmap
        imgView_foto_mat.setImageBitmap(Bitmap.createScaledBitmap(imageBitmap, imgView_foto_mat.width, imgView_foto_mat.height, false))
    }

    fun tomarFoto(){
        val imageFile = createImageFile("JPEG_", Environment.DIRECTORY_PICTURES, ".jpg")
        tomarFotoIntent(imageFile)
    }

    fun createImageFile(prefix: String, directory: String, extension: String): File {
        val timestamp = SimpleDateFormat("ddMMyyyy_HHmmss").format(Date())
        val filename = prefix + timestamp + "_"
        val storageDir = getExternalFilesDir(directory)
        return File.createTempFile(filename, extension, storageDir)
    }
}
