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
                    val newUser = User(
                        uid = user.uid,
                        name = decrypt(user.name),
                        userType = decrypt(user.userType),
                        email = decrypt(user.email),
                        image = decrypt(user.image),
                        mobileNumber = decrypt(user.mobileNumber),
                        age = decrypt(user.age),
                        address = decrypt(user.address),
                        gender = decrypt(user.gender),
                        doctorCategory = decrypt(user.doctorCategory),
                        doctorDesignation = decrypt(user.doctorDesignation),
                        doctorPrice = decrypt(user.doctorPrice),
                        doctorRating = decrypt(user.doctorRating)
                    )
                    _user.value = newUser
                }
            }
    }
}