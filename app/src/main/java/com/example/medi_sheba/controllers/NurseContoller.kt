package com.example.medi_sheba.controllers

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.medi_sheba.model.Appointment
import com.example.medi_sheba.model.User
import com.example.medi_sheba.presentation.constant.Constant.NURSE
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class NurseContoller {
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
                        if(doc.getString("userType")==NURSE){
                            val userValue = doc.toObject(User::class.java)
                            user.add(userValue)
                        }

                    }
                    _nurseList.value = user
                    Log.d("nurse", "getNurseList: ${_nurseList.value}")
                    Log.d("nurse", "getNurseList: ${_nurseList.value?.size}")
                }
            }
    }
}