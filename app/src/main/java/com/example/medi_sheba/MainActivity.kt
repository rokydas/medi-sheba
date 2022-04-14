package com.example.medi_sheba

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.medi_sheba.presentation.*
import com.example.medi_sheba.presentation.profile.ProfileScreen
import com.example.medi_sheba.ui.theme.medi_shebaTheme
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val auth: FirebaseAuth = FirebaseAuth.getInstance()

        setContent {
            medi_shebaTheme {
                Surface(
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost (
                        navController = navController,
                        startDestination = Screen.SplashScreen.route,
                    ) {
                        composable(route = Screen.HomeScreen.route) {
                            HomeScreen(navController = navController, auth)
                        }
                        composable(route = Screen.ProfileScreen.route) {
                            ProfileScreen(navController = navController, auth)
                        }
                        composable(route = Screen.SplashScreen.route) {
                            SplashScreen(navController = navController, auth)
                        }
                        composable(route = Screen.IntroScreen.route) {
                            IntroScreen(navController = navController)
                        }
                        composable(route = Screen.RegistrationScreen.route) {
                            RegistrationScreen(navController = navController, auth)
                        }
                        composable(route = Screen.LoginScreen.route) {
                            LoginScreen(navController = navController, auth)
                        }
                        composable(route = Screen.UpdateProfileScreen.route) {
                            UpdateProfile(navController = navController, auth)
                        }
                    }
                }
            }
        }
    }
}