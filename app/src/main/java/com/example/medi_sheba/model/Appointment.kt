package com.example.medi_sheba.model

data class Appointment(
    val doctor_uid: String = "",
    val patient_uid: String = "",
    val nurse_uid: String = "",
    val doc_checked: Boolean = false,
    val time_slot: String = "",
    val serial: String = "",
    val date: String = "",
    val cabin_no: String = "",
    val weight: String = "",
    val prescription: String = "",
    val disease_details: String = "",
    var document_id: String = ""
)