package com.example.medi_sheba.controllers

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medi_sheba.model.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class ChatUserListController(): ViewModel() {

    val db = Firebase.firestore

    private val _chatUserList = MutableLiveData<List<User>>()
    val chatUserList: LiveData<List<User>>
        get() = _chatUserList

    fun getChatUserList(context: Context) = viewModelScope.launch {
        val docRef = db.collection("users")
        docRef.get()
            .addOnSuccessListener { result ->
                val chatUsers = mutableListOf<User>()
                for (document in result) {
                    val user = document.toObject(User::class.java)
                    chatUsers.add(user)
                }
                _chatUserList.value = chatUsers
            }
            .addOnFailureListener { exception ->
                Toast.makeText(context, exception.message, Toast.LENGTH_SHORT).show()
            }
    }
}