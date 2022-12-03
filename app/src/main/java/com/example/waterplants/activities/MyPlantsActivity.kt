package com.example.waterplants.activities

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.waterplants.MainActivity
import com.example.waterplants.R
import com.example.waterplants.adapters.PlantAdapter
import com.example.waterplants.classes.Plant
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
                plantChosen = dbHelper.selectPlantById(db, myPlantList[position].id)
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
                plantChosen = dbHelper.selectPlantById(db, myPlantList[position].id)
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

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@MyPlantsActivity, MainActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }
}
