package com.example.hostelmate.hostel.presentation.ui.screens.main.explore

import androidx.lifecycle.ViewModel
import com.example.hostelmate.hostel.data.model.Hostel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ExploreViewModel(
): ViewModel() {
    private val _uiState = MutableStateFlow(ExploreUIState())
    val uiState = _uiState.asStateFlow()

    //fun getHostelsList(city: AvailableCity) {

    //}
}

data class ExploreUIState(
    val hostels: List<Hostel>? = null,
    val isLoading: Boolean = false,
)