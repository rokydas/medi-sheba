package com.example.medi_sheba.controllers

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.medi_sheba.model.Appointment
import com.example.medi_sheba.model.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AppointmentController {
    val db = Firebase.firestore
    private val _appointLists = MutableLiveData<List<Appointment>>()
    val appointmentList: LiveData<List<Appointment>>
        get() = _appointLists

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    fun getAppointment() {
        val docRef = db.collection("appointment")
        val appointments = mutableListOf<Appointment>()

        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    for (doc in document) {
                        val appointment = doc.toObject(Appointment::class.java)
                        appointments.add(appointment)
                    }
                    _appointLists.value = appointments
                }
            }
    }
}