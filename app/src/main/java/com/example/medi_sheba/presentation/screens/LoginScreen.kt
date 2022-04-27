package com.example.medi_sheba.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.example.medi_sheba.presentation.screenItem.ScreenItem
import com.example.medi_sheba.ui.theme.PrimaryColor
import com.example.medi_sheba.ui.theme.SecondaryColor
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoginScreen(navController: NavController, auth: FirebaseAuth) {
    val context = LocalContext.current
    var isLoading by rememberSaveable { mutableStateOf(false) }

    if(isLoading) {
        Dialog(
            onDismissRequest = {  },
            DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
        ) {
            Box(
                contentAlignment= Alignment.Center,
                modifier = Modifier
                    .size(100.dp)
                    .background(Color.Transparent, shape = RoundedCornerShape(8.dp))
            ) {
                CircularProgressIndicator(color = PrimaryColor)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Column {
            Row {
                Icon(
                    modifier = Modifier
                        .clickable {
                            navController.popBackStack()
                        },
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "back"
                )
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    text = "SIGN IN",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.h6
                )
            }

            Spacer(modifier = Modifier.height(50.dp))
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "Enter your email and password\n to sign in",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.body1,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(50.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp)
                .shadow(5.dp, shape = RoundedCornerShape(10.dp))
                .background(Color.White)
                .padding(vertical = 15.dp, horizontal = 25.dp)

        ) {

            var email by rememberSaveable { mutableStateOf("") }
            var password by rememberSaveable { mutableStateOf("") }
            var passwordVisible by rememberSaveable { mutableStateOf(false) }

            Column {
                TextField(
                    modifier = Modifier
                        .background(Color.White),
                    value = email,
                    onValueChange = { email = it },
                    placeholder = { Text("Email") },
                    maxLines = 1,
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White,
                        cursorColor = Color.Gray,
                        focusedIndicatorColor = Color.Gray
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )

                Spacer(modifier = Modifier.height(15.dp))

                TextField(
                    modifier = Modifier
                        .background(Color.White),
                    value = password,
                    onValueChange = { password = it },
                    placeholder = { Text("Password") },
                    maxLines = 1,
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White,
                        cursorColor = Color.Gray,
                        focusedIndicatorColor = Color.Gray
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val image = if (passwordVisible)
                            Icons.Filled.Visibility
                        else Icons.Filled.VisibilityOff
                        val description = if (passwordVisible) "Hide password" else "Show password"

                        IconButton(onClick = {passwordVisible = !passwordVisible}){
                            Icon(imageVector  = image, description)
                        }
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))

                val gradient = Brush.horizontalGradient(listOf(SecondaryColor, PrimaryColor))

                Box(
                    modifier = Modifier
                        .noRippleClickable() {
                            if (email == "" || password == "") {
                                Toast.makeText(context, "Fill up all fields", Toast.LENGTH_SHORT).show()
                            } else {
                                isLoading = true
                                auth.signInWithEmailAndPassword(email.trim(), password.trim())
                                    .addOnCompleteListener() { task ->
                                        if (task.isSuccessful) {
                                            navController.navigate(ScreenItem.HomeScreenItem.route) {
                                                popUpTo(0)
                                            }
                                        } else {
                                            isLoading = false
                                            Toast.makeText(context, task.exception?.message!!, Toast.LENGTH_SHORT).show()
                                        }
                                    }
                            }
                        }
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(gradient)
                        .padding(8.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "SIGN IN",
                        color = Color.White,
                        style = MaterialTheme.typography.h6
                    )
                }
            }
        }
    }
}