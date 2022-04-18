package com.example.medi_sheba.presentation

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.medi_sheba.R
import com.example.medi_sheba.model.Doctor
import com.example.medi_sheba.model.doctors
import com.example.medi_sheba.presentation.profile.ProfileScreen
import com.example.medi_sheba.presentation.screenItem.BottomNavItem
import com.example.medi_sheba.presentation.util.gridItems
import com.example.medi_sheba.ui.theme.PrimaryColor
import com.example.medi_sheba.ui.theme.PrimaryColorLight
import com.example.medi_sheba.ui.theme.background
import com.google.firebase.auth.FirebaseAuth

@Composable
fun MainScreen(navController: NavHostController, auth: FirebaseAuth) {

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) {

        val bottomNavController = rememberNavController()

        NavHost(navController = bottomNavController, startDestination = BottomNavItem.Home.route) {
            composable(BottomNavItem.Home.route) {
                HomeScreen(navController = navController)
            }
            composable(BottomNavItem.Appointment.route) {
                AppointmentScreen(navController = navController, bottomNavController = bottomNavController)
            }
            composable(BottomNavItem.Profile.route) {
                ProfileScreen(navController = navController, auth = FirebaseAuth.getInstance())
            }
        }
    }
}
