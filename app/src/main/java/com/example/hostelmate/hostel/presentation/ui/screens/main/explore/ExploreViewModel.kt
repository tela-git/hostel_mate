package com.example.hostelmate.hostel.presentation.ui.screens.main.explore

import androidx.lifecycle.ViewModel
import com.example.hostelmate.hostel.data.model.Hostel

class ExploreViewModel(
): ViewModel() {

}

data class ExploreUIState(
    val hostels: Hostel
)