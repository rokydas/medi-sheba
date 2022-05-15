package com.example.medi_sheba.controllers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.medi_sheba.model.Appointment
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AppointmentController {
    val db = Firebase.firestore
    private val _appointLists = MutableLiveData<List<Appointment>>()
    val appointmentList: LiveData<List<Appointment>>
        get() = _appointLists

    private val _appointment = MutableLiveData<Appointment>()
    val appoint: LiveData<Appointment>
        get() = _appointment


    fun getAppointment() {
        val appointmentCol = db.collection("appointment")
        val appointments = mutableListOf<Appointment>()

        appointmentCol.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    for (doc in document) {
                        val appointment = doc.toObject(Appointment::class.java)
                        appointment.document_id = doc.id
                        appointments.add(appointment)
                    }
                    _appointLists.value = appointments
                }
            }
    }
    fun getAppointDocuData(document_id: String) {
        val appointmentCol = db.collection("appointment").document(document_id)
        val appointments = mutableListOf<Appointment>()

        appointmentCol.get()
            .addOnSuccessListener { document ->
                if(document != null){
                    val appointment = document.toObject(Appointment::class.java)
                    _appointment.value = appointment!!
                }
            }
    }
}