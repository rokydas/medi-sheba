package com.example.medi_sheba.model

data class Appointment(
    val doctor_uid: String = "",
    val patient_uid: String = "",
    val nurse_uid: String = "",
    val doc_checked: Boolean = false,
    var time_slot: String = "",
    var serial: String = "",
    var date: String = "",
    var cabin_no: String = "",
    var weight: String = "",
    var prescription: String = "",
    var disease_details: String = "",
    var document_id: String = "",
    val fcmToken: String = "",
    val reminderStatus: Boolean = false,
    var reminderTime: String = ""
    var rating: String = ""
)