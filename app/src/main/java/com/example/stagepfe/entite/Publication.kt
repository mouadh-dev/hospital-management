package com.example.stagepfe.entite

import android.os.Parcel
import android.os.Parcelable

data class Publication constructor(
    var id: String? = "",
    var idsenderPublication: String? = "",
    var textPublication: String?= "",
    var datePublication: String? = "",
    var heurePublication: String? = "",
    var imagePublication:String? = ""
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
        parcel.writeString(idsenderPublication)
        parcel.writeString(textPublication)
        parcel.writeString(datePublication)
        parcel.writeString(heurePublication)
        parcel.writeString(imagePublication)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Publication> {
        override fun createFromParcel(parcel: Parcel): Publication {
            return Publication(parcel)
        }

        override fun newArray(size: Int): Array<Publication?> {
            return arrayOfNulls(size)
        }
    }

}
