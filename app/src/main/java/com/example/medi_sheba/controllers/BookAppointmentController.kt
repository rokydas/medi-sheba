package com.example.medi_sheba.controllers

import android.app.AlarmManager
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.example.medi_sheba.R
import com.example.medi_sheba.model.Appointment
import com.example.medi_sheba.model.TimeSlot
import com.example.medi_sheba.services.messageExtra
import com.example.medi_sheba.services.notificationID
import com.example.medi_sheba.services.titleExtra
import com.example.medi_sheba.presentation.util.decrypt
import com.example.medi_sheba.services.channelID
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDate
import java.util.*

class BookAppointmentController {
    private val db = Firebase.firestore
    private val initialTimeSlots = listOf<TimeSlot>(
        TimeSlot("10.01 AM - 10.30 AM", true, "09.30 AM"),
        TimeSlot("10.31 AM - 11.00 AM", true, "10.00 AM"),
        TimeSlot("11.01 AM - 11.30 AM", true, "10.30 AM"),
        TimeSlot("11.31 AM - 12.00 PM", true, "11.00 AM"),
        TimeSlot("12.01 PM - 12.30 PM", true, "11.30 AM"),
        TimeSlot("12.31 PM - 01.00 PM", true, "12.00 PM"),
        TimeSlot("01.01 PM - 01.30 PM", true, "12.30 PM"),
        TimeSlot("01.31 PM - 02.00 PM", true, "01.00 PM"),
        TimeSlot("07.01 PM - 07.30 PM", true, "06.30 PM"),
        TimeSlot("07.31 PM - 08.00 PM", true, "07.00 PM"),
        TimeSlot("08.01 PM - 08.30 PM", true, "07.30 PM"),
        TimeSlot("08.31 PM - 09.00 PM", true, "08.00 PM"),
        TimeSlot("09.01 PM - 09.30 PM", true, "08.30 PM"),
        TimeSlot("09.31 PM - 10.00 PM", true, "09.00 PM")
    )

    private val _timeSlots = MutableLiveData<List<TimeSlot>>()
    val timeSlots: LiveData<List<TimeSlot>>
        get() = _timeSlots

    @RequiresApi(Build.VERSION_CODES.M)
    fun bookAppointment(
        appointment: Appointment,
        context: Context,
        navController: NavController,
    ) {
        db.collection("appointment")
            .document()
            .set(appointment)
            .addOnSuccessListener {
                Toast.makeText(context, "Your appointment booking is successful", Toast.LENGTH_SHORT).show()

                val notification = NotificationCompat.Builder(context, channelID)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("Your appointment booking is done.")
                    .build()

                val  manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                manager.notify(notificationID, notification)

                navController.popBackStack()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun loadAllAppointmentsForBooking(docUid: String, date: List<LocalDate>, context: Context) {
        if (date.isEmpty()) {
            _timeSlots.value = listOf()
            return
        }
        else {
            val today = LocalDate.now()
            if (today.isAfter(date[0])) {
                _timeSlots.value = listOf()
                return
            }
            val docRef = db.collection("appointment")
                .whereEqualTo("doctor_uid", docUid)
                .whereEqualTo("date", date[0].toString())
            docRef.get()
                .addOnSuccessListener { result ->
                    val appointments = mutableListOf<Appointment>()
                    for (document in result) {
                        val appointment = document.toObject(Appointment::class.java)
//                            appointment.doc_checked = decrypt(appointment.doc_checked)
                        appointment.time_slot = decrypt(appointment.time_slot)
                        appointment.serial = decrypt(appointment.serial)
//                        appointment.date = decrypt(appointment.date)
                        appointment.cabin_no = decrypt(appointment.cabin_no)
                        appointment.weight = decrypt(appointment.weight)
                        appointment.prescription = decrypt(appointment.prescription)
                        appointment.disease_details = decrypt(appointment.disease_details)

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
        }

    }
}

@RequiresApi(Build.VERSION_CODES.M)
private fun scheduleNotification(
    context: Context,
    title: String,
    message: String,
    minute: Int,
    hour: Int,
    day: Int,
    month: Int,
    year: Int,
    alarmManager: AlarmManager
)
{
    val intent = Intent(context, Notification::class.java)
    intent.putExtra(titleExtra, title)
    intent.putExtra(messageExtra, message)

    val pendingIntent = PendingIntent.getBroadcast(
        context,
        notificationID,
        intent,
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    )

    val calendar = Calendar.getInstance()
    calendar.set(year, month, day, hour, minute)
    val time = calendar.timeInMillis
    alarmManager.setExactAndAllowWhileIdle(
        AlarmManager.RTC_WAKEUP,
        time,
        pendingIntent
    )
}