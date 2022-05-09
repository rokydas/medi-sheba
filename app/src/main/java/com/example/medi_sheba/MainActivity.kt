package com.example.medi_sheba

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.medi_sheba.FirestoreAll.DataOrException
import com.example.medi_sheba.FirestoreAll.Product
import com.example.medi_sheba.FirestoreAll.ProductsViewModel
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
                            val userDetails =
                                navController.previousBackStackEntry?.arguments?.getParcelable<User>(
                                    "user"
                                )
                            UpdateProfileScreen(navController = navController, auth, userDetails!!)
                        }
                        composable(route = ScreenItem.AppointmentScreenItem.route) {
                            AppointmentScreen(
                                navController = navController,
                                bottomNavController = null
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
                    }
                }
            }
        }



        @Composable
        fun ProductsActivity(dataOrException: DataOrException<List<Product>, Exception>) {
            val products = dataOrException.data
            products?.let {
                LazyColumn {
                    items(
                        items = products
                    ) { product ->
//                    ProductCard(product = product)
                        Log.d(TAG, "==================---------=====================")
                        Log.d(TAG, "ProductsActivity: $product")
                    }
                }
            }

//        val e = dataOrException.e
//        e?.let {
//            Text(
//                text = e.message!!,
//                modifier = Modifier.padding(16.dp)
//            )
//        }
//
//        Column(
//            modifier = Modifier.fillMaxSize(),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            CircularProgressBar(
//                isDisplayed = viewModel.loading.value
//            )
//        }
        }
    }
}