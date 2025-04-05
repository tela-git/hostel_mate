package com.example.hostelmate.hostel.presentation.ui.components.loaders

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun LoadingPageOne() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(false) {  },
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}