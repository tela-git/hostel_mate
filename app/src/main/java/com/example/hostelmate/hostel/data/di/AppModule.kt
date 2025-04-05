package com.example.hostelmate.hostel.data.di

import com.example.hostelmate.hostel.data.repository.ExploreRepoImpl
import com.example.hostelmate.hostel.domain.repository.ExploreRepository
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import com.example.hostelmate.hostel.presentation.ui.screens.authentication.AuthViewModel
import com.example.hostelmate.hostel.presentation.ui.screens.main.explore.viewmodel.ExploreViewModel
import com.google.firebase.firestore.FirebaseFirestore

val appModule = module {
    single<FirebaseFirestore> { FirebaseFirestore.getInstance() }
    single<ExploreRepository> { ExploreRepoImpl(get()) }

    viewModelOf(::AuthViewModel)
    viewModelOf(::ExploreViewModel)
}