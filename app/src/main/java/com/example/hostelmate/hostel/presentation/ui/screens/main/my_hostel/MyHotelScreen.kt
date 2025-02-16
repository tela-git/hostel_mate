package com.example.hostelmate.hostel.presentation.ui.screens.main.my_hostel

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import com.example.hostelmate.hostel.presentation.ui.components.app_bars.HostelAppBottomBar
import com.example.hostelmate.hostel.presentation.ui.navigation.AppNavGraph

@Composable
fun MyHostelScreen(
    modifier: Modifier = Modifier,
    currentDestination: NavDestination?,
    onBottomNavIconClick: (AppNavGraph) -> Unit,
) {
    Scaffold(
        bottomBar = {
            HostelAppBottomBar(
                currentDestination = currentDestination,
                onBottomNavItemClick = onBottomNavIconClick
            )
        }
    ) { innerPadding->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "My Hotel Page"
            )
        }
    }
}