package com.example.medi_sheba.controllers

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.medi_sheba.model.Appointment
import com.example.medi_sheba.model.User
import com.example.medi_sheba.presentation.constant.Constant.NURSE
import com.example.medi_sheba.presentation.encryption.EncryptClass
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class NurseController {
    val db = Firebase.firestore
    private val _nurseList = MutableLiveData<List<User>>()
    val nurseList: LiveData<List<User>>
        get() = _nurseList


    fun getNurseList(encryptClass: EncryptClass) {
        val userCol = db.collection("users")
        val user = mutableListOf<User>()

        userCol.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    for (doc in document) {
                        if(encryptClass.decrypt(doc.getString("userType")!!)==NURSE){
                            val userValue = doc.toObject(User::class.java)
                            userValue.name = encryptClass.decrypt(userValue.name)
                            userValue.email = encryptClass.decrypt(userValue.email)
                            userValue.userType = encryptClass.decrypt(userValue.userType)
                            userValue.mobileNumber = encryptClass.decrypt(userValue.mobileNumber)
                            userValue.age = encryptClass.decrypt(userValue.age)
                            userValue.address = encryptClass.decrypt(userValue.address)
                            userValue.gender = encryptClass.decrypt(userValue.gender)
                            userValue.image = encryptClass.decrypt(userValue.image)
                            userValue.doctorCategory = encryptClass.decrypt(userValue.doctorCategory)
                            userValue.doctorDesignation = encryptClass.decrypt(userValue.doctorDesignation)
                            userValue.doctorRating = encryptClass.decrypt(userValue.doctorRating.toString())
                            userValue.doctorPrice = encryptClass.decrypt(userValue.doctorPrice.toString())
                            user.add(userValue)
                        }
                    }
                    _nurseList.value = user
                }
            }
    }
}