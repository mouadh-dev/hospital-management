package com.example.stagepfe.entite

import android.os.Parcel
import android.os.Parcelable

data class Ordonance constructor(
    var idPatient:String? = "",
    var idDoctor:String? = "",
    var medicament: ArrayList<MedicamentOrdonance> = arrayListOf<MedicamentOrdonance>(),
    var analyse: ArrayList<AnalyseOrdonnance> = arrayListOf<AnalyseOrdonnance>(),
    var id:String? = "",
    var typeOrdonnance:String? ="",
    var dateOrdonanceSend:String? = "",
    var hourOrdonanceSend:String? = "",
    var taken:String? = "",
    var color:String? = ""

):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        TODO("medicament"),
        TODO("analyse"),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(idPatient)
        parcel.writeString(idDoctor)
        parcel.writeString(id)
        parcel.writeString(typeOrdonnance)
        parcel.writeString(dateOrdonanceSend)
        parcel.writeString(hourOrdonanceSend)
        parcel.writeString(taken)
        parcel.writeString(color)
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