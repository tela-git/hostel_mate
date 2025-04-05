package com.example.hostelmate.hostel.data.repository

import android.util.Log
import com.example.hostelmate.hostel.data.model.Coordinate
import com.example.hostelmate.hostel.data.model.Hostel
import com.example.hostelmate.hostel.data.model.HostelType
import com.example.hostelmate.hostel.data.model.StayOption
import com.example.hostelmate.hostel.domain.repository.ExploreRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint

private const val HOSTEL_TAG = "HOSTEL_TAG"
class ExploreRepoImpl(
    private val firebaseFireStore: FirebaseFirestore
): ExploreRepository {
    override suspend fun getHostelInfo(hostelId: String, onComplete: (hostel: Hostel?) -> Unit) {
        firebaseFireStore.collection("hostels").document(hostelId)
            .get()
            .addOnCompleteListener { task->
                if(task.isSuccessful) {
                    val name = task.result.getString("name") ?: "unavailable"
                    val city = task.result.getString("city") ?: "unavailable"
                    val description = task.result.getString("description") ?: "unavailable"
                    val hostelType = when(task.result.getString("hostelType")) {
                        "BOYS" -> HostelType.BOYS
                        "GIRLS" -> HostelType.GIRLS
                        else -> HostelType.UNAVAILABLE
                    }
                    val locality = task.result.getString("locality") ?: "unavailable"
                    val managerName = task.result.getString("managerName") ?: "unavailable"
                    val managerContact = task.result.getString("managerContact") ?: "unavailable"
                    val rating = task.result.getString("rating")?.toDoubleOrNull() ?: 0.0
                    val pictures = task.result.get("pictures") as? List<String> ?: emptyList()
                    val facilities = task.result.get("facilities") as? List<String> ?: emptyList()
                    val stayOptionMap = task.result.get("stayOptions") as? Map<String, Long>
                    val coordinates = task.result.getGeoPoint("coordinates") ?: GeoPoint(0.0, 0.0)

                    val stayOptions = stayOptionMap?.map { (sharing, price) ->
                        StayOption(
                            sharing = sharing.toInt(),
                            cost = price.toInt()
                        )
                    } ?: emptyList()
                    val hostel = Hostel(
                        id = hostelId,
                        name = name,
                        city = city,
                        description = description,
                        hostelType = hostelType,
                        locality = locality,
                        managerName = managerName,
                        managerContact = managerContact,
                        rating = rating,
                        pictures = pictures,
                        facilities = facilities,
                        stayOptions = stayOptions,
                        coordinates = Coordinate(
                            latitude = coordinates.latitude.toString(),
                            longitude = coordinates.longitude.toString()
                        )
                    )

                    onComplete(
                        hostel
                    )
                    Log.d(HOSTEL_TAG, "RESULT DATA: $hostel")
                } else {
                    Log.d(HOSTEL_TAG, task.exception?.message ?: "")
                }
            }
    }
}