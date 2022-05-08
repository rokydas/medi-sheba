package com.example.medi_sheba.controllers

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.medi_sheba.FirestoreAll.Product
import com.example.medi_sheba.model.Message
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson

class AppointmentController {
    val db = Firebase.firestore
    val gson = Gson()

    private val _appointLists = MutableLiveData<List<Product>>()
    val appointLists: LiveData<List<Product>>
        get() = _appointLists




    fun getMessages(docName: String) {
        val docRef = db.collection("appointment").document()
        docRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.d("fireStoreListener", "Listen failed.", e)
                return@addSnapshotListener
            }
            if (snapshot != null && snapshot.exists()) {
                try {
                    Log.d("fireStoreListener", "snapshot value:  $snapshot")

                } catch (e: Exception) {

                }
            } else {
                Log.d("fireStoreListener", "Current data: null")
            }
        }
    }
}