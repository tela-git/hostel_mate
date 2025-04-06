package com.example.hostelmate.hostel.presentation.ui.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.example.hostelmate.hostel.data.fake_data.dummyTopHostels
import com.example.hostelmate.hostel.presentation.ui.screens.main.account.AccountScreen
import com.example.hostelmate.hostel.presentation.ui.screens.main.explore.ExploreScreen
import com.example.hostelmate.hostel.presentation.ui.screens.main.explore.HostelCard
import com.example.hostelmate.hostel.presentation.ui.screens.main.explore.HostelDetailScreen
import com.example.hostelmate.hostel.presentation.ui.screens.main.my_hostel.MyHostelScreen
import kotlinx.coroutines.MainScope

fun NavGraphBuilder.mainNavigation(
    appNavController: NavController
) {
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
                },
                navigateToHostelDetail = { id->
                    appNavController.navigate(AppNavGraph.MainNavGraph.HostelDetail(id))
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
                },
                navigateToLogin = {
                    appNavController.navigate(AppNavGraph.AuthNavGraph) {
                        popUpTo<AppNavGraph.MainNavGraph> {
                            inclusive = true
                        }
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
                },
                onBackToLogin = {
                    appNavController.navigate(AppNavGraph.AuthNavGraph) {
                        popUpTo<AppNavGraph.MainNavGraph> {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable<AppNavGraph.MainNavGraph.HostelDetail> {backStack->
            val id = backStack.toRoute<AppNavGraph.MainNavGraph.HostelDetail>().hostelId
            HostelDetailScreen(
                hostelId = id,
                navigateUp = {
                    appNavController.navigateUp()
                }
            )
        }
    }
}