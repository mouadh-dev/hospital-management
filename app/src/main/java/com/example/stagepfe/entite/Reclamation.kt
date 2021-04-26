package com.example.stagepfe.entite

import android.os.Parcel
import android.os.Parcelable

data class Reclamation(
    var fullName: String? = "",
    var phoneNumber: String? = "",
    var id:String? = "",
    var description:String? = ""
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(fullName)
        parcel.writeString(phoneNumber)
        parcel.writeString(id)
        parcel.writeString(description)
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