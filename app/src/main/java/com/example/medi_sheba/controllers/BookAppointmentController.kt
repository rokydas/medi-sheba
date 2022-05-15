package com.example.medi_sheba.controllers

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.example.medi_sheba.model.Appointment
import com.example.medi_sheba.model.TimeSlot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDate

class BookAppointmentController {
    val db = Firebase.firestore

    val initialTimeSlots = listOf<TimeSlot>(
        TimeSlot("09.01 AM - 09.30 AM", true),
        TimeSlot("09.31 AM - 10.00 AM", true),
        TimeSlot("10.01 AM - 10.30 AM", true),
        TimeSlot("10.31 AM - 11.00 AM", true),
        TimeSlot("11.01 AM - 11.30 AM", true),
        TimeSlot("11.31 AM - 12.00 PM", true),
        TimeSlot("12.01 PM - 12.30 PM", true),
        TimeSlot("12.31 PM - 01.00 PM", true),
        TimeSlot("07.01 PM - 07.30 PM", true),
        TimeSlot("07.31 PM - 08.00 PM", true),
        TimeSlot("08.01 PM - 08.30 PM", true),
        TimeSlot("08.31 PM - 09.00 PM", true),
        TimeSlot("09.01 PM - 09.30 PM", true),
        TimeSlot("09.31 PM - 10.00 PM", true)
    )

    private val _timeSlots = MutableLiveData<List<TimeSlot>>()
    val timeSlots: LiveData<List<TimeSlot>>
        get() = _timeSlots

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

    fun loadAllAppointmentsForBooking(docUid: String, date: List<LocalDate>, context: Context) {
        if (date.isNotEmpty()) {
            val docRef = db.collection("appointment")
                .whereEqualTo("doctor_uid", docUid)
                .whereEqualTo("date", date[0].toString())
            docRef.get()
                .addOnSuccessListener { result ->
                    val appointments = mutableListOf<Appointment>()
                    for (document in result) {
                        val appointment = document.toObject(Appointment::class.java)
                        appointments.add(appointment)
                    }
                    initialTimeSlots.forEach() { timeSlot ->
                        val filteredAppointment = appointments.filter { appointment ->
                            appointment.time_slot == timeSlot.time
                        }
                        timeSlot.isBooked = filteredAppointment.isNotEmpty()
                    }
                    _timeSlots.value = initialTimeSlots
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(context, exception.message, Toast.LENGTH_SHORT).show()
                }
        } else {
            _timeSlots.value = listOf()
        }

    }
}