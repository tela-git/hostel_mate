package com.example.hostelmate.hostel.data.di

import androidx.lifecycle.viewmodel.compose.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import com.example.hostelmate.hostel.presentation.ui.screens.authentication.AuthViewModel
import com.example.hostelmate.hostel.presentation.ui.screens.main.explore.ExploreViewModel

val appModule = module {
    viewModelOf(::AuthViewModel)
    viewModelOf(::ExploreViewModel)
}