package com.example.wilson.estudiantemateria

import android.os.Parcel
import android.os.Parcelable

class Materia(var id: Int, var nombre: Int, var codigo: String, var descripcion: String, var activo: String, var fechaCreacion: String, var numeroHorasPorSemana: Int, var imagenMateria:String, var estudianteId:Int, var createdAt: Long,
              var updatedAt: Long) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readLong(),
            parcel.readLong()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(nombre)
        parcel.writeString(codigo)
        parcel.writeString(descripcion)
        parcel.writeString(activo)
        parcel.writeString(fechaCreacion)
        parcel.writeInt(numeroHorasPorSemana)
        parcel.writeString(imagenMateria)
        parcel.writeInt(estudianteId)
        parcel.writeLong(createdAt)
        parcel.writeLong(updatedAt)
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
