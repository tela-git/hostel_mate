package com.example.hostelmate.hostel.presentation.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class AppNavGraph {
    @Serializable data object AuthNavGraph: AppNavGraph() {
        @Serializable
        data object OnBoarding : AppNavGraph()
        @Serializable
        data object Login: AppNavGraph()
        @Serializable
        data object SignUp: AppNavGraph()
    }

    @Serializable data object MainNavGraph: AppNavGraph() {
        @Serializable
        data object Explore: AppNavGraph()
        @Serializable
        data object MyHostel: AppNavGraph()
        @Serializable
        data object Chats: AppNavGraph()
        @Serializable
        data object Account: AppNavGraph()
    }

}

val topLevelRoutes = ""