package com.example.waterplants

import com.example.waterplants.api.model.PlantDetails
import com.example.waterplants.api.model.SimilarImage

class IdentifiedPlant(var id: Int,
                      var plant_details: PlantDetails,
                      var plant_name: String,
                      var probability: Double,
                      var similar_images: List<SimilarImage>?)
{
    // NIE WIEM CZY DOBRZE
//    constructor( _suggestion : Suggestion) {
//        this.id = _suggestion.id
//        this.plant_details = _suggestion.plant_details
//        this.plant_name = _suggestion.plant_name
//        this.probability = _suggestion.probability
//        this.similar_images = _suggestion.similar_images
//    }
}