package com.example.medi_sheba.presentation.screens
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.medi_sheba.controllers.ChatUserListController
import com.example.medi_sheba.presentation.screenItem.ScreenItem
import com.example.medi_sheba.ui.theme.PrimaryColorLight
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun ChatUserListScreen(navController: NavController) {

    val context = LocalContext.current
    val chatUserListController = ChatUserListController()
    val chatUserList = chatUserListController.chatUserList.observeAsState()
    val auth = Firebase.auth

    chatUserListController.getChatUserList(context)

    Scaffold {
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
                    val userList = chatUserList.value!!.filter { user ->
                        user.email != auth.currentUser?.email
                    }
                    if(userList.isEmpty()) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text(text = "There is no user")
                        }
                    }
                    else {
                        LazyColumn {
                            items(userList) { user ->
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 20.dp)
                                        .background(PrimaryColorLight)
                                        .padding(20.dp)
                                        .clickable {
                                            navController.navigate(
                                                ScreenItem.ChatScreenItem.route
                                                    + "/" + user.uid
                                            )
                                        }
                                ) {
                                    Text(text = user.name)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}