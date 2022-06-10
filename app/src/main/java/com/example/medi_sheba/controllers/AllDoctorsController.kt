package com.example.medi_sheba.controllers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.medi_sheba.model.User
import com.example.medi_sheba.presentation.encryption.EncryptClass
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AllDoctorsController {
    val encryptClass = EncryptClass()
    private val db = Firebase.firestore

    private val _doctors = MutableLiveData<List<User>>()
    val doctors: LiveData<List<User>>
        get() = _doctors



    fun getDoctors(doctorCategory: String) {
        val docRef = db.collection("users")
        docRef.get()
            .addOnSuccessListener { result ->
                val doctors = mutableListOf<User>()
                for (document in result) {
                    if(document != null){
                        if(doctorCategory == "All"){
                            if(encryptClass.decrypt(document.getString("userType")!!) == "Doctor" ){
                                getObjectValue(document, doctors)
                            }
                        }else{
                            if(encryptClass.decrypt(document.getString("userType")!!) == "Doctor" &&
                                encryptClass.decrypt(document.getString("doctorCategory")!!) == doctorCategory ){
                                getObjectValue(document, doctors)
                            }
                        }
                    }

                }
                _doctors.value = doctors
            }
    }


    private fun getObjectValue(document: QueryDocumentSnapshot, doctors: MutableList<User>) {
        val doctor = document.toObject(User::class.java)
        doctor.name = encryptClass.decrypt(doctor.name)
        doctor.email = encryptClass.decrypt(doctor.email)
        doctor.userType = encryptClass.decrypt(doctor.userType)
        doctor.mobileNumber = encryptClass.decrypt(doctor.mobileNumber)
        doctor.age = encryptClass.decrypt(doctor.age)
        doctor.address = encryptClass.decrypt(doctor.address)
        doctor.gender = encryptClass.decrypt(doctor.gender)
        doctor.image = encryptClass.decrypt(doctor.image)
        doctor.doctorCategory = encryptClass.decrypt(doctor.doctorCategory)
        doctor.doctorDesignation = encryptClass.decrypt(doctor.doctorDesignation)
        doctor.doctorRating = encryptClass.decrypt(doctor.doctorRating.toString())
        doctor.doctorPrice = encryptClass.decrypt(doctor.doctorPrice.toString())

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