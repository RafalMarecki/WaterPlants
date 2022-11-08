package com.example.waterplants

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.waterplants.databinding.ActivityChooseplantBinding

// UWAGA ZMIENNA GLOBALNA
lateinit var chosenPlant : IdentifiedPlant

class ChoosePlantActivity : AppCompatActivity() {
    private lateinit var binding : ActivityChooseplantBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseplantBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.listViewChooseplant.isClickable = true
        binding.listViewChooseplant.adapter = ChoosePlantAdapter(this, identifiedPlantArrayList)
        binding.listViewChooseplant.setOnItemClickListener {_, _, position, _ ->
            chosenPlant = identifiedPlantArrayList.get(index = position)
            startActivity(Intent(this@ChoosePlantActivity, ChoosePlantDetailsActivity::class.java))
        }

    }
}