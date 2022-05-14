package com.example.medi_sheba.controllers

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import com.example.medi_sheba.model.BookAppointment
import com.example.medi_sheba.presentation.screenItem.ScreenItem
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class BookAppointmentController {

//    private var sslCommerzInitialization: SSLCommerzInitialization? = null
//    var additionalInitializer: SSLCAdditionalInitializer? = null
//
//
//    val init = SSLCommerzInitialization(
//        "yourStoreID",
//        "yourPassword",
//        amount,
//        SSLCCurrencyType.BDT,
//        "123456789098765",
//        "yourProductType",
//        SSLCSdkType.TESTBOX
//    )
    val db = Firebase.firestore

    fun bookAppointment(
        bookAppointment: BookAppointment,
        context: Context,
        isOpenModal: MutableState<Boolean>
    ) {
        db.collection("appointment")
            .document()
            .set(bookAppointment)
            .addOnSuccessListener {
                Toast.makeText(context, "Your appointment is successful", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
    }
}