package com.example.medi_sheba.presentation

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun HomeScreen(navController: NavController, auth: FirebaseAuth) {
    Text(text = "Hello World")
    Button(
        onClick = {
            navController.navigate(Screen.ProfileScreen.route)
        }
    ) {
        Text(text = "Go to Profile Screen")
    }
}