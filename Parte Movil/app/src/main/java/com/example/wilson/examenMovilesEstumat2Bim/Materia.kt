package com.example.wilson.examenMovilesEstumat2Bim

import android.os.Parcel
import android.os.Parcelable
import java.util.*

class Materia(var id: Int = -1,
              var nombre: String,
              var numeroHorasPorSemana: Int,
              var codigo: String,
              @KlaxonDate var fechaCreacion: Date,
              var descripcion: String,
              var estudiante: Int = -1,
              var detalleOrden: Int = -1,
              var foto: Foto? = null): Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readString(),
            Date(parcel.readLong()),
            parcel.readString(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readParcelable(Foto::class.java.classLoader))

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(nombre)
        parcel.writeInt(numeroHorasPorSemana)
        parcel.writeString(codigo)
        parcel.writeLong(fechaCreacion.time)
        parcel.writeString(descripcion)
        parcel.writeInt(estudiante)
        parcel.writeInt(detalleOrden)
        parcel.writeParcelable(foto, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Materia> {
        override fun createFromParcel(parcel: Parcel): Materia {
            return Materia(parcel)
        }

        override fun newArray(size: Int): Array<Materia?> {
            return arrayOfNulls(size)
        }
    }


}
