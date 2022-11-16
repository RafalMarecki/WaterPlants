package com.example.waterplants

import android.app.Activity
import android.content.Intent
import android.database.CursorWindow
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import com.example.waterplants.api.PlantAPI
import com.example.waterplants.api.createPlantAPIClient
import com.example.waterplants.api.model.ResponseIdentify
import com.example.waterplants.api.model.Suggestion
import com.example.waterplants.api.request.IdentifyRequest
import com.example.waterplants.databinding.ActivityMainBinding
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.File
import java.lang.reflect.Field

// Photo related variables
lateinit var photoFile: File
private const val  FILE_NAME =  "photo.jpg"
private lateinit var takePictureIntent: Intent
private lateinit var fileProvider: Uri
private const val REQUEST_CODE = 200

// GLOBAL VARIABLE RESULT OF API
public var identifiedPlantArrayList : ArrayList<IdentifiedPlant> = ArrayList()
public var  responseIdentify : ArrayList<ResponseIdentify> = ArrayList()
var plantsToWater : ArrayList<Plant> = ArrayList()

class MainActivity : AppCompatActivity()
{
    // Main menu contents
    val menuImageId = intArrayOf(R.drawable.plants, R.drawable.watering)
    val menuItemName = listOf("My plants", "Watering")
    private lateinit var menuItemArrayList : ArrayList<MenuItem>
    // Binding
    private lateinit var binding : ActivityMainBinding
    // API HANDLING
    val plantDetails : MutableList<String> = mutableListOf("common_names", "edible_parts", "propagation_methods", "scientific_name", "structured_name", "synonyms", "taxonomy", "url", "watering", "wiki_description", "wiki_image")
    val plantapi : PlantAPI by lazy { createPlantAPIClient() }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Creating database
//        val dbHelper = DataBaseHelper(applicationContext)
//        val db = dbHelper.writableDatabase

        try {
            val field : Field = CursorWindow::class.java.getDeclaredField("sCursorWindowSize")
            field.isAccessible = true
            field.set(null, 100 * 1024 * 1024)
        } catch (e: java.lang.Exception)
        {
            println("Essa")
        }

//        val plantsToWater = dbHelper.selectPlantsToWater(db)
//        Log.d("dupa", "$plantsToWater")
//        for (i in plantsToWater.indices) {
//            Log.d("dateWatered", "${plantsToWater[i].dateWatered.toString()}")
//            Log.d("daysWatering", "${plantsToWater[i].daysWatering}")
//        }
//        Toast.makeText(applicationContext,"${plantsToWater.get(index = 0).name}", Toast.LENGTH_SHORT).show()
        menuItemArrayList = ArrayList()

        // TODO CZYSZCZENIE ZMIENNEJ GLOBALNEJ
        responseIdentify.clear()
        identifiedPlantArrayList.clear()

        // Feeding the main list view with content
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
                1 -> startActivity(Intent(applicationContext, WateringActivity::class.java))
            }
        }

        // Camera button handling
        binding.buttonCamera.isClickable = true
        binding.buttonCamera.setOnClickListener{
            takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            // Getting the photo file and photo URI
            photoFile = getPhotoFile(FILE_NAME)
            fileProvider = FileProvider.getUriForFile(this, "com.example.fileprovider", photoFile)


            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)
            if (takePictureIntent.resolveActivity(this.packageManager) != null)
            {
                startActivityForResult(takePictureIntent, REQUEST_CODE)
            }
            else
            {
                Toast.makeText(this, "Unable to open camera", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Encoding image file to string Base64
    fun encodeImageBase64(photoFile: File): String {
        return Base64.encodeToString(photoFile.readBytes(), Base64.NO_WRAP)
    }

    // Getting temporary file in image directory
    private fun getPhotoFile(fileName: String) : File {
        val directory = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName, ".jpg", directory)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // Activity of taking a picture
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK)
        {
            val image = BitmapFactory.decodeFile(photoFile.absolutePath)
            binding.pictureTest.setImageBitmap(image)
            Toast.makeText(this@MainActivity, "Processing identification...", Toast.LENGTH_SHORT).show()
            // Sending image to API, assigning results to global ArrayList of identified plants and starting ChoosePlantActivity
            lifecycleScope.launch {
                var res = sendImageToAPI(encodeImageBase64(photoFile)).await()
                responseIdentify.add(res)
                for (i in res.suggestions.indices)
                {
                    var singleSuggestion : Suggestion = res.suggestions.get(index = i)
                    var identifiedPlant = IdentifiedPlant(singleSuggestion.id, singleSuggestion.plant_details, singleSuggestion.plant_name, singleSuggestion.probability)
                    identifiedPlantArrayList.add(identifiedPlant)
                }
                startActivity(Intent(applicationContext, ChoosePlantActivity::class.java))
            }
        }
        // Every other activity
        else
        {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    // Sending image to api and returning a response
    private fun sendImageToAPI(encodedImageBase64: String)=
        lifecycleScope.async {
            val response = plantapi.identify(IdentifyRequest(images = listOf(encodedImageBase64), plant_details =  plantDetails))
            Log.d("MainActivity", "response: $response")
            return@async response
        }
}
