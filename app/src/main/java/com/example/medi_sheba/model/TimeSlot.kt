package com.example.medi_sheba.model

data class TimeSlot(
    val time: String,
    var isBooked: Boolean,
    val reminderTime: String
)
