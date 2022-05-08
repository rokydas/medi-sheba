package com.example.medi_sheba.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.medi_sheba.controllers.MakeAndDeleteRoleController
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

    Scaffold {
        when(userList.value) {
            null -> {
                CircularProgressIndicator()
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
                        items(users) { user ->
                            if(user.uid != auth.currentUser?.uid) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 20.dp)
                                        .clip(RoundedCornerShape(25.dp))
                                        .background(PrimaryColorLight)
                                        .padding(20.dp)
                                ) {
                                    Row {
                                        Text(text = user.name)
                                        if (user.userType == roleName) {
                                            Button(
                                                onClick = {
                                                    makeAndDeleteRoleController.changeRole("Patient", user.uid) { isSuccess ->
                                                        if (isSuccess) {
                                                            Toast.makeText(context,"Role updated successfully", Toast.LENGTH_SHORT).show()
                                                            makeAndDeleteRoleController.getUserList(context)
                                                        } else {
                                                            Toast.makeText(context,"Something went wrong", Toast.LENGTH_SHORT).show()
                                                        }
                                                    }
                                                }
                                            ) {
                                                Text(text = "Delete $roleName")
                                            }
                                        }
                                        else {
                                            Button(
                                                onClick = {
                                                    makeAndDeleteRoleController.changeRole(roleName, user.uid) { isSuccess ->
                                                        if (isSuccess) {
                                                            Toast.makeText(context,"Role updated successfully", Toast.LENGTH_SHORT).show()
                                                            makeAndDeleteRoleController.getUserList(context)
                                                        } else {
                                                            Toast.makeText(context,"Something went wrong", Toast.LENGTH_SHORT).show()
                                                        }
                                                    }
                                                }
                                            ) {
                                                Text(text = "Make $roleName")
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
}
