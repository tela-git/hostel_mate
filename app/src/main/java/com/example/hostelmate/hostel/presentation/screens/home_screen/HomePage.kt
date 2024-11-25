package com.example.hostelmate.hostel.presentation.screens.home_screen


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.hostelmate.hostel.presentation.components.app_bars.HostelAppTopBar

@Composable
fun HomePage(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Scaffold(
       topBar = {
           HostelAppTopBar(
               onOptionsClick = { },
               onProfileClick = { },
               onBookmarksClick = { },
           )
       }
    ) { innerPadding->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

        }
    }
}