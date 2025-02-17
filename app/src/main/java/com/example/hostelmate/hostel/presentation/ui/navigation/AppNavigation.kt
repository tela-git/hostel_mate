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
        startDestination = AppNavGraph.AuthNavGraph
    ) {
        navigation<AppNavGraph.AuthNavGraph>(
            startDestination = AppNavGraph.AuthNavGraph.OnBoarding
        ) {
            composable<AppNavGraph.AuthNavGraph.OnBoarding> {
                OnBoardingScreen(
                    modifier = Modifier,
                    onNavigateToLogin = {
                        appNavController.navigate(AppNavGraph.AuthNavGraph.Login)
                    }
                )
            }
            composable<AppNavGraph.AuthNavGraph.Login> {
                LoginScreen(
                    onSignUpInsteadClick = {
                        appNavController.navigate(AppNavGraph.AuthNavGraph.SignUp)
                    },
                    onGuestLoginClick = {
                        appNavController.navigate(AppNavGraph.MainNavGraph) {
                            popUpTo(AppNavGraph.AuthNavGraph) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
            composable<AppNavGraph.AuthNavGraph.SignUp> {
                SignUpScreen(
                    onLoginInsteadClick = {
                        appNavController.navigate(AppNavGraph.AuthNavGraph.Login) {
                            popUpTo(AppNavGraph.AuthNavGraph.SignUp) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
        }

        navigation<AppNavGraph.MainNavGraph>(
            startDestination = AppNavGraph.MainNavGraph.Explore
        ) {
            composable<AppNavGraph.MainNavGraph.Explore> {
                ExploreScreen(
                    currentDestination = appNavController.currentDestination,
                    onBottomNavItemClick = {route ->
                        appNavController.navigate(route) {
                            popUpTo(AppNavGraph.MainNavGraph.Explore) {
                                saveState = true
                                inclusive = false
                            }
                            launchSingleTop = true
                        }
                    }
                )
            }
            composable<AppNavGraph.MainNavGraph.MyHostel> {
                MyHostelScreen(
                    currentDestination = appNavController.currentDestination,
                    onBottomNavIconClick = {route->
                        appNavController.navigate(route) {
                            popUpTo(AppNavGraph.MainNavGraph.Explore) {
                                saveState = true
                                inclusive = false
                            }
                            launchSingleTop = true
                        }
                    }
                )
            }
            composable<AppNavGraph.MainNavGraph.Account> {
                AccountScreen(
                    currentDestination = appNavController.currentDestination,
                    onBottomNavIconClick = {route->
                        appNavController.navigate(route) {
                            popUpTo(AppNavGraph.MainNavGraph.Explore) {
                                saveState = true
                                inclusive = false
                            }
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}