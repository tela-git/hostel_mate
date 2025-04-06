package com.example.hostelmate.hostel.presentation.ui.screens.main.explore

import android.graphics.Paint.Align
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Downloading
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Boy
import androidx.compose.material.icons.outlined.Girl
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.hostelmate.R
import com.example.hostelmate.hostel.data.model.Hostel
import com.example.hostelmate.hostel.data.model.HostelType

@Composable
fun HostelCard(
    hostel: Hostel,
    onClick: (String) -> Unit,
) {
    ElevatedCard(
        onClick = { onClick(hostel.id) },
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 6.dp,
            pressedElevation = 20.dp
        )
    ) {
        Box(
          modifier = Modifier
              .fillMaxWidth(0.95f)
              .aspectRatio(16f / 9f),
            contentAlignment = Alignment.TopStart
        ) {
            Box(
                contentAlignment = Alignment.BottomStart,
                modifier = Modifier
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(hostel.pictures.firstOrNull())
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(10.dp)),
                    contentScale = ContentScale.Crop,
                    alpha = 0.9f,
                    placeholder = painterResource(R.drawable.glass_loading_png),
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = hostel.name,
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.primaryContainer,
                                shape = RoundedCornerShape(0.dp, 6.dp, 6.dp, 10.dp)
                            )
                            .padding(4.dp)
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.primaryContainer,
                                shape = RoundedCornerShape(6.dp, 0.dp, 10.dp, 6.dp)
                            )
                            .padding(4.dp),
                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier
                                .size(12.dp)
                        )
                        Text(
                            text = hostel.rating.toString(),
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.secondaryContainer,
                            shape = RoundedCornerShape(10.dp, 6.dp, 6.dp, 0.dp)
                        )
                        .padding(4.dp)
                ) {
                    Text(
                        text = hostel.city,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    )
                }
                if(hostel.hostelType == HostelType.BOYS) {
                    Box(
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.primaryContainer,
                                shape = RoundedCornerShape(6.dp, 10.dp, 0.dp, 6.dp)
                            )
                            .padding(2.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Boys",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                ),
                                modifier = Modifier.padding(2.dp, 0.dp, 0.dp, 0.dp)
                            )
                            Icon(
                                imageVector = Icons.Outlined.Boy,
                                contentDescription = "boys hostel",
                                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                } else if(hostel.hostelType == HostelType.GIRLS) {
                    Box(
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.primaryContainer,
                                shape = RoundedCornerShape(6.dp, 10.dp, 0.dp, 6.dp)
                            )
                            .padding(2.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Girls",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                ),
                                modifier = Modifier.padding(2.dp, 0.dp, 0.dp, 0.dp)
                            )
                            Icon(
                                imageVector = Icons.Outlined.Girl,
                                contentDescription = "girls hostel",
                                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun HosteslCard(
    hostel: Hostel
) {
    ElevatedCard(
        modifier = Modifier
            .sizeIn(
                minHeight = 240.dp
            )
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = hostel.name,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Medium
                    ),
                    modifier = Modifier
                        .weight(1f),
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(hostel.pictures.first())
                        .crossfade(true)
                        .build(),
                    contentDescription = "",
                    placeholder = painterResource(R.drawable.loading),
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .size(200.dp, 120.dp)
                        .aspectRatio(2f / 1.2f)
                        .clip(RoundedCornerShape(10.dp))
                )
                Column(

                ) {
                    Text(
                        text = "Type: " + if(hostel.hostelType == HostelType.GIRLS) "Girls" else "Boys",
                        style = MaterialTheme.typography.labelLarge
                    )
                    Text(
                        text = "Rating: ${hostel.rating}",
                        style = MaterialTheme.typography.bodySmall
                    )
//                    Text(
//                        text = "Fee: ₹${hostel.fee}",
//                        style = MaterialTheme.typography.bodyMedium
//                    )
                    Text(
                        text = "Place: ${hostel.city}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
            Column(
              modifier = Modifier
                  .fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "Facilities: ",
                    style = MaterialTheme.typography.labelLarge
                )
                val fac = hostel.facilities.joinToString(" ,")
                Text(
                    text = fac,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}