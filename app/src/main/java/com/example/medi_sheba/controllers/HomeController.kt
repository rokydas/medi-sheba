package com.example.medi_sheba.controllers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.medi_sheba.model.User
import com.example.medi_sheba.presentation.encryption.EncryptClass
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HomeController {
    val db = Firebase.firestore
    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user



    fun getHomeUser(userId: String, encryptClass: EncryptClass) {
        val docRef = db.collection("users").document(userId)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val user = document.toObject(User::class.java)!!
                    user.userType = encryptClass.decrypt(user.userType)
                    _user.value = user
                }
            }
    }


    //load doctor list
    private val _doctors = MutableLiveData<List<User>>()
    val doctors: LiveData<List<User>>
        get() = _doctors

    fun getDoctors(doctorCategory: String, encryptClass: EncryptClass) {
        val docRef = db.collection("users")
        docRef.get()
            .addOnSuccessListener { result ->
                val doctors = mutableListOf<User>()
                for (document in result) {
                    if(document != null){
                        if(doctorCategory == "All"){
                            if(encryptClass.decrypt(document.getString("userType")!!) == "Doctor" ){
                                getObjectValue(document, doctors, encryptClass)
                            }
                        }else{
                            if(encryptClass.decrypt(document.getString("userType")!!) == "Doctor" &&
                                encryptClass.decrypt(document.getString("doctorCategory")!!) == doctorCategory ){
                                getObjectValue(document, doctors, encryptClass)
                            }
                        }
                    }

                }
                _doctors.value = doctors
            }
    }
    private fun getObjectValue(
        document: QueryDocumentSnapshot,
        doctors: MutableList<User>,
        encryptClass: EncryptClass
    ) {
        val doctor = document.toObject(User::class.java)
        doctor.name = encryptClass.decrypt(doctor.name)
//        doctor.email = encryptClassClass.decrypt(doctor.email)
//        doctor.userType = encryptClassClass.decrypt(doctor.userType)
//        doctor.mobileNumber = encryptClassClass.decrypt(doctor.mobileNumber)
//        doctor.age = encryptClassClass.decrypt(doctor.age)
//        doctor.address = encryptClassClass.decrypt(doctor.address)
//        doctor.gender = encryptClassClass.decrypt(doctor.gender)
        doctor.image = encryptClass.decrypt(doctor.image)
        doctor.doctorCategory = encryptClass.decrypt(doctor.doctorCategory)
        doctor.doctorDesignation = encryptClass.decrypt(doctor.doctorDesignation)
        doctor.doctorRating = encryptClass.decrypt(doctor.doctorRating.toString())
        doctor.doctorPrice = encryptClass.decrypt(doctor.doctorPrice.toString())

        doctors.add(doctor)
    }
}