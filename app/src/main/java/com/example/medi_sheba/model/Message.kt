package com.example.medi_sheba.model

data class Message(
    val senderUid: String,
    val receiverUid: String,
    val message: String,
    val time: String
)
