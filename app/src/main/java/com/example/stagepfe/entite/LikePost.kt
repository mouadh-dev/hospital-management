package com.example.stagepfe.entite

import android.os.Parcel
import android.os.Parcelable

data class LikePost constructor(
    var idLiker:String? = "",
    var idtaker:String? = "",
    var id:String? = "",
    var idPost:String ? = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(idLiker)
        parcel.writeString(idtaker)
        parcel.writeString(id)
        parcel.writeString(idPost)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LikePost> {
        override fun createFromParcel(parcel: Parcel): LikePost {
            return LikePost(parcel)
        }

        override fun newArray(size: Int): Array<LikePost?> {
            return arrayOfNulls(size)
        }
    }

}