package com.example.stagepfe.entite

import android.os.Parcel
import android.os.Parcelable

class CheckedModel() :Parcelable {
    var isSelected:Boolean? = null
    var nomMedicament:String? = ""
    var quantity:String? = ""
    var description:String? = ""

    constructor(parcel: Parcel) : this() {
        isSelected = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        nomMedicament = parcel.readString()
        quantity = parcel.readString()
        description = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(isSelected)
        parcel.writeString(nomMedicament)
        parcel.writeString(quantity)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CheckedModel> {
        override fun createFromParcel(parcel: Parcel): CheckedModel {
            return CheckedModel(parcel)
        }

        override fun newArray(size: Int): Array<CheckedModel?> {
            return arrayOfNulls(size)
        }
    }


}