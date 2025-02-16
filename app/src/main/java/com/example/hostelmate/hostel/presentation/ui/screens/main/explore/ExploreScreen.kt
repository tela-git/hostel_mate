package com.example.hostelmate.hostel.presentation.ui.screens.main.explore

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Sort
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.SearchBarDefaults
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import com.example.hostelmate.R
import com.example.hostelmate.hostel.data.fake_data.AvailableCity
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
) {
    val viewModel: ExploreViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    var currentHostelType: HostelType? by remember { mutableStateOf(null) }
    val filteredList = uiState.hostels?.let { hostels ->
        if (currentHostelType != null) {
            hostels.filter { it.type == currentHostelType }
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
            items(
                items = filteredList ?: emptyList(),
                key = { it.id }
            ) {hostel->
                HostelCard(
                    hostel = hostel
                )
            }
        }
        if(uiState.isFilterSheetVisible) {
            ModalBottomSheet(
                onDismissRequest = {
                    scope.launch {
                        sheetState.hide()
                        viewModel.updateFilterSheetVisibility(false)
                    }
                }
            ) {
                FilterSheetContent(
                    onClearFilters = {
                        scope.launch {
                            sheetState.hide()
                            viewModel.updateFilterSheetVisibility(false)
                            viewModel.clearFilters()
                            currentHostelType = null
                        }
                    },
                    uiState = uiState,
                    updateFilterCity = {
                        viewModel.filterUpdateCity(it)
                        scope.launch {
                            sheetState.hide()
                            viewModel.updateFilterSheetVisibility(false)
                        }
                                       },
                    updateFilterHostelType = {
                        currentHostelType = it
                        scope.launch {
                            sheetState.hide()
                            viewModel.updateFilterSheetVisibility(false)
                        } },
                    updateFilterSortBy = {
                        viewModel.filterUpdateSortType(it)
                        scope.launch {
                            sheetState.hide()
                            viewModel.updateFilterSheetVisibility(false)
                        }
                    },
                    currentHostelType = currentHostelType
                )
            }
        }
        if(uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(enabled = false) { },
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
private fun FilterSheetContent(
    onClearFilters: () -> Unit,
    uiState: ExploreUIState,
    updateFilterCity: (AvailableCity) -> Unit,
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
        Card(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    shape = RoundedCornerShape(16.dp)
                )
                .fillMaxWidth()
                .weight(1f)
                .height(32.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
            )
        ) {
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
        }
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