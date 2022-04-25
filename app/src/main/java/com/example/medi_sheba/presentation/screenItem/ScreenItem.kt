package com.example.medi_sheba.presentation.screenItem

sealed class ScreenItem(val route: String) {
    object HomeScreenItem: ScreenItem("home_screen")
    object ProfileScreenItem: ScreenItem("profile_screen")
    object SplashScreenItem: ScreenItem("splash_screen")
    object RegistrationScreenItem: ScreenItem("registration_screen")
    object IntroScreenItem: ScreenItem("intro_screen")
    object LoginScreenItem: ScreenItem("login_screen")
    object UpdateProfileScreenItem: ScreenItem("update_profile_screen")
    object AppointmentScreenItem: ScreenItem("appointment_screen")
    object AllAppointmentsScreenItem: ScreenItem("all_appointments_screen")
    object AllDoctorsScreenItem: ScreenItem("all_doctors_screen")
}
