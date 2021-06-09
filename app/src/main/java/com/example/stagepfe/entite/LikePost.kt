package com.example.stagepfe.entite

import android.os.Parcel
import android.os.Parcelable

data class LikePost constructor(
    var idLiker:String? = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(idLiker)
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