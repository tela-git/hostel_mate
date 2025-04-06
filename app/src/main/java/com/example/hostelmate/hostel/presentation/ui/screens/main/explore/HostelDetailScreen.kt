package com.example.hostelmate.hostel.presentation.ui.screens.main.explore

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardDoubleArrowDown
import androidx.compose.material.icons.filled.KeyboardDoubleArrowUp
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.hostelmate.R
import com.example.hostelmate.hostel.data.fake_data.dummyTopHostels
import com.example.hostelmate.hostel.data.model.Hostel
import com.example.hostelmate.hostel.presentation.ui.components.indicators.PagerIndicator
import com.example.hostelmate.hostel.presentation.ui.components.loaders.LoadingPageOne
import com.example.hostelmate.hostel.presentation.ui.screens.main.explore.viewmodel.ExploreViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HostelDetailScreen(
    hostelId: String,
    navigateUp: () -> Unit,
) {

    val exploreViewModel : ExploreViewModel = koinViewModel()
    val hostelUI by exploreViewModel.hostelDetails.collectAsState()
    val hostel = hostelUI.hostel

    LaunchedEffect(Unit) {
        exploreViewModel.getHostelInfo(hostelId = hostelId)
    }

    var isLiked by remember { mutableStateOf(false) }

    // intent for g maps
    val context = LocalContext.current
    val latitude = hostel?.coordinates?.latitude
    val longitude = hostel?.coordinates?.longitude
    val gmmIntentUri = Uri.parse("geo:$latitude,$longitude?q=$latitude,$longitude(${Uri.encode(hostel?.name)})")
    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri).apply {
        setPackage("com.google.android.apps.maps")
    }

    val shareText = "Check out this hostel : ${hostel?.name} in ${hostel?.city}. This is rated ${hostel?.rating}. For details contact hostel manager: ${hostel?.managerName} ${hostel?.managerContact}"
    val shareIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, shareText)
        type = "text/plain"
    }

    val phoneNumber = hostel?.managerContact
    val callIntent = Intent(Intent.ACTION_DIAL).apply {
        data = Uri.parse("tel:$phoneNumber")
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(
                        onClick = { navigateUp() }
                    ){
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Navigate up",
                        )
                    }
                },
                actions = {
                    if(hostel != null) {
                        IconButton(
                            onClick = {
                               context.startActivity(Intent.createChooser(shareIntent, "Share via"))
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Share,
                                contentDescription = "share hostel",
                                modifier = Modifier
                                    .size(24.dp)
                            )
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            if(hostel != null) {
                Column {
                    FloatingActionButton(
                        onClick = {
                            context.startActivity(callIntent)
                        },
                        modifier = Modifier
                            .padding(horizontal = 12.dp, vertical = 16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Call,
                            contentDescription = "Call hostel",
                        )
                    }
                    FloatingActionButton(
                        onClick = {
                            context.startActivity(mapIntent)
                        },
                        modifier = Modifier
                            .padding(horizontal = 12.dp)
                            .padding(bottom = 20.dp)
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Filled.LocationOn,
                                contentDescription = "Locate hostel",
                            )
                            Text(
                                text = "locate",
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    }
                }
            }
        }
    ) { innerPadding->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            if(hostel != null) {
                Text(
                    text = hostel.name,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.primary
                    )
                )
                Spacer(Modifier.height(20.dp))
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(
                            state = rememberScrollState()
                        ),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Hostel Images
                    HostelImages(
                        hostel = hostel
                    )

                    // Hostel Ratings
                    Ratings(
                        hostel = hostel,
                        onLikeClick = { isLiked = !isLiked },
                        isLiked = isLiked
                    )

                    // Hostel Facilities
                    HostelFacilities(
                        hostel = hostel
                    )

                    // Stay Options
                    StayOptions(
                        hostel = hostel
                    )

                    // Hostel description
                    HostelDescription(
                        hostel = hostel
                    )

                    // Owner Information
                    ManagerInfo(
                        hostel = hostel
                    )

                    Spacer(Modifier.height(60.dp))
                }
            }
            else if(hostelUI.isLoading) {
                LoadingPageOne()
            }
            else if(hostelUI.isError) {
                ErrorMessage()
            }
        }
    }
}

