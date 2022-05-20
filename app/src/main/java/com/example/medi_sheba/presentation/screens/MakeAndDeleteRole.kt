package com.example.medi_sheba.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.medi_sheba.controllers.MakeAndDeleteRoleController
import com.example.medi_sheba.ui.theme.PrimaryColor
import com.example.medi_sheba.ui.theme.PrimaryColorLight
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun MakeAndDeleteRole(navController: NavController, roleName: String) {

    val makeAndDeleteRoleController = MakeAndDeleteRoleController()
    val userList = makeAndDeleteRoleController.userList.observeAsState()
    val auth = Firebase.auth
    val context = LocalContext.current

    makeAndDeleteRoleController.getUserList(context)

    Scaffold(
        topBar = {
            AppBar(navController = navController, title = "Update Role")
        }
    ) {
        when(userList.value) {
            null -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            else -> {
                val users = userList.value!!.filter { user ->
                    user.email != auth.currentUser?.email
                }
                if(users.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = "There is no user")
                    }
                }
                else {
                    LazyColumn {
                        item {
                            Spacer(modifier = Modifier.height(20.dp))
                        }
                        items(users) { user ->
                            if(user.uid != auth.currentUser?.uid) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 20.dp, vertical = 5.dp)
                                        .clip(RoundedCornerShape(5.dp))
                                        .background(PrimaryColorLight)
                                        .padding(10.dp)
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Spacer(modifier = Modifier.width(15.dp))
                                        Text(text = user.name)
                                        Spacer(modifier = Modifier.width(15.dp))
                                        Box(
                                            modifier = Modifier
                                                .background(PrimaryColor)
                                                .padding(horizontal = 10.dp, vertical = 5.dp)
                                                .clickable {
                                                    makeAndDeleteRoleController.changeRole(
                                                        roleName = if (user.userType == roleName) "Patient" else roleName,
                                                        uid = user.uid
                                                    ) { isSuccess ->
                                                        if (isSuccess) {
                                                            Toast
                                                                .makeText(
                                                                    context,
                                                                    "Role updated successfully",
                                                                    Toast.LENGTH_SHORT
                                                                )
                                                                .show()
                                                            makeAndDeleteRoleController.getUserList(
                                                                context
                                                            )
                                                        } else {
                                                            Toast
                                                                .makeText(
                                                                    context,
                                                                    "Something went wrong",
                                                                    Toast.LENGTH_SHORT
                                                                )
                                                                .show()
                                                        }
                                                    }
                                                },
                                            contentAlignment = Alignment.Center,
                                        ) {
                                            Text(
                                                text = if (user.userType == roleName) "Delete $roleName" else "Make $roleName",
                                                color = Color.White,
                                                style = MaterialTheme.typography.body1
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
