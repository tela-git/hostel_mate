package com.example.hostelmate.hostel.presentation.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.hostelmate.core.presentation.onboarding.OnBoardingScreen
import com.example.hostelmate.hostel.presentation.components.app_bars.HostelAppBottomBar
import com.example.hostelmate.hostel.presentation.screens.authentication.AuthViewModel
import com.example.hostelmate.hostel.presentation.screens.authentication.LoginScreen
import com.example.hostelmate.hostel.presentation.screens.authentication.SignUpScreen
import com.example.hostelmate.hostel.presentation.screens.chats.ChatsPage
import com.example.hostelmate.hostel.presentation.screens.home_screen.HomePage
import com.example.hostelmate.hostel.presentation.screens.my_hostel.MyHostelPage
import com.example.hostelmate.hostel.presentation.screens.posts.PostsPage

@Composable
fun HostelMateApp(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {

    val authViewModel = AuthViewModel()
    val screensWithoutBottomBar = listOf("login", "signup", "onboarding")

    Scaffold(
        bottomBar = {
            if (!screensWithoutBottomBar.contains(navController.currentBackStackEntryAsState().value?.destination?.route)) {
                HostelAppBottomBar(navController = navController)
            }
        }
    ) { innerPadding->
        NavHost(
            navController = navController,
            startDestination = Screens.OnBoarding.route
        ) {
            composable(
                route = Screens.OnBoarding.route
            ) {
                OnBoardingScreen(
                    modifier = Modifier
                        .padding(innerPadding),
                    navController = navController
                )
            }
            composable(
                route = Screens.Login.route
            ) {
                LoginScreen(
                    navController = navController,
                    modifier = Modifier
                        .padding(innerPadding),
                    authViewModel = authViewModel
                )
            }
            composable(
                route = Screens.Signup.route
            ) {
                SignUpScreen(
                    navController = navController,
                    modifier = Modifier
                        .padding(innerPadding),
                    authViewModel = authViewModel
                )
            }
            composable(
                route = Screens.Home.route
            ) {
                HomePage(
                    modifier = modifier
                        .padding(innerPadding),
                    navController = navController
                )
            }
            composable(
                route = Screens.MyHostel.route
            ) {
                MyHostelPage(
                    modifier = modifier
                        .padding(innerPadding),
                    navController = navController
                )
            }
            composable(
                route = Screens.Posts.route
            ) {
                PostsPage(
                    modifier = modifier
                        .padding(innerPadding),
                    navController = navController
                )
            }
            composable(
                route = Screens.Chats.route
            ) {
                ChatsPage(
                    modifier = modifier
                        .padding(innerPadding),
                    navController = navController
                )
            }
        }
    }
}