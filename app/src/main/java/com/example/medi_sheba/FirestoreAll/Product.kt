package com.example.medi_sheba.FirestoreAll

import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class Product(
    var patient_uid: String? = null,
    var doctor_uid: String? = null,
    var nurse_uid: String? = null,
    var doc_checked: Boolean = false,
    var cabin_no: Int = 0,
    @ServerTimestamp
    var timestamp: String? = null
)
