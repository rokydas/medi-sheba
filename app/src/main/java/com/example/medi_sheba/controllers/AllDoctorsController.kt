package com.example.medi_sheba.controllers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.medi_sheba.model.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AllDoctorsController {
    private val db = Firebase.firestore

    private val _doctors = MutableLiveData<List<User>>()
    val doctors: LiveData<List<User>>
        get() = _doctors

    fun getDoctors(doctorCategory: String) {
        val docRef = if(doctorCategory == "All") db.collection("users")
                .whereEqualTo("userType", "Doctor")
            else db.collection("users")
                .whereEqualTo("doctorCategory", doctorCategory)
                .whereEqualTo("userType", "Doctor")
        docRef.get()
            .addOnSuccessListener { result ->
                val doctors = mutableListOf<User>()
                for (document in result) {
                    if (document != null) {
                        val doctor = document.toObject(User::class.java)
                        doctors.add(doctor)
                    }
                }
                _doctors.value = doctors
            }
    }
}