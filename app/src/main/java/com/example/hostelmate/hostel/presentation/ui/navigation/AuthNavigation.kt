package com.example.hostelmate.hostel.presentation.ui.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.hostelmate.hostel.presentation.ui.screens.authentication.LoginScreen
import com.example.hostelmate.hostel.presentation.ui.screens.authentication.SignUpScreen
import com.example.hostelmate.hostel.presentation.ui.screens.authentication.onboarding.OnBoardingScreen

fun NavGraphBuilder.authNavigation(
    appNavController: NavController
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
}