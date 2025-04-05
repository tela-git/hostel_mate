package com.example.hostelmate.hostel.presentation.ui.screens.main.explore

import android.graphics.ImageDecoder
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.SnapPosition
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Sort
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SecondaryScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.traceEventStart
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.hostelmate.R
import com.example.hostelmate.hostel.data.fake_data.dummyTopHostels
import com.example.hostelmate.hostel.data.model.Hostel
import com.example.hostelmate.hostel.data.model.HostelType
import com.example.hostelmate.hostel.presentation.ui.components.app_bars.HostelAppBottomBar
import com.example.hostelmate.hostel.presentation.ui.navigation.AppNavGraph
import com.example.hostelmate.hostel.presentation.ui.screens.main.explore.viewmodel.ExploreUIState
import com.example.hostelmate.hostel.presentation.ui.screens.main.explore.viewmodel.ExploreViewModel
import com.example.hostelmate.hostel.presentation.ui.screens.main.explore.viewmodel.SortType
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreScreen(
    modifier: Modifier = Modifier,
    currentDestination: NavDestination?,
    onBottomNavItemClick: (AppNavGraph) -> Unit,
    navigateToHostelDetail: (String) -> Unit
) {
    val viewModel: ExploreViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    var currentHostelType: HostelType? by remember { mutableStateOf(null) }
    val filteredList = uiState.hostels?.let { hostels ->
        if (currentHostelType != null) {
            hostels.filter { it.hostelType == currentHostelType }
        } else {
            hostels
        }
    }

    Scaffold(
        topBar = {
            ExploreScreenTopAppBar()
        },
        bottomBar = {
            HostelAppBottomBar(
                currentDestination = currentDestination,
                onBottomNavItemClick = onBottomNavItemClick
            )
        }
    ) { innerPadding->

        Box(
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.background_relax),
                contentDescription = null,
                alpha = 0.15f,
                contentScale = ContentScale.Fit
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    FilterOptions(
                        searchText = uiState.searchText,
                        onSearchTextChange = viewModel::updateInputSearchString,
                        onSortClick = {
                            scope.launch {
                                sheetState.show()
                                viewModel.updateFilterSheetVisibility(true)
                            }
                        }
                    )
                }
                item {
                    AnimatedVisibility(
                        visible = uiState.searchText.isNotEmpty(),
                        enter = fadeIn() + slideInHorizontally(),
                        exit = fadeOut() + slideOutVertically()
                    ) {
                        CityAndSortOptions()
                    }
                }
                item {
                    AnimatedVisibility(
                        visible = uiState.searchText.isEmpty(),
                        enter = fadeIn() + slideInVertically(),
                        exit = fadeOut() + slideOutVertically()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(20.dp)
                        ) {
                            HomeBanner()
                            TopRatedHostels(
                                topHostels = dummyTopHostels,
                                navigateToHostelDetail = navigateToHostelDetail
                            )
                        }
                    }
                }
//            items(
//                items = filteredList ?: emptyList(),
//                key = { it.id }
//            ) { hostel->
//                HostelCard(
//                    hostel = hostel
//                )
//            }
            }
        }
