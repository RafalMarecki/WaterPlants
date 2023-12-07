package com.example.waterplants.classes

import com.example.waterplants.api.model.PlantDetails

class IdentifiedPlant(var id: Long, //TODO: ZMIENIAM Z INT NA LONG 12.11
                      var plant_details: PlantDetails,
                      var plant_name: String,
                      var probability: Double)
