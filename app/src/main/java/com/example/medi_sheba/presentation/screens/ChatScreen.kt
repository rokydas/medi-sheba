package com.example.medi_sheba.presentation.screens

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
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
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChatScreen(navController: NavController, receiverUid: String?) {

    val chatController = ChatController()
    val auth = Firebase.auth
    val uid = auth.currentUser?.uid
    val context = LocalContext.current
    val messageLists = chatController.messageLists.observeAsState()
    val dateFormatter = DateTimeFormatter.ofPattern("h:mm:s a")

    chatController.getMessages(receiverUid + "_" + uid)

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
            if (messageLists.value != null) {
                MessagesUI(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    messageLists = messageLists.value!!
                )
            }
            MessageField(newMessage, chatController, uid, receiverUid, context, dateFormatter)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MessageField(
    newMessage: MutableState<String>,
    chatController: ChatController,
    uid: String?,
    receiverUid: String?,
    context: Context,
    dateFormatter: DateTimeFormatter
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
                            val current = LocalDateTime.now()
                            val formattedDate = current.format(dateFormatter)
                            chatController.sendMessage(Message(
                                senderUid = uid!!,
                                receiverUid = receiverUid!!,
                                message = newMessage.value,
                                time = formattedDate
                            ),
                                context = context
                            )
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
    modifier: Modifier,
    messageLists: List<Message>
) {
    LazyColumn(
        modifier = modifier
            .padding(20.dp)
    ) {
        items(messageLists) { messageData ->
            Text(text = messageData.message)
        }
    }
}