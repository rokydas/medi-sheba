package com.example.medi_sheba.controllers

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medi_sheba.model.User
import com.example.medi_sheba.presentation.util.decrypt
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class ProfileController() : ViewModel() {
    val db = Firebase.firestore

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    @RequiresApi(Build.VERSION_CODES.O)
    fun getUser(userId: String) = viewModelScope.launch {
        val docRef = db.collection("users").document(userId)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val user = document.toObject(User::class.java)!!
                    user.name = decrypt(user.name)
                    user.email = decrypt(user.email)
                    user.userType = decrypt(user.userType)
                    user.mobileNumber = decrypt(user.mobileNumber)
                    user.age = decrypt(user.age)
                    user.address = decrypt(user.address)
                    user.gender = decrypt(user.gender)
                    user.image = decrypt(user.image)
                    user.doctorCategory = decrypt(user.doctorCategory)
                    user.doctorDesignation = decrypt(user.doctorDesignation)
                    user.doctorRating = decrypt(user.doctorRating)
                    user.doctorPrice = decrypt(user.doctorPrice)

                    _user.value = user
                }
            }
    }
}