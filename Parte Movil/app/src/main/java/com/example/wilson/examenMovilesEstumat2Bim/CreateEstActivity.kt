package com.example.wilson.examenMovilesEstumat2Bim

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_create_est.*
import java.text.SimpleDateFormat

class CreateEstActivity : AppCompatActivity() {

    private var estudiante: Estudiante? = null
    private val dateFormat = SimpleDateFormat("dd-MM-yyyy")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_est)

        estudiante = intent.getParcelableExtra("ES")

        estudiante?.let {
            llenarFormulario(estudiante as Estudiante)
        }

        btn_guardar_est.setOnClickListener{

            if(estudiante == null) {
                val est = crearEstudiante()
                val osJson = JsonParser.osToJson(est)

                HttpFuel.requestHTTP("POST", "/Estudiante", osJson, { error, datos ->
                    if (error) {
                        Toast.makeText(this, "Error al crear un estudiante", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this, "Estudiante creado", Toast.LENGTH_LONG).show()
                    }

                })
            }else{
                actualizarEstudiante(estudiante as Estudiante)
                val osJson = JsonParser.osToJson(estudiante as Estudiante)

                HttpFuel.requestHTTP("PUT", "/Estudiante/${estudiante!!.id}", osJson, { error, datos ->
                    if (error) {
                        Toast.makeText(this, "Error al actualizar", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this, "Actualizado correctamente", Toast.LENGTH_LONG).show()
                    }

                })
            }

        }
    }

    fun actualizarEstudiante(estudiante: Estudiante){
        val nombres = editText_nombre_est.text.toString()
        val apellidos = editText_apellido_est.text.toString()
        val fechaNacimiento = dateFormat.parse(editText_fecha_est.text.toString())
        val semestre = editText_semestre_est.text.toString().toInt()
        val graduado = radio_yes_est.isChecked

        estudiante.nombres = nombres
        estudiante.apellidos = apellidos
        estudiante.fechaNacimiento = fechaNacimiento
        estudiante.semestreActual = semestre
        estudiante.graduado = graduado
    }

    fun llenarFormulario(estudiante: Estudiante){
        editText_nombre_est.append(estudiante.nombres)
        editText_fecha_est.append(dateFormat.format(estudiante.fechaNacimiento))
        editText_apellido_est.append(estudiante.apellidos)
        editText_semestre_est.append(estudiante.semestreActual.toString())
        if(estudiante.graduado) radioGroup_yes_no.check(R.id.radio_yes_est) else radioGroup_yes_no.check(R.id.radio_no_est)
    }

    fun crearEstudiante(): Estudiante {
        val nombres = editText_nombre_est.text.toString()
        val apellidos = editText_apellido_est.text.toString()
        val fechaNacimiento = dateFormat.parse(editText_fecha_est.text.toString())
        val semestre = editText_semestre_est.text.toString().toInt()
        val graduado = radio_yes_est.isChecked

        val estudiante = Estudiante(
                nombres = nombres,
                apellidos = apellidos,
                fechaNacimiento = fechaNacimiento,
                semestreActual = semestre,
                graduado = graduado)

        return estudiante

    }


}
