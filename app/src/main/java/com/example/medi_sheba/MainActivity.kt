package com.example.medi_sheba

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.medi_sheba.model.User
import com.example.medi_sheba.presentation.screenItem.ScreenItem
import com.example.medi_sheba.presentation.screens.ProfileScreen
import com.example.medi_sheba.presentation.screens.*
import com.example.medi_sheba.ui.theme.medi_shebaTheme
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val auth: FirebaseAuth = FirebaseAuth.getInstance()
        val startDest = if(auth.uid != null)
            ScreenItem.HomeScreenItem.route
        else ScreenItem.IntroScreenItem.route

        setContent {
            medi_shebaTheme {
                Surface(
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost (
                        navController = navController,
                        startDestination = startDest
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
                            val userDetails = navController.previousBackStackEntry?.arguments?.getParcelable<User>("user")
                            UpdateProfileScreen(navController = navController, auth, userDetails!!)
                        }
                        composable(route = ScreenItem.AppointmentScreenItem.route) {
                            AppointmentScreen(navController = navController, bottomNavController = null)
                        }
                        composable(route = ScreenItem.AllAppointmentsScreenItem.route) {
                            AllAppointmentsScreen(navController = navController)
                        }
                        composable(route = ScreenItem.AllDoctorsScreenItem.route) {
                            AllDoctorsScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}