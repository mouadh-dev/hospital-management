package com.example.stagepfe.entite
import android.os.Parcel
import android.os.Parcelable

data class Message constructor(
    var id: String? = "",
    var sender: String? = "",
    var reciever: String? = "",
    var message: String? = "",
    var nameSender: String? = "",
    var nameReciever:String?= "",
    var timemsg: String? = "",
    var imgmsgPatient: Int? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(sender)
        parcel.writeString(reciever)
        parcel.writeString(message)
        parcel.writeString(nameSender)
        parcel.writeString(nameReciever)
        parcel.writeString(timemsg)
        parcel.writeValue(imgmsgPatient)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Message> {
        override fun createFromParcel(parcel: Parcel): Message {
            return Message(parcel)
        }

        override fun newArray(size: Int): Array<Message?> {
            return arrayOfNulls(size)
        }
    }


}

