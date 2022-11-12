package com.example.waterplants

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.waterplants.database.DataBaseHelper
import com.example.waterplants.databinding.ActivityChooseplantBinding
import java.io.ByteArrayOutputStream

// UWAGA ZMIENNA GLOBALNA
var chosenPlant : IdentifiedPlant? = null

class ChoosePlantActivity : AppCompatActivity() {
    private lateinit var binding : ActivityChooseplantBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseplantBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // DATABASE
        val dbHelper = DataBaseHelper(applicationContext)
        val db = dbHelper.writableDatabase
        val saveInfoToast = Toast.makeText(applicationContext, "Plant saved", Toast.LENGTH_SHORT)
        val errorToastInfo = Toast.makeText(applicationContext, "Null exception while saving", Toast.LENGTH_SHORT)

        // TODO CZYSZCZENIE ZMIENNEJ GLOBALNEJ
        chosenPlant = null

//        val button = findViewById(R.id.chooseplant_button) as Button
//        button.setOnClickListener {
//            val name = chooseplant_name.text.toString()
//            val ind = identifiedPlantArrayList.indices.find { identifiedPlantArrayList[it].plant_name == name}
//            if (ind != null) {
//                dbHelper.addPlantToDB(db, identifiedPlantArrayList.get(index = ind))
//                saveInfoToast.show()
//            } else {
//                errorToastInfo.show()
//            }
//
//        }

        binding.listViewChooseplant.isClickable = true
        binding.listViewChooseplant.adapter = ChoosePlantAdapter(this, identifiedPlantArrayList)
        binding.listViewChooseplant.setOnItemClickListener {_, _, position, _ ->
            chosenPlant = identifiedPlantArrayList.get(index = position)
            startActivity(Intent(this@ChoosePlantActivity, ChoosePlantDetailsActivity::class.java))
        }
    }
}
 fun convertBitmapToByteArray (bitmap : Bitmap) : ByteArray {
     val baos = ByteArrayOutputStream()
     bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
     return baos.toByteArray()
 }

fun convertByteArrayToBitmap (byteArray: ByteArray) : Bitmap {
    return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
}

fun returnPlantDaysWatering (identifiedPlant: IdentifiedPlant) : Int {
    val watering = identifiedPlant.plant_details.watering
    if (watering != null) {
        val min = identifiedPlant.plant_details.watering?.min
        val max = identifiedPlant.plant_details.watering?.max
        if (max == 1) {
            return 14
        } else if (min == 3) {
            return 3
        } else if (min == 2 && max == 3) {
            return 10
        } else if (min == 2 && max == 2) {
            return 7
        } else if (min == 1 && max == 2) {
            return 5
        } else if (min == 1 && max == 3) {
            return 9
        }
    }
    return 7 // Default value
}
