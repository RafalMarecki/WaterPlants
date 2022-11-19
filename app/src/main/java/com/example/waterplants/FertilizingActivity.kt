package com.example.waterplants

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.waterplants.database.CommandSQL
import com.example.waterplants.database.DataBaseHelper
import com.example.waterplants.databinding.ActivityFertilizingBinding

class FertilizingActivity : AppCompatActivity() {
    lateinit var binding : ActivityFertilizingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFertilizingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Creating database
        val dbHelper = DataBaseHelper(this@FertilizingActivity)
        val db = dbHelper.writableDatabase

        val plantsToFertilize = dbHelper.selectPlantsToWaterOrFertilize(db, CommandSQL.SELECT_PLANTS_TO_FERTILIZE)
        if (plantsToFertilize.size != 0) {
            binding.fertilizingListview.adapter = FertilizingAdapter(this, plantsToFertilize)
            binding.fertilizingListview.isClickable = false
        } else {
            noResultsActivityStart()
        }
    }

    fun noResultsActivityStart () {
        setContentView(R.layout.activity_noresults)
        val noPlantsText = "No plants to fertilize today"
        val noResultsTextView : TextView = findViewById(R.id.noresults_textview)
        noResultsTextView.text = noPlantsText
    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.finish()
    }
}