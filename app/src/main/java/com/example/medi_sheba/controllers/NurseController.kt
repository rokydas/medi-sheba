package com.example.medi_sheba.controllers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.medi_sheba.model.User
import com.example.medi_sheba.presentation.constant.Constant.NURSE
import com.example.medi_sheba.presentation.util.decrypt
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class NurseController {
    val db = Firebase.firestore
    private val _nurseList = MutableLiveData<List<User>>()
    val nurseList: LiveData<List<User>>
        get() = _nurseList

    fun getNurseList() {
        val userCol = db.collection("users")
        val user = mutableListOf<User>()

        userCol.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    for (doc in document) {
                        if(decrypt(doc.getString("userType")!!)==NURSE){
                            val userValue = doc.toObject(User::class.java)
                            userValue.name = decrypt(userValue.name)
                            userValue.email = decrypt(userValue.email)
                            userValue.userType = decrypt(userValue.userType)
                            userValue.mobileNumber = decrypt(userValue.mobileNumber)
                            userValue.age = decrypt(userValue.age)
                            userValue.address = decrypt(userValue.address)
                            userValue.gender = decrypt(userValue.gender)
                            userValue.image = decrypt(userValue.image)
                            userValue.doctorCategory = decrypt(userValue.doctorCategory)
                            userValue.doctorDesignation = decrypt(userValue.doctorDesignation)
                            user.add(userValue)
                        }
                    }
                    _nurseList.value = user
                }
            }
    }
}