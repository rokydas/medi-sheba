package com.example.medi_sheba.controllers

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.example.medi_sheba.model.Appointment
import com.example.medi_sheba.model.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class BookAppointmentController {
    val db = Firebase.firestore

    private val _appointments = MutableLiveData<List<Appointment>>()
    val appointments: LiveData<List<Appointment>>
        get() = _appointments

    private val _chatUserList = MutableLiveData<List<Appointment>>()
    val chatUserList: LiveData<List<Appointment>>
        get() = _chatUserList

    fun bookAppointment(
        appointment: Appointment,
        context: Context,
        navController: NavController
    ) {
        db.collection("appointment")
            .document()
            .set(appointment)
            .addOnSuccessListener {
                Toast.makeText(context, "Your appointment booking is successful", Toast.LENGTH_SHORT).show()
                navController.popBackStack()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
    }

    fun loadAllAppointmentsForBooking(docUid: String, date: String, context: Context) {
        val docRef = db.collection("appointment")
            .whereEqualTo("doctor_uid", docUid)
            .whereEqualTo("date", date)
        docRef.get()
            .addOnSuccessListener { result ->
                val appointments = mutableListOf<Appointment>()
                for (document in result) {
                    val appointment = document.toObject(Appointment::class.java)
                    appointments.add(appointment)
                }
                _appointments.value = appointments
            }
            .addOnFailureListener { exception ->
                Toast.makeText(context, exception.message, Toast.LENGTH_SHORT).show()
            }
    }
}