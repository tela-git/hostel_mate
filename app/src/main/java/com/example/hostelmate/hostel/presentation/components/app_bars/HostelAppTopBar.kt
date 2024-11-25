package com.example.hostelmate.hostel.presentation.components.app_bars

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.hostelmate.core.presentation.components.AppTopBar

@Composable
fun HostelAppTopBar(
    onBookmarksClick: () -> Unit,
    onProfileClick: () -> Unit,
    onOptionsClick: () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = { },
) {
    AppTopBar(
        actions = {
            IconButton(
                onClick = { onBookmarksClick() },
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .size(26.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Bookmarks,
                    contentDescription = "Bookmarked hostels",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            IconButton(
                onClick = { onProfileClick() },
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .size(26.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.AccountCircle,
                    contentDescription = "Your account",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            IconButton(
                onClick = { onOptionsClick() },
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .size(26.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.MoreVert,
                    contentDescription = "More options",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        modifier = Modifier,
        navigationIcon = navigationIcon
    )
}