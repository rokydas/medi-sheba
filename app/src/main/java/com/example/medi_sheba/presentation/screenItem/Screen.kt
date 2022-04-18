package com.example.medi_sheba.presentation.screenItem

sealed class Screen(val route: String) {
    object MainScreen: Screen("home_screen")
    object ProfileScreen: Screen("profile_screen")
    object SplashScreen: Screen("splash_screen")
    object RegistrationScreen: Screen("registration_screen")
    object IntroScreen: Screen("intro_screen")
    object LoginScreen: Screen("login_screen")
    object UpdateProfileScreen: Screen("update_profile_screen")
    object AppointmentScreen: Screen("appointment_screen")
    object AllAppointmentsScreen: Screen("all_appointments_screen")
    object AllDoctorsScreen: Screen("all_doctors_screen")
}
