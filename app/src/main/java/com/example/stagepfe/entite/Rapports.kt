package com.example.stagepfe.entite

import android.os.Parcel
import android.os.Parcelable

class Rapports (
    var id:String? = "",
//    var namePatientRapport: String? = "",
    var idPatientRapport:String? = "",
//    var nameDoctorRapport:String? = "",
    var idDoctorRapport:String? = "",
    var textRapport:String? = "",
    var specialityDoctor:String? = "",
    var dateRapport:String? = "",
    var hourRapport:String? = ""
): Parcelable {
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
        parcel.writeString(id)
        parcel.writeString(idPatientRapport)
        parcel.writeString(idDoctorRapport)
        parcel.writeString(textRapport)
        parcel.writeString(specialityDoctor)
        parcel.writeString(dateRapport)
        parcel.writeString(hourRapport)
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
