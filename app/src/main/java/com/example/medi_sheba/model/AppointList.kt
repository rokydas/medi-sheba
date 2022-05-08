package com.example.medi_sheba.model

import android.annotation.SuppressLint
import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi

data class AppointList(
    val patient_uid: String? = null,
    val doctor_uid: String? = null,
    val nurse_uid: String? = null,
    val doc_checked: Boolean = false,
    val cabin_no: Int = 0,
    val timestamp: String? = null
) : Parcelable {
    @SuppressLint("NewApi")
    constructor(source: Parcel) : this(
        source.readString()!!,
        source.readString()!!,
        source.readString()!!,
        source.readBoolean(),
        source.readInt(),
        source.readString()!!,

    )

    override fun describeContents() = 0

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(patient_uid)
        writeString(doctor_uid)
        writeString(nurse_uid)
        writeBoolean(doc_checked)
        writeInt(cabin_no)
        writeString(timestamp)

    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<AppointList> = object : Parcelable.Creator<AppointList> {
            override fun createFromParcel(source: Parcel): AppointList = AppointList(source)
            override fun newArray(size: Int): Array<AppointList?> = arrayOfNulls(size)
        }
    }
}
