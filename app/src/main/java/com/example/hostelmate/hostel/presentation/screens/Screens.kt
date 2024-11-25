package com.example.hostelmate.hostel.presentation.screens

sealed class Screens(val route: String) {
    data object OnBoarding : Screens(route = "onboarding")
    data object Login: Screens(route = "login")
    data object Signup: Screens(route = "signup")
    data object Home: Screens(route = "homepage")
    data object MyHostel: Screens(route= "my_hostel)")
    data object Posts: Screens(route = "posts")
    data object Chats: Screens(route = "chats")

}
