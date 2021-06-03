package com.example.stagepfe.entite

import android.os.Parcel
import android.os.Parcelable

data class Notification constructor(
    var id:String? = "",
    var idPatient:String? = "",
    var idDoctor:String? = "",
    var timeNotification:String? = "",
    var type:String? = "",
    var dateNotification:String? = ""
) :Parcelable{
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
        parcel.writeString(idPatient)
        parcel.writeString(idDoctor)
        parcel.writeString(timeNotification)
        parcel.writeString(type)
        parcel.writeString(dateNotification)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Notification> {
        override fun createFromParcel(parcel: Parcel): Notification {
            return Notification(parcel)
        }

        override fun newArray(size: Int): Array<Notification?> {
            return arrayOfNulls(size)
        }
    }

}