package com.example.medi_sheba.presentation.screens
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.medi_sheba.controllers.ChatUserListController
import com.example.medi_sheba.model.appointments
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun ChatUserListScreen(navController: NavController) {

    val context = LocalContext.current
    val chatUserListController = ChatUserListController()
    val chatUserList = chatUserListController.chatUserList.observeAsState()
    val auth = Firebase.auth

    chatUserListController.getChatUserList(context)

    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController,
            title = "Chat") }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            when(chatUserList.value) {
                null -> {
                    CircularProgressIndicator()
                }
                else -> {
                    if(chatUserList.value!!.isEmpty()) {
                        Text(text = "no user")
                    }
                    LazyColumn {
                        val userList = chatUserList.value!!.filter { user ->
                            user.email == auth.currentUser?.email
                        }
                        items(userList) { user ->
                            Text(text = user.name)
                        }
                    }
                }
            }
        }
    }
}