package com.example.medi_sheba.controllers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medi_sheba.EncryptClass
import com.example.medi_sheba.model.User
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
                    user.name = EncryptClass.decrypt(document.getString("name")!!)
                    user.address = EncryptClass.decrypt(document.getString("address")!!)
                    user.email = EncryptClass.decrypt(document.getString("email")!!)
                    user.age = EncryptClass.decrypt(document.getString("age")!!)
                    user.mobileNumber = EncryptClass.decrypt(document.getString("mobileNumber")!!)
                    _user.value = user



                    //1st step
//                    val user = document.toObject(User::class.java)!!
//                    _user.value = user
                }
            }
    }



}