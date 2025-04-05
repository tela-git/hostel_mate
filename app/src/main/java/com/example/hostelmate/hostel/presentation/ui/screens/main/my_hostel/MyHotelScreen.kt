package com.example.hostelmate.hostel.presentation.ui.screens.main.my_hostel

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import com.example.hostelmate.R
import com.example.hostelmate.hostel.presentation.ui.components.app_bars.HostelAppBottomBar
import com.example.hostelmate.hostel.presentation.ui.navigation.AppNavGraph

@Composable
fun MyHostelScreen(
    modifier: Modifier = Modifier,
    currentDestination: NavDestination?,
    onBottomNavIconClick: (AppNavGraph) -> Unit,
    navigateToLogin: () -> Unit
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
            Image(
                painter = painterResource(R.drawable.go),
                contentDescription = null,
                modifier = Modifier,
                alpha = 0.5f
            )
            Spacer(Modifier.height(20.dp))
            Text(
                text = "OOPS, YOU ARE NOT LOGGED IN...!",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(Modifier.height(20.dp))
            Button(
                onClick = navigateToLogin,
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    text = "Login Now !"
                )
            }
        }
    }
}