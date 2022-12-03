package com.example.waterplants.activities

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.waterplants.R
import com.example.waterplants.adapters.WateringAdapter
import com.example.waterplants.database.CommandSQL
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

        val plantsToWater = dbHelper.selectPlantsToWaterOrFertilize(db, CommandSQL.SELECT_PLANTS_TO_WATER)
        if (plantsToWater.size != 0) {
            binding.wateringListview.adapter = WateringAdapter(this, plantsToWater)
            binding.wateringListview.isClickable = false
        } else {
            noResultsActivityStart()
        }
    }

    fun noResultsActivityStart () {
        setContentView(R.layout.activity_noresults)
        val noPlantsText = "No plants to water today"
        val noResultsTextView : TextView = findViewById(R.id.noresults_textview)
        noResultsTextView.text = noPlantsText
    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.finish()
    }
}