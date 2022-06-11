package com.example.medi_sheba.controllers

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.medi_sheba.model.Message
import com.example.medi_sheba.presentation.util.decrypt
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class ChatController {
    val db = Firebase.firestore

    private val _messageLists = MutableLiveData<List<Message>>()
    val messageLists: LiveData<List<Message>>
        get() = _messageLists

    @RequiresApi(Build.VERSION_CODES.O)
    fun getMessages(docName: String) {
        val docRef = db.collection("messages").document(docName)
            .collection("texts")
        docRef.addSnapshotListener { data, e ->
            if (e != null) {
                return@addSnapshotListener
            }

            val messages = mutableListOf<Message>()
            for (doc in data!!) {
                messages.add(
                    Message(
                        message = decrypt(doc.getString("message")!!),
                        senderUid = doc.getString("senderUid")!!,
                        receiverUid = doc.getString("receiverUid")!!,
                        time = doc.getString("time")!!
                ))
            }
            _messageLists.value = messages
        }
    }

    fun sendMessage(message: Message, context: Context) {
        sendMessageInDocument(message.senderUid + "_" + message.receiverUid, message, context)
        sendMessageInDocument(message.receiverUid + "_" + message.senderUid, message, context)
    }

    private fun sendMessageInDocument(docName: String, message: Message, context: Context) {
        db.collection("messages")
            .document(docName)
            .collection("texts")
            .document(message.time)
            .set(message)
            .addOnSuccessListener { }
            .addOnFailureListener {
                Toast.makeText(
                    context,
                    "Something went wrong. Please try again.",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}

