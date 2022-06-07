package com.example.medi_sheba.controllers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medi_sheba.model.User
import com.example.medi_sheba.presentation.screens.encryptClass
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class ProfileController() : ViewModel() {
    val db = Firebase.firestore

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    fun getUser(userId: String) = viewModelScope.launch {
        val docRef = db.collection("users").document(userId)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    //2nd step encrupt-decrypt
                    val user = document.toObject(User::class.java)!!
                    user.name = encryptClass.decrypt(user.name)
                    user.email = encryptClass.decrypt(user.email)
                    user.userType = encryptClass.decrypt(user.userType)
                    user.mobileNumber = encryptClass.decrypt(user.mobileNumber)
                    user.age = encryptClass.decrypt(user.age)
                    user.address = encryptClass.decrypt(user.address)
                    user.gender = encryptClass.decrypt(user.gender)
                    user.image = encryptClass.decrypt(user.image)
                    user.doctorCategory = encryptClass.decrypt(user.doctorCategory)
                    user.doctorDesignation = encryptClass.decrypt(user.doctorDesignation)
                    user.doctorRating = encryptClass.decrypt(user.doctorRating.toString()).toFloat()
                    user.doctorPrice = encryptClass.decrypt(user.doctorPrice.toString()).toInt()

                    _user.value = user



                    //1st step
//                    val user = document.toObject(User::class.java)!!
//                    _user.value = user
                }
            }
    }



}