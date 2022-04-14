package com.example.medi_sheba.presentation

sealed class Screen(val route: String) {
    object HomeScreen: Screen("home_screen")
    object ProfileScreen: Screen("profile_screen")
    object SplashScreen: Screen("splash_screen")
    object RegistrationScreen: Screen("registration_screen")
    object IntroScreen: Screen("intro_screen")
    object LoginScreen: Screen("login_screen")
    object UpdateProfileScreen: Screen("update_profile_screen")
}
