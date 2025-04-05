package com.example.hostelmate.hostel.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.hostelmate.hostel.presentation.ui.screens.authentication.LoginScreen
import com.example.hostelmate.hostel.presentation.ui.screens.authentication.SignUpScreen
import com.example.hostelmate.hostel.presentation.ui.screens.authentication.onboarding.OnBoardingScreen
import com.example.hostelmate.hostel.presentation.ui.screens.main.account.AccountScreen
import com.example.hostelmate.hostel.presentation.ui.screens.main.explore.ExploreScreen
import com.example.hostelmate.hostel.presentation.ui.screens.main.my_hostel.MyHostelScreen

@Composable
fun AppNavigation() {
    val appNavController = rememberNavController()

    NavHost(
        navController = appNavController,
        startDestination = AppNavGraph.MainNavGraph
    ) {
        authNavigation(
            appNavController = appNavController
        )
        mainNavigation(
            appNavController = appNavController
        )
    }
}