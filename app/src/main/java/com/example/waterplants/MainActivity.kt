package com.example.waterplants

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.waterplants.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity()
{
    // Main menu contents
    val menuImageId = intArrayOf(R.drawable.plants, R.drawable.watering)
    val menuItemName = listOf<String>("My plants", "Watering")
    private lateinit var menuItemArrayList : ArrayList<MenuItem>
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        menuItemArrayList = ArrayList()

        for(i in menuItemName.indices) {
            val singleMenuItem = MenuItem(menuItemName[i], menuImageId[i])
            menuItemArrayList.add(singleMenuItem)
        }
        binding.listViewMain.isClickable = true
        binding.listViewMain.adapter = MenuAdapter(this, menuItemArrayList)

        binding.listViewMain.setOnItemClickListener{_, _, position, _ ->
            when(position)
            {
                0 -> startActivity(Intent(applicationContext, MyPlantsActivity::class.java))
                1 -> Toast.makeText(this@MainActivity, "Watering",   Toast.LENGTH_SHORT).show()
            }
        }

    }

}
