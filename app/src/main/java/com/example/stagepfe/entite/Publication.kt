package com.example.stagepfe.entite

import android.os.Parcel
import android.os.Parcelable

data class Publication constructor(
    var id:String? = "",
    var idUser: String? = "",
    var textPublication: String?= "",
    var datePublication: String? = "",
    var heurePublication: String? = "",
    var imagePublication:String? = "",
    var likes:ArrayList<String>? = ArrayList(),
    var comments:ArrayList<Comment>? = ArrayList()
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        TODO("likes"),
        TODO("comments")
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(idUser)
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
