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
    object ChatUserListScreenItem: ScreenItem("chat_user_list_screen")
    object ChatScreenItem: ScreenItem("chat_screen")
    object DashboardScreenItem: ScreenItem("dashboard_screen")
    object MakeAndDeleteRoleItem: ScreenItem("make_and_delete_role_screen")
    object AllCategoryScreen : ScreenItem("all_category")
    object BookAppointmentScreenItem : ScreenItem("book_appointment_screen")
    object PaymentScreenItem : ScreenItem("payment_screen")
}
