package com.example.medi_sheba.controllers

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medi_sheba.model.User
import com.example.medi_sheba.presentation.util.decrypt
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class MakeAndDeleteRoleController: ViewModel() {
    val db = Firebase.firestore

    private val _userList = MutableLiveData<List<User>>()
    val userList: LiveData<List<User>>
        get() = _userList

    fun getUserList(context: Context) = viewModelScope.launch {
        val docRef = db.collection("users")
        docRef.get()
            .addOnSuccessListener { result ->
                val users = mutableListOf<User>()
                for (document in result) {
                    val user = document.toObject(User::class.java)
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
                    user.doctorRating = decrypt(user.doctorRating.toString())
                    user.doctorPrice = decrypt(user.doctorPrice.toString())

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