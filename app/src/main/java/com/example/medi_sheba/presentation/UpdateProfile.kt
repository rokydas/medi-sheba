package com.example.medi_sheba.presentation

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun UpdateProfile(navController: NavController, auth: FirebaseAuth) {
    Text(text = "update profile screen")
}