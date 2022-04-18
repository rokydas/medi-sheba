package com.example.medi_sheba.presentation.screenItem

import com.example.medi_sheba.R

sealed class BottomNavItem(var route: String, var icon: Int, var title: String) {
    object Appointment : BottomNavItem("home", R.drawable.appointment, "Appointment")
    object Home : BottomNavItem("music", R.drawable.home, "Home")
    object Profile : BottomNavItem("movies", R.drawable.appointment, "Profile")
}
