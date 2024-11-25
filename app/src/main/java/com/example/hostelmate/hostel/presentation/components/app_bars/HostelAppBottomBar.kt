package com.example.hostelmate.hostel.presentation.components.app_bars

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Message
import androidx.compose.material.icons.outlined.ControlPoint
import androidx.compose.material.icons.outlined.LocalHotel
import androidx.compose.material.icons.outlined.PinDrop
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.hostelmate.hostel.presentation.screens.Screens

@Composable
fun HostelAppBottomBar(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val currentRoute = navController.currentBackStackEntryAsState()?.value?.destination?.route

    BottomAppBar(

    ) {
       NavigationBar(

        ) {
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.PinDrop,
                        contentDescription = ""
                    )
                },
                onClick = {
                    navController.navigate(Screens.Home.route) {
                        popUpTo(Screens.Home.route)
                    }
                },
                selected = currentRoute == Screens.Home.route
            )
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.LocalHotel,
                        contentDescription = ""
                    )
                },
                onClick = {
                    navController.navigate(Screens.MyHostel.route) {
                        popUpTo(Screens.MyHostel.route)
                    }
                },
                selected = currentRoute == Screens.MyHostel.route
            )
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.ControlPoint,
                        contentDescription = ""
                    )
                },
                onClick = {
                    navController.navigate(Screens.Posts.route) {
                        popUpTo(Screens.Posts.route)
                    }
                },
                selected = currentRoute == Screens.Posts.route
            )
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.Message,
                        contentDescription = ""
                    )
                },
                onClick = {
                    navController.navigate(Screens.Chats.route) {
                        popUpTo(Screens.Chats.route)
                    }
                },
                selected = currentRoute == Screens.Chats.route
            )
        }
    }
}