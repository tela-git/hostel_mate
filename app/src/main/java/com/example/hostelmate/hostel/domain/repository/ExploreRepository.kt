package com.example.hostelmate.hostel.domain.repository

import com.example.hostelmate.hostel.data.model.Hostel

interface ExploreRepository {
    suspend fun getHostelInfo(hostelId: String, onComplete: (Hostel?) -> Unit)
    suspend fun searchHostels(key: String, onComplete: (List<Hostel>?) -> Unit)
}