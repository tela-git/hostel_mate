package com.example.hostelmate.hostel.presentation.ui.screens.main.explore

import android.graphics.Paint.Align
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.ElevatedCard
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