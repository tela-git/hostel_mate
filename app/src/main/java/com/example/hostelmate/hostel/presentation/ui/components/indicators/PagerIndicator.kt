package com.example.hostelmate.hostel.presentation.ui.components.indicators

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun PagerIndicator(currentPage: Int, totalPages: Int) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier
            .wrapContentWidth()
            .height(8.dp)
            .clip(RoundedCornerShape(4.dp))
    ) {
        repeat(totalPages) { index ->
            Box(
                modifier = Modifier
                    .animateContentSize()
                    .height(8.dp)
                    .width(if (index == currentPage) 24.dp else 8.dp)
                    .clip(CircleShape)
                    .background(
                        if (index == currentPage)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                    )
            )
        }
    }
}