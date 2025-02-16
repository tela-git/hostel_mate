package com.example.hostelmate.hostel.presentation.ui.screens.authentication.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.hostelmate.R
import com.example.hostelmate.hostel.presentation.ui.components.buttons.LongButtonPrimary

@Composable
fun OnBoardingScreen(
    modifier: Modifier = Modifier,
    onNavigateToLogin: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.hostel_mate_onboarding_no_bg),
            contentDescription = null,
            modifier = Modifier
        )
        Text(
            text = stringResource(R.string.onboarding_slogan),
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = stringResource(R.string.onboarding_supporting_text),
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(Modifier.height(100.dp))
        LongButtonPrimary(
            onClick = {
                onNavigateToLogin()
            },
            text = stringResource(R.string.lets_go),
            isEnabled = true,
            modifier = Modifier
                .fillMaxWidth(0.9f)
        )
    }
}