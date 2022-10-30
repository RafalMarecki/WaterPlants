package com.example.waterplants

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.waterplants.databinding.ActivityPlantdetailsBinding

class PlantDetailsActivity : AppCompatActivity() {
    private lateinit var binding : ActivityPlantdetailsBinding
//    private val name : String? = intent.getStringExtra("Name")
//    private val image : Int? = intent.getIntExtra("Image", R.drawable.plants)
    private val timefertilizing = listOf<String>("Day/s","Month/s")
    private val timewatering = listOf<String>("Day/s","Month/s")
    private val numberfertilizing : MutableList<Int> = ArrayList()
    private val numberwatering : MutableList<Int> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlantdetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        for (i in 1..60)
        {
            numberwatering.add(i)
            numberfertilizing.add(i)
        }

        val adapterTimeWatering : ArrayAdapter<String> = ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, timewatering)
        val adapterTimeFertilizing : ArrayAdapter<String> = ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, timefertilizing)
        val adapterNumberWatering : ArrayAdapter<Int> = ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, numberwatering)
        val adapterNumberFertilizing : ArrayAdapter<Int> = ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, numberfertilizing)
        binding.spinnerTimewatering.adapter = adapterTimeWatering
        binding.spinnerTimefertilizing.adapter = adapterTimeFertilizing
        binding.spinnerNumberwatering.adapter = adapterNumberWatering
        binding.spinnerNumberfertilizing.adapter = adapterNumberFertilizing


    }
}