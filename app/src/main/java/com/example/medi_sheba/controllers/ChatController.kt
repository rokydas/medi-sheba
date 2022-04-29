package com.example.medi_sheba.controllers

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.medi_sheba.model.Message
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ChatController {

    val db = Firebase.firestore

    private val _messageLists = MutableLiveData<List<Message>>()
    val messageLists: LiveData<List<Message>>
        get() = _messageLists

    fun getMessages(docName: String, context: Context) {
        val docRef = db.collection(docName)
        docRef.get()
            .addOnSuccessListener { result ->
                val messageLists = mutableListOf<Message>()
                for (document in result) {
                    val message = document.toObject(Message::class.java)
                    messageLists.add(message)
                }
                _messageLists.value = messageLists
            }
            .addOnFailureListener { exception ->
                Toast.makeText(context, exception.message, Toast.LENGTH_SHORT).show()
            }
    }

    fun sendMessage(message: Message) {
//        db.collection("messages")
//            .document(message.senderUid)
//            .set(user)
//            .addOnSuccessListener {
//                navController.navigate(ScreenItem.HomeScreenItem.route) {
//                    popUpTo(0)
//                }
//            }
//            .addOnFailureListener {
//                isLoading = false
//                Toast.makeText(context, "Something went wrong. Please try again.", Toast.LENGTH_SHORT).show()
//            }
    }
}

