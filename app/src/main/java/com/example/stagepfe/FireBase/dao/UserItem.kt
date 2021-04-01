package com.example.stagepfe.FireBase.dao

import android.os.Parcel
import android.os.Parcelable

data class UserItem constructor(
    var nom: String? = "",
    var prenom: String? = "",
    var mail: String? = "",
    var password: String? = "",
    var confirmpassword: String? = "",
    var adresse: String? = "",
    var datenaiss: String? = "",
    var phonenumber: String? = "",
    var sexe: String? = "",
    var groupesanguin: String? = "",
    var role: ArrayList<String>? = ArrayList(),
    var matricule: String? = "",
    var numCIN: String? = "",
    var maladi: String? = "",
    var medicament: String? = "",
    var speciality: String? = "",
    var bio: String? = ""


) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        TODO("role"),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nom)
        parcel.writeString(prenom)
        parcel.writeString(mail)
        parcel.writeString(password)
        parcel.writeString(confirmpassword)
        parcel.writeString(adresse)
        parcel.writeString(datenaiss)
        parcel.writeString(phonenumber)
        parcel.writeString(sexe)
        parcel.writeString(groupesanguin)
        parcel.writeString(matricule)
        parcel.writeString(numCIN)
        parcel.writeString(maladi)
        parcel.writeString(medicament)
        parcel.writeString(speciality)
        parcel.writeString(bio)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserItem> {
        override fun createFromParcel(parcel: Parcel): UserItem {
            return UserItem(parcel)
        }

        override fun newArray(size: Int): Array<UserItem?> {
            return arrayOfNulls(size)
        }
    }

}

