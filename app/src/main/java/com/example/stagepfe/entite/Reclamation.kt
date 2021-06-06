package com.example.stagepfe.entite

import android.os.Parcel
import android.os.Parcelable

data class Reclamation(
    var phoneNumber: String? = "",
    var idReclameur:String? = "",
    var id:String? = "",
    var description:String? = "",
    var timeReclamation:String? = "",
    var dateReclamation:String? = ""
):Parcelable {
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
        parcel.writeString(phoneNumber)
        parcel.writeString(idReclameur)
        parcel.writeString(id)
        parcel.writeString(description)
        parcel.writeString(timeReclamation)
        parcel.writeString(dateReclamation)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Reclamation> {
        override fun createFromParcel(parcel: Parcel): Reclamation {
            return Reclamation(parcel)
        }

        override fun newArray(size: Int): Array<Reclamation?> {
            return arrayOfNulls(size)
        }
    }


}