package com.example.waterplants

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.example.waterplants.databinding.ActivityMainBinding
import java.io.File

private lateinit var photoFile: File
private const val  FILE_NAME =  "photo.jpg"
class MainActivity : AppCompatActivity()
{
    val REQUEST_CODE = 200
    // Main menu contents
    val menuImageId = intArrayOf(R.drawable.plants, R.drawable.watering)
    val menuItemName = listOf("My plants", "Watering")
    private lateinit var menuItemArrayList : ArrayList<MenuItem>
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        menuItemArrayList = ArrayList()

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
                1 -> startActivity(Intent(applicationContext, PlantDetailsActivity::class.java))
            }
        }

        // Camera button handling
        binding.buttonCamera.isClickable = true
        binding.buttonCamera.setOnClickListener{
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            photoFile = getPhotoFile(FILE_NAME)
            val fileProvider = FileProvider.getUriForFile(this, "com.example.fileprovider", photoFile)

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
        }
        // Every other activity
        else
        {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}