//        if(uiState.isFilterSheetVisible) {
//            ModalBottomSheet(
//                onDismissRequest = {
//                    scope.launch {
//                        sheetState.hide()
//                        viewModel.updateFilterSheetVisibility(false)
//                    }
//                }
//            ) {
//                FilterSheetContent(
//                    onClearFilters = {
//                        scope.launch {
//                            sheetState.hide()
//                            viewModel.updateFilterSheetVisibility(false)
//                            viewModel.clearFilters()
//                            currentHostelType = null
//                        }
//                    },
//                    uiState = uiState,
//                    updateFilterCity = {
//                        viewModel.filterUpdateCity(it)
//                        scope.launch {
//                            sheetState.hide()
//                            viewModel.updateFilterSheetVisibility(false)
//                        }
//                                       },
//                    updateFilterHostelType = {
//                        currentHostelType = it
//                        scope.launch {
//                            sheetState.hide()
//                            viewModel.updateFilterSheetVisibility(false)
//                        } },
//                    updateFilterSortBy = {
//                        viewModel.filterUpdateSortType(it)
//                        scope.launch {
//                            sheetState.hide()
//                            viewModel.updateFilterSheetVisibility(false)
//                        }
//                    },
//                    currentHostelType = currentHostelType
//                )
//            }
//        }
        if(uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(false) {},
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
fun TopRatedHostels(
    topHostels: List<Hostel>,
    navigateToHostelDetail: (String) -> Unit,
) {
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { topHostels.size })

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = "Top Rated Hostels (in ${topHostels.firstOrNull()?.city ?: "City"})",
            style = MaterialTheme.typography.titleMedium.copy(
                color = MaterialTheme.colorScheme.primary
            )
        )
        HorizontalPager(
            state = pagerState,
            pageSpacing = 4.dp,
            contentPadding = PaddingValues(horizontal = 24.dp),
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            var imageLoaded by remember { mutableStateOf(false) }

            Box(
                contentAlignment = Alignment.BottomStart,
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .aspectRatio(16f / 9f)
                    .clickable {
                        navigateToHostelDetail(topHostels[page].id)
                    }
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(topHostels[page].pictures.firstOrNull())
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    onSuccess = { imageLoaded = true },
                    onLoading = { imageLoaded = false },
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
                        text = topHostels[page].name,
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
                            text = topHostels[page].rating.toString(),
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HomeBanner() {
    var selected by remember { mutableStateOf(1) }

    Row {
            listOf("EXPLORE", "SETTLE", "ENJOY").forEachIndexed { index, text ->
                val isSelected = selected == index + 1
                val animatedWidth by animateDpAsState(
                    targetValue = if (isSelected) 160.dp else 80.dp,
                    animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
                    label = "Box Width"
                )

                val animatedBorderColor by animateColorAsState(
                    targetValue = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.tertiary,
                    animationSpec = tween(durationMillis = 300),
                    label = "Border Color"
                )

                val animatedBackgroundColor by animateColorAsState(
                    targetValue = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.1f) else Color.Transparent,
                    animationSpec = tween(durationMillis = 300),
                    label = "Background Color"
                )

                val cornerShape = when (index) {
                    0 -> RoundedCornerShape(20.dp, 0.dp, 0.dp, 20.dp)
                    1 -> RoundedCornerShape(0.dp)
                    else -> RoundedCornerShape(0.dp, 20.dp, 20.dp, 0.dp)
                }

                Box(
                    modifier = Modifier
                        .background(color = animatedBackgroundColor, shape = cornerShape)
                        .border(
                            width = 1.dp,
                            color = animatedBorderColor,
                            shape = cornerShape
                        )
                        .size(width = animatedWidth, height = 80.dp)
                        .clickable { selected = index + 1 }
                        .animateContentSize(
                            animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = text
                    )
                }
            }
        }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CityAndSortOptions() {
    val sortOptions = listOf("nearby","popular", "price: low-high", "price: high-low")
    var selectedIndex by remember { mutableStateOf(0) }
    SecondaryScrollableTabRow(
        selectedTabIndex = selectedIndex,
        divider = { },
        indicator = { },
        edgePadding = 4.dp
    ) {
        sortOptions.forEach { option->
            val selected = selectedIndex == sortOptions.indexOf(option)
            OutlinedButton(
                onClick = { selectedIndex = sortOptions.indexOf(option)},
                contentPadding = PaddingValues(6.dp),
                modifier = Modifier
                    .height(32.dp)
                    .padding(horizontal = 4.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = if(selected) MaterialTheme.colorScheme.primaryContainer else Color.Unspecified
                )
            ) {
                Text(
                    text = option,
                    style = MaterialTheme.typography.labelMedium,
                    color = if(selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.tertiary
                )
            }
        }
    }
}

@Composable
private fun FilterSheetContent(
    onClearFilters: () -> Unit,
    uiState: ExploreUIState,
    //updateFilterCity: (AvailableCity) -> Unit,
    updateFilterHostelType: (HostelType) -> Unit,
    updateFilterSortBy: (SortType) -> Unit,
    currentHostelType: HostelType?
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = "Clear Filters",
                style = MaterialTheme.typography.bodyMedium
            )
            IconButton(
                onClick = onClearFilters
            ) {
               Icon(
                   imageVector = ImageVector.vectorResource(R.drawable.cancel),
                   contentDescription = ""
               )
            }
        }
        HorizontalDivider(
            thickness = 1.dp,
            color = Color.Black
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "Hostel Type",
                style = MaterialTheme.typography.titleMedium
            )
            TabRow(
                divider = { },
                contentColor = Color.Black,
                containerColor = Color.Transparent,
                indicator = { },
                selectedTabIndex = if(currentHostelType == HostelType.GIRLS) 0 else 1
            ) {
                Tab(
                    selected = uiState.genderFilter?.name == "Girls",
                    onClick = {
                        updateFilterHostelType(HostelType.GIRLS)
                    },
                    selectedContentColor = MaterialTheme.colorScheme.primary,
                ) {
                    Text(
                        text = "Girls",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Tab(
                    selected = uiState.genderFilter?.name == "Boys",
                    onClick = {
                        updateFilterHostelType(HostelType.BOYS)
                    }
                ) {
                    Text(
                        text = "Boys",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
        Spacer(Modifier.height(40.dp))
    }
}

@Composable
private fun FilterOptions(
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    onSortClick: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        OutlinedTextField(
            value = searchText,
            onValueChange = onSearchTextChange,
            trailingIcon = {
                if(searchText.isNotEmpty()) {
                    Icon(
                        imageVector = Icons.Outlined.Close,
                        contentDescription = "Clear search",
                        modifier = Modifier
                            .clickable { onSearchTextChange("") }
                    )
                }
            },
            modifier = Modifier
                .height(48.dp),
            textStyle = MaterialTheme.typography.bodyMedium,
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedBorderColor = Color.Unspecified,
                unfocusedBorderColor = Color.Unspecified,
            ),
            placeholder = {
                Text(
                    text= "search for city, hostel name, ...",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(
                        alpha = 0.8f
                    )
                )
            },
            maxLines = 1,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = { focusManager.clearFocus() }
            )
        )
        IconButton(
            onClick = {
                onSortClick()
            },
            modifier = Modifier
        ) {
            Icon(
                imageVector = Icons.Default.Tune,
                contentDescription = "Sort",
                modifier = Modifier

            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreScreenTopAppBar() {
    TopAppBar(
        title = {
            Text(
                text = "Hostel Mate",
                style = MaterialTheme.typography.titleLarge
            )
        }
    )
}

/*
BasicTextField(
                value = searchText,
                onValueChange = onSearchTextChange,
                modifier = Modifier
                    .padding(start = 12.dp, top = 8.dp, end = 12.dp),
                singleLine = true,
                textStyle = TextStyle(
                    fontSize = 14.sp
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        focusManager.clearFocus()
                    }
                ),
                decorationBox = { innerTextField->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Box(
                            modifier = Modifier.weight(1f)
                        ) {
                             innerTextField() // The actual text field content
                        }
                        if(searchText.isEmpty()) {
                            Text(
                                text = "search hostel name, city, ...",
                                modifier = Modifier
                                    .fillMaxWidth(),
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(
                                    alpha = 0.7f
                                )
                            )
                        }
                        if (searchText.isNotEmpty()) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Clear text",
                                tint = Color.Black,
                                modifier = Modifier
                                    .padding(bottom = 4.dp)
                                    .clickable {
                                        focusManager.clearFocus()
                                        onSearchTextChange("")
                                    }
                            )
                        }
                    }
                }
            )
 */