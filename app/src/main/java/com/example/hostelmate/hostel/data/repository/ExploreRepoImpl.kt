package com.example.hostelmate.hostel.data.repository

import android.util.Log
import androidx.compose.ui.platform.LocalDensity
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
                    val name = task.result.getString("name")
                    val city = task.result.getString("city")
                    val description = task.result.getString("description") ?: "unavailable"
                    val hostelType = when(task.result.getString("hostelType")) {
                        "BOYS" -> HostelType.BOYS
                        "GIRLS" -> HostelType.GIRLS
                        else -> HostelType.UNAVAILABLE
                    }
                    val locality = task.result.getString("locality")
                    val managerName = task.result.getString("managerName") ?: "unavailable"
                    val managerContact = task.result.getString("managerContact") ?: "unavailable"
                    val rating = task.result.getString("rating")?.toDoubleOrNull()
                    val pictures = task.result.get("pictures") as? List<String> ?: emptyList()
                    val facilities = task.result.get("facilities") as? List<String> ?: emptyList()
                    val stayOptionMap = task.result.get("stayOptions") as? Map<String, Long>
                    val coordinates = task.result.getGeoPoint("coordinates")

                    val stayOptions = stayOptionMap?.map { (sharing, price) ->
                        StayOption(
                            sharing = sharing.toInt(),
                            cost = price.toInt()
                        )
                    }

                    if(
                        name != null && city != null && locality != null && rating != null && stayOptions != null
                    ) {
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
                                latitude = coordinates?.latitude.toString(),
                                longitude = coordinates?.longitude.toString()
                            )
                        )
                        onComplete(
                            hostel
                        )
                    } else {
                        onComplete(
                            null
                        )
                    }
                    //Log.d(HOSTEL_TAG, "RESULT DATA: $hostel")
                } else {
                    onComplete(
                        null
                    )
                    Log.d(HOSTEL_TAG, task.exception?.message ?: "")
                }
            }
    }

    override suspend fun searchHostels(key: String, onComplete: (List<Hostel>?) -> Unit) {
        firebaseFireStore.collection("hostels")
            .whereArrayContains("searchKeywords", key.lowercase())
            .get()
            .addOnSuccessListener { snapshot ->
                val hostels = snapshot.documents.mapNotNull { doc ->
                    val name = doc.getString("name")
                    val city = doc.getString("city")
                    val description = doc.getString("description") ?: "unavailable"
                    val hostelType = when (doc.getString("hostelType")) {
                        "BOYS" -> HostelType.BOYS
                        "GIRLS" -> HostelType.GIRLS
                        else -> HostelType.UNAVAILABLE
                    }
                    val locality = doc.getString("locality")
                    val managerName = doc.getString("managerName") ?: "unavailable"
                    val managerContact = doc.getString("managerContact") ?: "unavailable"
                    val rating = doc.getString("rating")?.toDoubleOrNull()
                    val pictures = doc.get("pictures") as? List<String> ?: emptyList()
                    val facilities = doc.get("facilities") as? List<String> ?: emptyList()
                    val stayOptionMap = doc.get("stayOptions") as? Map<String, Long>
                    val coordinates = doc.getGeoPoint("coordinates")

                    val stayOptions = stayOptionMap?.map { (sharing, price) ->
                        StayOption(
                            sharing = sharing.toInt(),
                            cost = price.toInt()
                        )
                    }

                    if (name != null && city != null && locality != null && rating != null && stayOptions != null) {
                        Hostel(
                            id = doc.id,
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
                                latitude = coordinates?.latitude.toString(),
                                longitude = coordinates?.longitude.toString()
                            )
                        )
                    } else null
                }
                onComplete(hostels.ifEmpty { null })
            }
            .addOnFailureListener {excep->
                Log.e(HOSTEL_TAG, "Error fetching hostels: ${excep.message}")
                onComplete(null)
            }

    }
}