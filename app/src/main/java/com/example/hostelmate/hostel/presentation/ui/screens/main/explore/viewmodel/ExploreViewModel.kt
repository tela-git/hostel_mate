package com.example.hostelmate.hostel.presentation.ui.screens.main.explore.viewmodel

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
                    hostels = emptyList(),
                    isLoading = true,
                    notFound = false
                )
            }
            exploreRepository.searchHostels(key = key) { hostels->
                if(hostels.isNullOrEmpty()) {
                    _uiState.update { state->
                        state.copy(
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
                            notFound = false
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
            _uiState.update { it.copy(isLoading = true) }
            delay(1000)

            _uiState.update { it.copy(hostels = dummyTopHostels, isLoading = false) }

        }
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
                sortType = null,
                genderFilter = null,
                hostels = null
            )
        }
    }

}

data class ExploreUIState(
    val hostels: List<Hostel>? = null,
    val isLoading: Boolean = false,
    val searchText: String = "",
    val sortType: SortType? = SortType.POPULAR,
    val genderFilter: HostelType? = null,
    val notFound: Boolean = false
)

data class HostelDetailsUI(
    val hostel: Hostel? = null,
    val isError: Boolean = false,
    val isLoading: Boolean = true
)

enum class SortType {
    POPULAR,
    PRICE_LOW_TO_HIGH,
    PRICE_HIGH_TO_LOW
}

