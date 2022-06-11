package com.example.medi_sheba.controllers

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.medi_sheba.model.User
import com.example.medi_sheba.presentation.util.decrypt
import com.example.medi_sheba.presentation.util.encrypt
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AllDoctorsController {
    private val db = Firebase.firestore

    private val _doctors = MutableLiveData<List<User>>()
    val doctors: LiveData<List<User>>
        get() = _doctors



    @RequiresApi(Build.VERSION_CODES.O)
    fun getDoctors(doctorCategory: String) {
        val docRef = db.collection("users")
        docRef.get()
            .addOnSuccessListener { result ->
                val doctors = mutableListOf<User>()
                for (document in result) {
                    if(document != null){
                        if(doctorCategory == "All"){
                            Log.d("admin", "getDoctors: ${encrypt("Admin")}")
                            if(decrypt(document.getString("userType")!!) == "Doctor" ){
                                getObjectValue(document, doctors)
                            }
                        }else{
                            if(decrypt(document.getString("userType")!!) == "Doctor" &&
                                decrypt(document.getString("doctorCategory")!!) == doctorCategory ){
                                getObjectValue(document, doctors)
                            }
                        }
                    }

                }
                _doctors.value = doctors
            }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun getObjectValue(document: QueryDocumentSnapshot, doctors: MutableList<User>) {
        val doctor = document.toObject(User::class.java)
        doctor.name = decrypt(doctor.name)
        doctor.email = decrypt(doctor.email)
        doctor.userType = decrypt(doctor.userType)
        doctor.mobileNumber = decrypt(doctor.mobileNumber)
        doctor.age = decrypt(doctor.age)
        doctor.address = decrypt(doctor.address)
        doctor.gender = decrypt(doctor.gender)
        doctor.image = decrypt(doctor.image)
        doctor.doctorCategory = decrypt(doctor.doctorCategory)
        doctor.doctorDesignation = decrypt(doctor.doctorDesignation)

        doctors.add(doctor)
    }

    fun getDoctors1(doctorCategory: String) {
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