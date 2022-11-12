package com.example.waterplants

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.waterplants.database.DataBaseHelper
import com.example.waterplants.databinding.ActivityMyplantsBinding

var plantChosen : Plant? = null
class MyPlantsActivity : AppCompatActivity() {
//    private var plants = listOf<Plant>(Plant("Dracena Reflexa", R.drawable.dracena_reflexa), Plant("Golden photos", R.drawable.golden_photos), Plant("Monstera monkey mask",R.drawable.monstera_mask))
    private lateinit var binding : ActivityMyplantsBinding
//    private lateinit var myplantsArrayList : ArrayList<Plant>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyplantsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Creating database
        val dbHelper = DataBaseHelper(this@MyPlantsActivity)
        val db = dbHelper.writableDatabase

        val plantList : ArrayList<Plant> = dbHelper.selectPlants(db)
        // TODO ZROBIC ZEBY TO MOGLO BYC NULL I JAK JEST NULL TO WYSWIETLIC Z ENIE MA ROSLIN W BAZIE
//        myplantsArrayList = ArrayList()
//        for (i in 0 until plants.lastIndex)
//        {
//            var singlePlant : Plant = plants.get(index = i)
//            myplantsArrayList.add(singlePlant)
//        }

        binding.listViewPlants.isClickable = true
        binding.listViewPlants.adapter = PlantAdapter(this, plantList, db)
        binding.listViewPlants.setOnItemClickListener {_, _, position, _ ->
                val intent = Intent(this@MyPlantsActivity, PlantDetailsActivity::class.java)
                plantChosen = plantList[position]
                startActivity(intent)
        }
    }

}
