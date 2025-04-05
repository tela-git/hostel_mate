package com.example.hostelmate.hostel.data.model

data class Hostel(
    val id: String,
    val name: String,
    val city: String,
    val pictures: List<String>,
    val description: String,
    val rating: Double,
    val facilities: List<String>,
    val hostelType: HostelType,
    val stayOptions: List<StayOption>,
    val locality: String,
    val coordinates: Coordinate,
    val managerName: String,
    val managerContact: String
)