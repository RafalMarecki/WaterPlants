package com.example.waterplants.api.request

data class IdentifyRequest(
    val images : List<String>,
    val plant_details : List<String>,
    val api_key : String = "9H2LHybTfiU3oDKWdxERJCuNE7Cqe3PUa82KeFpg7W9FV34MdA"
)
