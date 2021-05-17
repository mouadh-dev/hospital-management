package com.example.stagepfe.entite

import android.os.Parcel
import android.os.Parcelable

class ProfilPhoto (

    var id:String? = ""

):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

        parcel.writeString(id)

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProfilPhoto> {
        override fun createFromParcel(parcel: Parcel): ProfilPhoto {
            return ProfilPhoto(parcel)
        }

        override fun newArray(size: Int): Array<ProfilPhoto?> {
            return arrayOfNulls(size)
        }
    }
}