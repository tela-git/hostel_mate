package com.example.hostelmate.hostel.presentation.ui.screens.main.explore.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hostelmate.hostel.data.fake_data.AvailableCity
import com.example.hostelmate.hostel.data.fake_data.dummyData
import com.example.hostelmate.hostel.data.model.Hostel
import com.example.hostelmate.hostel.data.model.HostelType
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ExploreViewModel(
): ViewModel() {
    private val _uiState = MutableStateFlow(ExploreUIState())
    val uiState = _uiState.asStateFlow()

    private fun getAllHostelsList() {
        viewModelScope.launch{
            _uiState.update { it.copy(isLoading = true) }
           // delay(1500)
            _uiState.update { it.copy(
                isLoading = false,
                hostels = dummyData
            ) }
        }

    }
    fun updateInputSearchString(searchText: String) {
        _uiState.update { state->
            state.copy(
                searchText = searchText
            )
        }
        filterHostelsList()
    }
    private fun filterHostelsList() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            delay(1000)
            val filteredList = dummyData.filter { hostel ->
                hostel.name.startsWith(uiState.value.searchText, ignoreCase = true) }
//            val sortedList = when (uiState.value.sortType) {
//                SortType.POPULAR -> filteredList.sortedByDescending { it.rating }
//                SortType.PRICE_HIGH_TO_LOW -> filteredList.sortedByDescending { it.fee }
//                SortType.PRICE_LOW_TO_HIGH -> filteredList.sortedBy { it.fee }
//                null -> filteredList
//            }
            _uiState.update { it.copy(hostels = filteredList, isLoading = false) }

        }
    }

    fun updateFilterSheetVisibility(boolean: Boolean) {
        _uiState.update { it.copy(isFilterSheetVisible = boolean) }
    }

    fun filterUpdateCity(city: AvailableCity) {
        _uiState.update { it.copy(city = city) }
        filterHostelsList()
    }
    fun filterUpdateHostelType(hostelType: HostelType) {
        _uiState.update { it.copy(genderFilter = hostelType) }
        filterHostelsList()
    }
    fun filterUpdateSortType(sortType: SortType) {
        _uiState.update { it.copy(sortType = sortType) }
        filterHostelsList()
    }
    fun clearFilters() {
        _uiState.update {
            it.copy(
                city = null,
                sortType = null,
                genderFilter = null,
                hostels = dummyData
            )
        }
    }

    init {
        getAllHostelsList()
    }
}

data class ExploreUIState(
    val hostels: List<Hostel>? = null,
    val isLoading: Boolean = false,
    val searchText: String = "",
    val isFilterSheetVisible: Boolean = false,
    val city: AvailableCity? = null,
    val sortType: SortType? = SortType.POPULAR,
    val genderFilter: HostelType? = null
)

enum class SortType {
    POPULAR,
    PRICE_LOW_TO_HIGH,
    PRICE_HIGH_TO_LOW
}

