package com.example.wilson.examenMovilesEstumat2Bim

import android.os.Parcel
import android.os.Parcelable
import com.beust.klaxon.Json
import java.text.SimpleDateFormat
import java.util.*

class Estudiante(var id: Int = -1,
                 var nombres: String,
                 var apellidos: String,
                 @KlaxonDate var fechaNacimiento: Date,
                 var semestreActual: Int,
                 var graduado: Boolean,
                 @Json(ignored = true) var aplicaciones: List<Materia>? = null) : Parcelable {

    val dateFormat: SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy")

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            Date(parcel.readLong()),
            parcel.readInt(),
            parcel.readByte() != 0.toByte(),
            parcel.createTypedArrayList(Materia)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(nombres)
        parcel.writeString(apellidos)
        parcel.writeLong(fechaNacimiento.time)
        parcel.writeInt(semestreActual)
        parcel.writeByte((if (graduado) 1 else 0).toByte())
        parcel.writeTypedList(aplicaciones)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Estudiante> {
        override fun createFromParcel(parcel: Parcel): Estudiante {
            return Estudiante(parcel)
        }

        override fun newArray(size: Int): Array<Estudiante?> {
            return arrayOfNulls(size)
        }
    }

}