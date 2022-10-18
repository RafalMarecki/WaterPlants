package com.example.waterplants

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.waterplants.databinding.ActivityMyplantsBinding

class MyPlantsActivity : AppCompatActivity() {
    var plants = listOf<Plant>(Plant("Dracena Reflexa", R.drawable.dracena_reflexa), Plant("Golden photos", R.drawable.golden_photos), Plant("Monstera monkey mask",R.drawable.monstera_mask))
    private lateinit var binding : ActivityMyplantsBinding
    private lateinit var myplantsArrayList : ArrayList<Plant>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyplantsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        myplantsArrayList = ArrayList()
        for (i in 0 until plants.lastIndex)
        {
            var singlePlant : Plant = plants.get(index = i)
            myplantsArrayList.add(singlePlant)
        }

        binding.listViewPlants.adapter = PlantAdapter(this, myplantsArrayList)

    }
}