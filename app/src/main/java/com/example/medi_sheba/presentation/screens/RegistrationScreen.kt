package com.example.medi_sheba.presentation.screens

import android.app.Notification
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
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
import com.example.medi_sheba.model.User
import com.example.medi_sheba.presentation.screenItem.ScreenItem
import com.example.medi_sheba.ui.theme.PrimaryColor
import com.example.medi_sheba.ui.theme.SecondaryColor
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

inline fun Modifier.noRippleClickable(crossinline onClick: ()->Unit): Modifier = composed {
    clickable(indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RegistrationScreen(navController: NavController, auth: FirebaseAuth) {

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
            .verticalScroll(rememberScrollState())
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
                    text = "SIGN UP",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.h6
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "Please fill up this for creating an account.",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.body1,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp)
                .shadow(5.dp, shape = RoundedCornerShape(10.dp))
                .background(Color.White)
                .padding(vertical = 15.dp, horizontal = 25.dp)
        ) {

            var name by rememberSaveable { mutableStateOf("") }
            var email by rememberSaveable { mutableStateOf("") }
            var mobileNumber by rememberSaveable { mutableStateOf("") }
            var password by rememberSaveable { mutableStateOf("") }
            var age by rememberSaveable { mutableStateOf("") }
            var address by rememberSaveable { mutableStateOf("") }
            var passwordVisible by rememberSaveable { mutableStateOf(false) }
            val gender = remember { mutableStateOf("") }

            Column {
                TextField(
                    modifier = Modifier
                        .background(Color.White),
                    value = name,
                    onValueChange = { name = it },
                    placeholder = { Text("Name") },
                    maxLines = 1,
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White,
                        cursorColor = Color.Gray,
                        focusedIndicatorColor = Color.Gray
                    )
                )

                Spacer(modifier = Modifier.height(15.dp))

                TextField(
                    modifier = Modifier
                        .background(Color.White),
                    value = mobileNumber,
                    onValueChange = { mobileNumber = it },
                    placeholder = { Text("Mobile") },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White,
                        cursorColor = Color.Gray,
                        focusedIndicatorColor = Color.Gray
                    )
                )

                Spacer(modifier = Modifier.height(15.dp))

                TextField(
                    modifier = Modifier
                        .background(Color.White),
                    value = age,
                    onValueChange = { age = it },
                    placeholder = { Text("Age") },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White,
                        cursorColor = Color.Gray,
                        focusedIndicatorColor = Color.Gray
                    )
                )

                Spacer(modifier = Modifier.height(15.dp))

                TextField(
                    modifier = Modifier
                        .background(Color.White),
                    value = address,
                    onValueChange = { address = it },
                    placeholder = { Text("Address") },
                    maxLines = 1,
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White,
                        cursorColor = Color.Gray,
                        focusedIndicatorColor = Color.Gray
                    )
                )

                Spacer(modifier = Modifier.height(15.dp))

                displayGenderRadio(gender)

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
                            if (
                                name == "" || email == "" || password == "" || mobileNumber == ""
                                || age == "" || address == "" || gender.value == ""
                            ) {
                                Toast
                                    .makeText(context, "Fill up all fields", Toast.LENGTH_SHORT)
                                    .show()
                            } else {
                                isLoading = true
                                auth
                                    .createUserWithEmailAndPassword(email.trim(), password.trim())
                                    .addOnCompleteListener() { task ->
                                        if (task.isSuccessful) {
                                            val authUser = task.result.user
                                            val db = Firebase.firestore

                                            val user = User(
                                                uid = authUser!!.uid,
                                                name = name,
                                                email = email,
                                                mobileNumber = mobileNumber,
                                                age = age,
                                                address = address,
                                                gender = gender.value
                                            )
                                            db.collection("users")
                                                .document(authUser.uid)
                                                .set(user)
                                                .addOnSuccessListener {
                                                    navController.navigate(ScreenItem.HomeScreenItem.route) {
                                                        popUpTo(0)
                                                    }
                                                }
                                                .addOnFailureListener { e ->
                                                    isLoading = false
                                                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
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
                        text = "SIGN UP",
                        color = Color.White,
                        style = MaterialTheme.typography.h6
                    )
                }
            }
        }
    }
}

@Composable
fun displayGenderRadio(gender: MutableState<String>){
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = gender.value == "Male", onClick = { gender.value = "Male" },
                colors = RadioButtonDefaults.colors(
                    selectedColor = PrimaryColor
                )
            )
            Text(
                text = "Male",
                modifier = Modifier
                    .clickable(onClick = { gender.value = "Male" })
                    .padding(start = 4.dp)
            )
            Spacer(modifier = Modifier.size(4.dp))
            RadioButton(
                selected = gender.value == "Female", onClick = { gender.value = "Female" },
                colors = RadioButtonDefaults.colors(
                    selectedColor = PrimaryColor
                )
            )
            Text(
                text = "Female",
                modifier = Modifier
                    .clickable(onClick = { gender.value = "Female" })
                    .padding(start = 4.dp)
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = gender.value == "Other", onClick = { gender.value = "Other" },
                colors = RadioButtonDefaults.colors(
                    selectedColor = PrimaryColor
                )
            )
            Text(
                text = "Other",
                modifier = Modifier
                    .clickable(onClick = { gender.value = "Other" })
                    .padding(start = 4.dp)
            )
        }
    }
}

