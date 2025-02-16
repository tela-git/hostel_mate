package com.example.hostelmate.hostel.data.model

data class Hostel(
    val id: String,
    val name: String,
    val city: String,
    val photos: List<String>,
    val description: String,
    val rating: Double
)
