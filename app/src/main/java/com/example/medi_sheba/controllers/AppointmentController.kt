package com.example.medi_sheba.controllers

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.medi_sheba.model.Appointment
import com.example.medi_sheba.presentation.screens.encryptClass
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


    fun getAppointment(uid: String?, userType: String?) {
        var uid_title= "user"
        var dataAccess = false
        when (userType) {
            "Doctor" -> {
                uid_title = "doctor_uid"
            }
            "Nurse" -> {
                uid_title = "nurse_uid"
            }
            "Patient" -> {
                uid_title = "patient_uid"
            }
            "Admin" -> {
                dataAccess = true
            }
        }
        val appointmentCol = db.collection("appointment")
        val appointments = mutableListOf<Appointment>()

        appointmentCol.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    for (doc in document) {
                        if(doc.getString(uid_title) == uid || dataAccess){
                            val appointment = doc.toObject(Appointment::class.java)
                            appointment.document_id = doc.id
//                            appointment.doc_checked = encryptClass.decrypt(appointment.doc_checked)
                            appointment.time_slot = encryptClass.decrypt(appointment.time_slot)
                            appointment.serial = encryptClass.decrypt(appointment.serial)
                            appointment.date = encryptClass.decrypt(appointment.date)
                            appointment.cabin_no = encryptClass.decrypt(appointment.cabin_no)
                            appointment.weight = encryptClass.decrypt(appointment.weight)
                            appointment.prescription = encryptClass.decrypt(appointment.prescription)
                            appointment.disease_details = encryptClass.decrypt(appointment.disease_details)

                            appointments.add(appointment)
                        }

                    }
                    _appointLists.value = appointments
                }
            }
    }
    fun getAppointDocuData(document_id: String) {
        val appointmentCol = db.collection("appointment").document(document_id)

        appointmentCol.get()
            .addOnSuccessListener { document ->
                if(document != null){

                    //1st step
                    val appointment = document.toObject(Appointment::class.java)
                    if (appointment != null) {
//                        appointment.doc_checked = encryptClass.decrypt(appointment.doc_checked)
                        appointment.time_slot = encryptClass.decrypt(appointment.time_slot)
                        appointment.serial = encryptClass.decrypt(appointment.serial)
                        appointment.date = encryptClass.decrypt(appointment.date)
                        appointment.cabin_no = encryptClass.decrypt(appointment.cabin_no)
                        appointment.weight = encryptClass.decrypt(appointment.weight)
                        appointment.prescription = encryptClass.decrypt(appointment.prescription)
                        appointment.disease_details = encryptClass.decrypt(appointment.disease_details)
                    }
                    _appointment.value = appointment!!



//                    //2nd step by encryption
//                    val appoint = Appointment(
//                        document.getString("doctor_uid")!!,
//                        document.getString("patient_uid")!!,
//                        document.getString("nurse_uid")!!,
//                        document.getBoolean("doc_checked")!!,
//                        document.getString("time_slot")!!,
//                        document.getString("serial")!!,
//                        document.getString("date")!!,
//                        document.getString("cabin_no")!!,
//                        "${document.getString("weight")}" ,
//                        document.getString("prescription")!!,
//                        document.getString("disease_details")!!,
//                        document.getString("document_id")!!
//                        )
//                    _appointment.value = appoint




                }
            }
    }
}