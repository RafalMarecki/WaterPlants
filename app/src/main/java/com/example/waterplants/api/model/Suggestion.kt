package com.example.waterplants.api.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Suggestion(
    val confirmed: Boolean,
    val id: Long, // TODO: ZMIENIAM z int na long 12.11
    val plant_details: PlantDetails,
    val plant_name: String,
    val probability: Double,
    val similar_images: List<SimilarImage>?
) : Parcelable