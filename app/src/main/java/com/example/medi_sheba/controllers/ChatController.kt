package com.example.medi_sheba.controllers

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.medi_sheba.model.AllMessages
import com.example.medi_sheba.model.Message
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject

class ChatController {

    val db = Firebase.firestore
    val gson = Gson()

    private val _messageLists = MutableLiveData<List<Message>>()
    val messageLists: LiveData<List<Message>>
        get() = _messageLists

    fun getMessages(docName: String) {
        val docRef = db.collection("messages").document("3Q2eFw0vAef6mEmq4fk3u4bc7NA2_ggN5nR8HXZMrwH2NGqhBt62ilGs1")
        docRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.d("fireStoreListener", "Listen failed.", e)
                return@addSnapshotListener
            }
            if (snapshot != null && snapshot.exists()) {
                try {

                } catch (e: Exception) {

                }
            } else {
                Log.d("fireStoreListener", "Current data: null")
            }
        }
    }

    fun sendMessage(message: Message, context: Context) {

        var isSent = false

        val docRef = db.collection("messages")
        docRef.get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    if(
                        document.id == message.senderUid + "_" + message.receiverUid ||
                        document.id == message.receiverUid + "_" + message.senderUid
                    ) {
                        db.collection("messages")
                            .document(document.id)
                            .update("texts", (FieldValue.arrayUnion(message)) )
                            .addOnSuccessListener { }
                            .addOnFailureListener {
                                Toast.makeText(context, "Something went wrong. Please try again.", Toast.LENGTH_SHORT).show()
                            }
                        isSent = true
                    }
                }
                if(!isSent) {
                    val allMessages = AllMessages(texts = listOf(message))
                    db.collection("messages")
                        .document(message.senderUid + "_" + message.receiverUid)
                        .set(allMessages)
                        .addOnSuccessListener { }
                        .addOnFailureListener {
                            Toast.makeText(context, "Something went wrong. Please try again.", Toast.LENGTH_SHORT).show()
                        }

                    db.collection("messages")
                        .document(message.receiverUid + "_" + message.senderUid)
                        .set(allMessages)
                        .addOnSuccessListener { }
                        .addOnFailureListener {
                            Toast.makeText(context, "Something went wrong. Please try again.", Toast.LENGTH_SHORT).show()
                        }
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(context, exception.message, Toast.LENGTH_SHORT).show()
            }
    }
}