@Composable
fun ErrorMessage() {
    Column(
      modifier = Modifier
          .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(Modifier.height(160.dp))
        Image(
            painter = painterResource(R.drawable.error_bg),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp)),
            alpha = 0.9f
        )
        Spacer(Modifier.height(20.dp))
        Text(
            text = "DO DETAILS FOUND !",
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
fun HostelImages(
    hostel: Hostel
) {
    if(hostel.pictures.isNotEmpty()) {
        val pagerState = rememberPagerState(0, pageCount = { hostel.pictures.size })

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            HorizontalPager(
                state = pagerState,
                pageSpacing = 2.dp,
                contentPadding = PaddingValues(horizontal = 16.dp),
                modifier = Modifier.fillMaxWidth()
            ) { page ->
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(hostel.pictures[page])
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth(0.98f)
                        .aspectRatio(8f / 6f)
                        .clip(RoundedCornerShape(10.dp)),
                    contentScale = ContentScale.Crop,
                    alpha = 0.9f,
                    placeholder = painterResource(R.drawable.glass_loading_png),
                )
            }
            PagerIndicator(
                currentPage = pagerState.currentPage,
                totalPages = hostel.pictures.size
            )
        }
    } else {
        Box(
            contentAlignment = Alignment.BottomStart
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(R.drawable.error_bg),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .aspectRatio(16f / 9f)
                        .clip(RoundedCornerShape(10.dp)),
                    contentScale = ContentScale.Fit,
                )
            }
            Text(
                text = "Images not available",
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(0.dp, 6.dp, 6.dp, 10.dp)
                    )
                    .padding(4.dp)
            )
        }
    }
}

@Composable
private fun StayOptions(
    hostel: Hostel
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = "Stay Options"
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            hostel.stayOptions.forEach { option->
                Box(
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(10.dp)
                        )
                ) {
                    Text(
                        text = option.sharing.toString()+ " sharing " + " : " + " ₹ " + option.cost.toString(),
                        modifier = Modifier
                            .padding(6.dp),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun ManagerInfo(
    hostel: Hostel
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = "Manager Information"
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ){
            Text(
                text = "Name: " + hostel.managerName,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.primary
                )
            )
            Text(
                text = "Contact Number: " + if(hostel.managerContact.isEmpty()) "Unavailable" else hostel.managerContact,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.primary
                )
            )
        }
    }
}

@Composable
private fun HostelFacilities(
    hostel: Hostel
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = "Facilities"
        )
        hostel.facilities.forEach { facility->
            Box(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(10.dp)
                    )
            ) {
                Text(
                    text = facility,
                    modifier = Modifier
                        .padding(6.dp),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.primary
                    )
                )
            }
        }
    }
}



@Composable
private fun Ratings(
    hostel: Hostel,
    onLikeClick: () -> Unit,
    isLiked: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Ratings
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.StarRate,
                contentDescription = "Ratings + ${hostel.rating}",
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .size(18.dp)
            )
            Text(
                text = "Rated: " + hostel.rating,
                style = MaterialTheme.typography.labelLarge.copy(
                    color = MaterialTheme.colorScheme.secondary
                )
            )
        }
        // Add to favorite
        Icon(
            imageVector = if(isLiked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
            contentDescription = "Add to favorite",
            modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .clickable { onLikeClick() },
            tint = Color.Red
        )
    }
}

@Composable
private fun HostelDescription(hostel: Hostel) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = "About"
        )
        Row(
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = hostel.description,
                maxLines = if (expanded) Int.MAX_VALUE else 3,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .weight(1f)
                    .animateContentSize()
            )
            Icon(
                imageVector = if (expanded) Icons.Default.KeyboardDoubleArrowUp else Icons.Default.KeyboardDoubleArrowDown,
                contentDescription = if (expanded) "collapse description" else "expand description",
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .clickable {
                        expanded = !expanded
                    }
            )
        }
    }
}
