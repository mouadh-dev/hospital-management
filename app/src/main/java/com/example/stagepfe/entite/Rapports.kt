package com.example.stagepfe.entite

import android.os.Parcel
import android.os.Parcelable

class Rapports (
    var fullName: String? = "",
    var id:String? = "",
    var textRapport:String? = ""
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(fullName)
        parcel.writeString(id)
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
