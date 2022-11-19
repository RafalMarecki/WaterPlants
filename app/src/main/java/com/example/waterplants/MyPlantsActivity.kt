package com.example.waterplants

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.waterplants.database.DataBaseHelper
import com.example.waterplants.databinding.ActivityMyplantsBinding

var plantChosen : Plant? = null
class MyPlantsActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMyplantsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyplantsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Database
        val dbHelper = DataBaseHelper(this@MyPlantsActivity)
        val db = dbHelper.writableDatabase
        val myPlantList : ArrayList<Plant> = dbHelper.selectPlants(db)

        if (myPlantList.size != 0) {
            binding.listViewPlants.isClickable = true
            binding.listViewPlants.adapter = PlantAdapter(this, myPlantList)
            binding.listViewPlants.setOnItemClickListener { _, _, position, _ ->
                val intent = Intent(this@MyPlantsActivity, PlantDetailsActivity::class.java)
                plantChosen = dbHelper.selectPlantByIdAPI(db, myPlantList[position].idApi)
                startActivity(intent)
            }
        } else {
            noResultsActivityStart()
        }
    }

    override fun onRestart() {
        super.onRestart()
        binding = ActivityMyplantsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Database
        val dbHelper = DataBaseHelper(this@MyPlantsActivity)
        val db = dbHelper.writableDatabase
        val myPlantList : ArrayList<Plant> = dbHelper.selectPlants(db)

        if (myPlantList.size != 0) {
            binding.listViewPlants.isClickable = true
            binding.listViewPlants.adapter = PlantAdapter(this, myPlantList)
            binding.listViewPlants.setOnItemClickListener { _, _, position, _ ->
                val intent = Intent(this@MyPlantsActivity, PlantDetailsActivity::class.java)
                plantChosen = dbHelper.selectPlantByIdAPI(db, myPlantList[position].idApi)
                startActivity(intent)
            }
        } else {
            noResultsActivityStart()
        }
    }

    fun noResultsActivityStart () {
        setContentView(R.layout.activity_noresults)
        val noPlantsText = "No plants added yet"
        val noResultsTextView : TextView = findViewById(R.id.noresults_textview)
        noResultsTextView.text = noPlantsText
    }

}
