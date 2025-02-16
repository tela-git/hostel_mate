package com.example.hostelmate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.hostelmate.hostel.presentation.ui.navigation.AppNavigation
import com.example.hostelmate.hostel.presentation.ui.theme.HostelMateTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HostelMateTheme {
                AppNavigation()
            }
        }
    }
}
