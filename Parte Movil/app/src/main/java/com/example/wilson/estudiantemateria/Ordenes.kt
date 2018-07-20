package com.example.wilson.estudiantemateria

import android.os.Parcel
import android.os.Parcelable

class Ordenes(var idCompra:Int, var cedulaComprador:Int, var sector:String, var idMateria:Int) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(idCompra)
        parcel.writeInt(cedulaComprador)
        parcel.writeString(sector)
        parcel.writeInt(idMateria)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Ordenes> {
        override fun createFromParcel(parcel: Parcel): Ordenes {
            return Ordenes(parcel)
        }

        override fun newArray(size: Int): Array<Ordenes?> {
            return arrayOfNulls(size)
        }
    }
}