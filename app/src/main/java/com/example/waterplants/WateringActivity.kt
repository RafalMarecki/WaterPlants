package com.example.waterplants

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.waterplants.database.DataBaseHelper
import com.example.waterplants.databinding.ActivityWateringBinding

class WateringActivity : AppCompatActivity() {
    lateinit var binding: ActivityWateringBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWateringBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Creating database
        val dbHelper = DataBaseHelper(this@WateringActivity)
        val db = dbHelper.writableDatabase

        val plantsToWater = dbHelper.selectPlantsToWater(db)

        binding.wateringListview.adapter = WateringAdapter(this, plantsToWater)
        binding.wateringListview.isClickable = false
    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.finish()
    }
}