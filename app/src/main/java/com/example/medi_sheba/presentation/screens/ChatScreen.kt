package com.example.medi_sheba.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.medi_sheba.controllers.ChatController
import com.example.medi_sheba.model.Message
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun ChatScreen(navController: NavController, receiverUid: String?) {

    val chatController = ChatController()
    val auth = Firebase.auth
    val uid = auth.currentUser?.uid
    val context = LocalContext.current
    chatController.getMessages(receiverUid + "_" + uid, context)

    Scaffold {

        val newMessage = rememberSaveable { mutableStateOf("") }

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(elevation = 2.dp)
                    .padding(10.dp)
            ) {
                Text(text = "Showrav Das",)
            }
            MessagesUI(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            MessageField(newMessage, chatController, uid, receiverUid)
        }
    }
}

@Composable
fun MessageField(
    newMessage: MutableState<String>,
    chatController: ChatController,
    uid: String?,
    receiverUid: String?
) {

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = newMessage.value,
            onValueChange = {
                newMessage.value = it
            },
            maxLines = 3,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Send, contentDescription = "",
                    modifier = Modifier
                        .clickable {
                            chatController.sendMessage(Message(
                                senderUid = uid!!,
                                receiverUid = receiverUid!!,
                                message = newMessage.value
                            ))
                        }
                )
            },
            placeholder = {
                Text(
                    text = "Write your message",
                    color = Color.Gray
                )
            }
        )
    }
}

@Composable
fun MessagesUI(
    modifier: Modifier
) {
    LazyColumn(
        modifier = modifier
            .padding(20.dp)
    ) {
        items(messages) { message ->
            Text(text = message)
        }
    }
}

val messages = listOf<String>("Hi", "Hello", "ki obostha?")