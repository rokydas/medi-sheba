package com.example.medi_sheba.controllers

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medi_sheba.model.User
import com.example.medi_sheba.presentation.encryption.EncryptClass
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import javax.inject.Inject

class MakeAndDeleteRoleController: ViewModel() {
    val db = Firebase.firestore

    private val _userList = MutableLiveData<List<User>>()
    val userList: LiveData<List<User>>
        get() = _userList


    fun getUserList(context: Context, encryptClass: EncryptClass) = viewModelScope.launch {
        val docRef = db.collection("users")
        docRef.get()
            .addOnSuccessListener { result ->
                val users = mutableListOf<User>()
                for (document in result) {
                    val user = document.toObject(User::class.java)
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
                    user.doctorRating = encryptClass.decrypt(user.doctorRating.toString())
                    user.doctorPrice = encryptClass.decrypt(user.doctorPrice.toString())

                    users.add(user)
                }
                _userList.value = users
            }
            .addOnFailureListener { exception ->
                Toast.makeText(context, exception.message, Toast.LENGTH_SHORT).show()
            }
    }

    fun changeRole(roleName: String, uid: String, onClick: (Boolean) -> Unit) {
        val docRef = db.collection("users").document(uid)
        docRef.update("userType", roleName)
            .addOnSuccessListener {
                onClick(true)
            }
    }
}