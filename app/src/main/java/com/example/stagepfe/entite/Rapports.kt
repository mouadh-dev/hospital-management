package com.example.stagepfe.entite

import android.os.Parcel
import android.os.Parcelable

class Rapports (
    var id:String? = "",
    var namPatientRapport: String? = "",
    var idPatientRapport:String? = "",
    var nameDoctorRapport:String? = "",
    var idDoctorRapport:String? = "",
    var textRapport:String? = ""
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(namPatientRapport)
        parcel.writeString(idPatientRapport)
        parcel.writeString(nameDoctorRapport)
        parcel.writeString(idDoctorRapport)
        parcel.writeString(textRapport)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Rapports> {
        override fun createFromParcel(parcel: Parcel): Rapports {
            return Rapports(parcel)
        }

        override fun newArray(size: Int): Array<Rapports?> {
            return arrayOfNulls(size)
        }
    }


}
