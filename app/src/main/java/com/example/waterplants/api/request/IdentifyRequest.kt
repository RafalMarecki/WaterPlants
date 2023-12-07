package com.example.waterplants.api.request

data class IdentifyRequest(
    val images : List<String>,
    val plant_details : List<String>,
    val api_key : String = "gTMVIDtBQ6HsCb0sAGyDOS2BRPbDay5kUNMwwatUKRZN66M0Vm"//ZaK4HB1gaLxHmXLBBNJOGD2pEkRoZp1aF7yWdu444N9ONpzCZ5"//"9H2LHybTfiU3oDKWdxERJCuNE7Cqe3PUa82KeFpg7W9FV34MdA"
)
