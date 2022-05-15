package com.example.medi_sheba

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
    @RequiresApi(Build.VERSION_CODES.O)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val auth: FirebaseAuth = FirebaseAuth.getInstance()
        val startDest = if (auth.uid != null)
            ScreenItem.HomeScreenItem.route
        else ScreenItem.IntroScreenItem.route

        setContent {
            medi_shebaTheme {


                Surface(
                    color = MaterialTheme.colors.background
                ) {

                    val navController = rememberNavController()
                    NavHost(
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
                            val userDetails = navController.previousBackStackEntry?.savedStateHandle?.get<User>("user")
                            userDetails?.let {
                                UpdateProfileScreen(navController = navController, auth, userDetails)
                            }
                        }
                        composable(route = ScreenItem.AppointmentScreenItem.route + "/{document_id}/{user_id}/{user_type}") { navBackStack ->
                            val document_id = navBackStack.arguments?.getString("document_id")
                            val user_id = navBackStack.arguments?.getString("user_id")
                            val user_type = navBackStack.arguments?.getString("user_type")
                            AppointmentScreen(
                                navController = navController,
                                document_id = document_id,
                                user_id = user_id,
                                user_type = user_type
                            )
                        }
                        composable(route = ScreenItem.AllAppointmentsScreenItem.route) {
                            AllAppointmentsScreen(navController = navController, auth)
                        }
                        composable(route = ScreenItem.AllDoctorsScreenItem.route) {
                            AllDoctorsScreen(navController = navController)
                        }
                        composable(route = ScreenItem.ChatUserListScreenItem.route) {
                            ChatUserListScreen(navController = navController)
                        }
                        composable(route = ScreenItem.ChatScreenItem.route + "/{receiverUid}/{receiverName}") { navBackStack ->
                            val receiverUid = navBackStack.arguments?.getString("receiverUid")
                            val receiverName = navBackStack.arguments?.getString("receiverName")
                            ChatScreen(
                                navController = navController,
                                receiverUid = receiverUid,
                                receiverName = receiverName
                            )
                        }
                        composable(route = ScreenItem.DashboardScreenItem.route) {
                            DashboardScreen(navController = navController)
                        }
                        composable(route = ScreenItem.MakeAndDeleteRoleItem.route + "/{roleName}") { navBackStack ->
                            val roleName = navBackStack.arguments?.getString("roleName")
                            MakeAndDeleteRole(navController = navController, roleName!!)
                        }
                        composable(route = ScreenItem.AllTopDoctorScreen.route) {
                            AllTopDoctorsScreen(navController = navController)
                        }
                        composable(route = ScreenItem.AllCategoryScreen.route) {
                            AllCategoryScreen(navController = navController)
                        }
                        composable(route = ScreenItem.BookAppointmentScreenItem.route + "/{name}/{designation}/{price}/{doctorUid}") { navBackStack ->
                            val name = navBackStack.arguments?.getString("name")
                            val designation = navBackStack.arguments?.getString("designation")
                            val price = navBackStack.arguments?.getString("price")
                            val doctorUid = navBackStack.arguments?.getString("doctorUid")
                            BookAppointmentScreen(
                                navController = navController,
                                name = name,
                                designation = designation,
                                price = price,
                                doctorUid = doctorUid
                            )
                        }
                        composable(route = ScreenItem.PaymentScreenItem.route + "/{doctorUid}/{time}/{serial}/{date}/{name}/{designation}") { navBackStack ->
                            val doctorUid = navBackStack.arguments?.getString("doctorUid")
                            val time = navBackStack.arguments?.getString("time")
                            val serial = navBackStack.arguments?.getString("serial")
                            val date = navBackStack.arguments?.getString("date")
                            val name = navBackStack.arguments?.getString("name")
                            val designation = navBackStack.arguments?.getString("designation")
                            PaymentScreen(
                                navController = navController,
                                doctorUid = doctorUid,
                                time = time,
                                serial = serial,
                                date = date,
                                name = name,
                                designation = designation
                            )
                        }
                    }
                }
            }
        }
    }
}