package com.example.hostelmate.hostel.presentation.ui.components.app_bars

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import com.example.hostelmate.R
import com.example.hostelmate.hostel.presentation.ui.navigation.AppNavGraph


@Composable
fun HostelAppBottomBar(
    currentDestination: NavDestination?,
    onBottomNavItemClick: (AppNavGraph) -> Unit
) {
    val insets = WindowInsets.navigationBars.asPaddingValues()

    HorizontalDivider(thickness = 1.dp, color = Color(0xFF323232))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp + insets.calculateBottomPadding())
            .navigationBarsPadding(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        topLevelRoutes.forEach { topLevelRoute ->
            val isSelected =
                currentDestination?.hierarchy?.any { it.hasRoute(topLevelRoute.route::class) } == true
            Button(
                onClick = {
                    if (!isSelected)
                        onBottomNavItemClick(topLevelRoute.route)
                },
                modifier = Modifier
                    .weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                contentPadding = PaddingValues(6.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(
                            if (isSelected) topLevelRoute.selectedIcon else topLevelRoute.unSelectedIcon
                        ),
                        contentDescription = topLevelRoute.displayName,
                        tint = if (isSelected) Color.Unspecified else Color.Unspecified,
                        modifier = Modifier
                            .size(24.dp)
                    )
                    Text(
                        text = topLevelRoute.displayName,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = if(isSelected) FontWeight.Bold else FontWeight.Normal,
                            fontSize = if(isSelected) 12.sp else 10.sp
                        )
                    )
                }
            }
        }
    }
}


val topLevelRoutes = listOf(
    TopLevelRoute("Explore", R.drawable.explore_outlined, R.drawable.explore_filled, AppNavGraph.MainNavGraph.Explore),
    TopLevelRoute("My Hostel", R.drawable.hostel_new_outlined, R.drawable.hostel_new_filled, AppNavGraph.MainNavGraph.MyHostel),
    TopLevelRoute("Account", R.drawable.account_outlined, R.drawable.account_filled, AppNavGraph.MainNavGraph.Account),
)

data class TopLevelRoute<T: Any>(
    val displayName: String,
    @DrawableRes val unSelectedIcon: Int,
    @DrawableRes val selectedIcon: Int,
    val route: T
)