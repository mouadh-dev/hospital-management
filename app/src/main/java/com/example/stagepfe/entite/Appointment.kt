package com.example.stagepfe.entite

import android.os.Parcel
import android.os.Parcelable

data class Appointment constructor(
    var nameDoctor: String? = "",
    var namePatient: String? = "",
    var date: String? = "",
    var hour: String? = "",
    var dispo: String? = "",
    var FinishOrNot: String? = "",
    var id: String? = ""
) : Parcelable {
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
        parcel.writeString(nameDoctor)
        parcel.writeString(namePatient)
        parcel.writeString(date)
        parcel.writeString(hour)
        parcel.writeString(dispo)
        parcel.writeString(FinishOrNot)
        parcel.writeString(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Appointment> {
        override fun createFromParcel(parcel: Parcel): Appointment {
            return Appointment(parcel)
        }

        override fun newArray(size: Int): Array<Appointment?> {
            return arrayOfNulls(size)
        }
    }

}
