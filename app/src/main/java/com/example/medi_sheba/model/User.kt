package com.example.medi_sheba.model

import android.os.Parcel
import android.os.Parcelable

data class User(
    val name: String = "",
    val userType: String = "",
    val email: String = "",
    val image: String = "",
    val mobileNumber: String = "",
    val fcmToken: String = "",
    val age: String = "",
    val address: String = "",
    val gender: String = ""
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString()!!,
        source.readString()!!,
        source.readString()!!,
        source.readString()!!,
        source.readString()!!,
        source.readString()!!,
        source.readString()!!,
        source.readString()!!,
        source.readString()!!,
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(name)
        writeString(userType)
        writeString(email)
        writeString(image)
        writeString(mobileNumber)
        writeString(fcmToken)
        writeString(age)
        writeString(address)
        writeString(gender)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<User> = object : Parcelable.Creator<User> {
            override fun createFromParcel(source: Parcel): User = User(source)
            override fun newArray(size: Int): Array<User?> = arrayOfNulls(size)
        }
    }
}
