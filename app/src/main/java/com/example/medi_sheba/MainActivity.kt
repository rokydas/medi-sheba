package com.example.medi_sheba

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.medi_sheba.model.User
import com.example.medi_sheba.presentation.screenItem.ScreenItem
import com.example.medi_sheba.presentation.screens.ProfileScreen
import com.example.medi_sheba.presentation.screens.*
import com.example.medi_sheba.ui.theme.PrimaryColor
import com.example.medi_sheba.ui.theme.medi_shebaTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val auth: FirebaseAuth = FirebaseAuth.getInstance()

        setContent {
            medi_shebaTheme {
                val systemUiController = rememberSystemUiController()
                systemUiController.setStatusBarColor(PrimaryColor)
                Surface(
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = ScreenItem.SplashScreenItem.route
                    ) {
                        composable(route = ScreenItem.HomeScreenItem.route) {
                            HomeScreen(navController = navController, auth)
                        }
                        composable(route = ScreenItem.ProfileScreenItem.route) {
                            ProfileScreen(navController = navController, auth)
                        }
                        composable(route = ScreenItem.SplashScreenItem.route) {
                            SplashScreen(navController = navController, auth)
                        }
                        composable(route = ScreenItem.IntroScreenItem.route) {
                            IntroScreen(navController = navController)
                        }
                        composable(route = ScreenItem.RegistrationScreenItem.route) {
                            RegistrationScreen(navController = navController, auth)
                        }
                        composable(route = ScreenItem.LoginScreenItem.route) {
                            LoginScreen(navController = navController, auth)
                        }
                        composable(route = ScreenItem.UpdateProfileScreenItem.route) {
                            val userDetails = navController.previousBackStackEntry?.savedStateHandle?.get<User>("user")
                            userDetails?.let {
                                UpdateProfileScreen(navController = navController, auth, userDetails)
                            }
                        }
                    }
                }
            }
        }
    }
}