package com.example.medi_sheba.model

import android.os.Parcel
import android.os.Parcelable

data class User(
    val uid: String = "",
    var name: String = "",
    var userType: String = "",
    var email: String = "",
    var image: String = "",
    var mobileNumber: String = "",
    val fcmToken: String = "",
    var age: String = "",
    var address: String = "",
    var gender: String = "",
    var doctorCategory: String = "",
    var doctorDesignation: String = "",
    var doctorPrice: String = "0",
    var doctorRating: String = "0.0f"
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(uid)
        parcel.writeString(name)
        parcel.writeString(userType)
        parcel.writeString(email)
        parcel.writeString(image)
        parcel.writeString(mobileNumber)
        parcel.writeString(fcmToken)
        parcel.writeString(age)
        parcel.writeString(address)
        parcel.writeString(gender)
        parcel.writeString(doctorCategory)
        parcel.writeString(doctorDesignation)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}
