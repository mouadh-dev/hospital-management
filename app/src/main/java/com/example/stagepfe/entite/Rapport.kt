package com.example.stagepfe.entite

import android.os.Parcel
import android.os.Parcelable

class Rapport (
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

    companion object CREATOR : Parcelable.Creator<Rapport> {
        override fun createFromParcel(parcel: Parcel): Rapport {
            return Rapport(parcel)
        }

        override fun newArray(size: Int): Array<Rapport?> {
            return arrayOfNulls(size)
        }
    }
}
