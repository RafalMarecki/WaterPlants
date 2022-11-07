package com.example.waterplants

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.waterplants.databinding.ActivityChooseplantBinding

class ChoosePlantActivity : AppCompatActivity() {
    private lateinit var binding : ActivityChooseplantBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseplantBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.listViewChooseplant.isClickable = true
        binding.listViewChooseplant.adapter = ChoosePlantAdapter(this, identifiedPlantArrayList)

    }
}