package com.example.hostelmate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.hostelmate.hostel.presentation.screens.HostelMateApp
import com.example.hostelmate.ui.theme.HostelMateTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HostelMateTheme {
                val navController = rememberNavController()
                HostelMateApp(
                    modifier = Modifier
                        .fillMaxSize(),
                    navController = navController
                )
            }
        }
    }
}
