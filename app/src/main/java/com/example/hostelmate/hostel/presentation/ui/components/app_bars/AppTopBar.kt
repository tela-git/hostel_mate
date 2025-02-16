package com.example.hostelmate.hostel.presentation.ui.components.app_bars

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.hostelmate.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    navigationIcon: @Composable () -> Unit = { },
    actions: @Composable() (RowScope.() -> Unit) = {},
    modifier: Modifier
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.hostel_mate_headline),
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Medium,
                fontSize = 24.sp
            )
        },
        navigationIcon = navigationIcon,
        actions = actions,
        modifier = modifier
    )
}