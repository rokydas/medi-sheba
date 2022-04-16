package com.example.medi_sheba.model

import com.example.medi_sheba.R

data class Appointment(
    val id: String,
    val doctorId: String,
    val patientId: String,
    val doctorImage: Int
)

val appointments = listOf<Appointment>(
    Appointment("", "", "", R.drawable.doctor2),
    Appointment("", "", "", R.drawable.doctor2),
    Appointment("", "", "", R.drawable.doctor2),
    Appointment("", "", "", R.drawable.doctor2),
    Appointment("", "", "", R.drawable.doctor2),
    Appointment("", "", "", R.drawable.doctor2),
    Appointment("", "", "", R.drawable.doctor2),
    Appointment("", "", "", R.drawable.doctor2),
    Appointment("", "", "", R.drawable.doctor2),
    Appointment("", "", "", R.drawable.doctor2),
)
