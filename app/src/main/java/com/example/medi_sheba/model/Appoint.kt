package com.example.medi_sheba.model


data class Appoint(
    var patient_uid: String? = null,
    var doctor_uid: String? = null,
    var nurse_uid: String? = null,
    var doc_checked: Boolean = false,
    var cabin_no: String = "0",
    var time: String? = null
)
