package com.example.waterplants

import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

//import android.os.Parcel
//import android.os.Parcelable

//class Plant (var name: String, var imageId: Int ) /*: Parcelable*/ {
//}
@Parcelize
class Plant (var id : Int,
             var idApi : Int,
             var name : String,
             var picture : Bitmap,
             var dateAdded : Date,
             var dateWatered : Date,
             var daysWatering : Int,
             var dateFertilized : Date,
             var daysFertilizing : Int,
             var scientificName : String?,
             var commonNames : String?,
             var edibleParts : String?,
             var propagationMethods : String?,
             var structuredNameGenus : String?,
             var structuredNameSpecies : String?,
             var synonyms : String?,
             var taxonomyClass : String?,
             var taxonomyFamily : String?,
             var taxonomyGenus : String?,
             var taxonomyKingdom : String?,
             var taxonomyOrder : String?,
             var taxonomyPhylum : String?,
             val descriptionValue : String?,
             val descriptionLicense : String?,
             val descriptionCitation : String?
) : Parcelable
{
//    constructor (parcel: Parcel
//
//    override fun describeContents(): Int {
//        TODO("Not yet implemented")
//    }
//
//    override fun writeToParcel(dest: Parcel?, flags: Int) {
//        TODO("Not yet implemented")
//    }
}
