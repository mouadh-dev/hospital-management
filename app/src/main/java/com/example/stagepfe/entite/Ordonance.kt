package com.example.stagepfe.entite

import android.os.Parcel
import android.os.Parcelable

data class Ordonance constructor(
    var namepatientOrdo:String? = "",
    var nameDoctorOrd:String? = "",
    var idPatient:String? = "",
    var idDoctor:String? = "",
    var nameMedicament:String? = "",
    var quantity:String? = "",
    var description:String? = ""
):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(namepatientOrdo)
        parcel.writeString(nameDoctorOrd)
        parcel.writeString(idPatient)
        parcel.writeString(idDoctor)
        parcel.writeString(nameMedicament)
        parcel.writeString(quantity)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Ordonance> {
        override fun createFromParcel(parcel: Parcel): Ordonance {
            return Ordonance(parcel)
        }

        override fun newArray(size: Int): Array<Ordonance?> {
            return arrayOfNulls(size)
        }
    }
}