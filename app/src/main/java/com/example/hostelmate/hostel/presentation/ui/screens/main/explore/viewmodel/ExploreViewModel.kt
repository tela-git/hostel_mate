package com.example.hostelmate.hostel.presentation.ui.screens.main.explore.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hostelmate.hostel.data.fake_data.AvailableCity
import com.example.hostelmate.hostel.data.fake_data.dummyTopHostels
import com.example.hostelmate.hostel.data.model.Hostel
import com.example.hostelmate.hostel.data.model.HostelType
import com.example.hostelmate.hostel.domain.repository.ExploreRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ExploreViewModel(
    private val exploreRepository: ExploreRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(ExploreUIState())
    val uiState = _uiState.asStateFlow()

    private val _hostelDetails = MutableStateFlow(HostelDetailsUI())
    val hostelDetails = _hostelDetails.asStateFlow()

    private fun searchHostels(key: String) {
        viewModelScope.launch {
            _uiState.update { state->
                state.copy(
                    originalList = emptyList(),
                    hostels = emptyList(),
                    isLoading = true,
                    notFound = false
                )
            }
            exploreRepository.searchHostels(key = key) { hostels->
                if(hostels.isNullOrEmpty()) {
                    _uiState.update { state->
                        state.copy(
                            originalList = emptyList(),
                            hostels = emptyList(),
                            isLoading = false,
                            notFound = true
                        )
                    }
                } else {
                    _uiState.update { state->
                        state.copy(
                            isLoading = false,
                            hostels = hostels,
                            notFound = false,
                            originalList = hostels
                        )
                    }
                }
            }
        }
    }

    fun getHostelInfo(hostelId: String) {
        if(hostelId != hostelDetails.value.hostel?.id) {
            viewModelScope.launch {
                _hostelDetails.update { state->
                    state.copy(
                        hostel = null,
                        isError = false,
                        isLoading = true
                    )
                }
                exploreRepository.getHostelInfo(
                    hostelId = hostelId,
                    onComplete = { hostel->
                        if(hostel == null) {
                            _hostelDetails.update { state->
                                state.copy(
                                    hostel = null,
                                    isError = true,
                                    isLoading = false,
                                )
                            }
                        }
                        else {
                            _hostelDetails.update { state->
                                state.copy(
                                    hostel = hostel,
                                    isError = false,
                                    isLoading = false
                                )
                            }
                        }
                    }
                )
            }
        }
    }

    fun updateInputSearchString(searchText: String) {
        _uiState.update { state->
            state.copy(
                searchText = searchText
            )
        }
        searchHostels(key = searchText)
    }

    private fun filterHostelsList() {
        viewModelScope.launch {
            val allHostels = uiState.value.originalList
            val genderFilter = uiState.value.genderFilter
            val sortType = uiState.value.sortType

            if (allHostels == null) {
                Log.e("HostelFilter", "Original hostel list is null")
                return@launch
            }

            var filtered = allHostels

            if (genderFilter != null) {
                filtered = filtered.filter { it.hostelType == genderFilter }
            }

            filtered = when (sortType) {
                SortType.Popular -> filtered.sortedByDescending { it.rating }
                SortType.PriceLowToHigh -> filtered.sortedBy { it.stayOptions.minOfOrNull { it.cost } ?: Int.MAX_VALUE }
                SortType.PriceHighToLow -> filtered.sortedByDescending { it.stayOptions.maxOfOrNull { it.cost } ?: Int.MIN_VALUE }
                SortType.NearBy -> filtered
                null -> filtered
            }

            _uiState.update {
                if(uiState.value.hostels.isNullOrEmpty()) {
                    it.copy(
                        hostels = filtered,
                        notFound = false
                ) } else {
                    it.copy(
                        hostels = filtered,
                        notFound = true
                    )
                }
            }

            if (filtered.isEmpty()) {
                Log.w("HostelFilter", "No hostels found after filtering")
            }
        }
    }



    fun filterUpdateHostelType(hostelType: HostelType?) {
        _uiState.update { it.copy(genderFilter = hostelType) }
        filterHostelsList()
    }
    fun filterUpdateSortType(sortType: SortType) {
        _uiState.update { it.copy(sortType = sortType) }
        filterHostelsList()
    }

}

data class ExploreUIState(
    val originalList : List<Hostel>? = null,
    val hostels: List<Hostel>? = null,
    val isLoading: Boolean = false,
    val searchText: String = "",
    val sortType: SortType? = SortType.Popular,
    val genderFilter: HostelType? = null,
    val notFound: Boolean = false
)

data class HostelDetailsUI(
    val hostel: Hostel? = null,
    val isError: Boolean = false,
    val isLoading: Boolean = true
)

sealed class SortType(val name: String) {
    data object NearBy: SortType("Near by")
    data object Popular: SortType("Popular")
    data object PriceLowToHigh: SortType("Price: Low to High")
    data object PriceHighToLow: SortType("Price: High to Low")
}

val sortOptions = listOf(SortType.NearBy, SortType.Popular, SortType.PriceLowToHigh, SortType.PriceHighToLow)
