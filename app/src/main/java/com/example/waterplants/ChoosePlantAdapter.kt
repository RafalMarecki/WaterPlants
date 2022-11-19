package com.example.waterplants

import android.app.Activity
import android.content.Intent
import android.database.sqlite.SQLiteException
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.waterplants.database.DataBaseHelper
import com.squareup.picasso.Picasso

class ChoosePlantAdapter(private val context : Activity, private val identifiedPlantArrayList : ArrayList<IdentifiedPlant>) : ArrayAdapter<IdentifiedPlant>(context, R.layout.listitem_chooseplant, identifiedPlantArrayList){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater : LayoutInflater = LayoutInflater.from(context)
        val view : View = inflater.inflate(R.layout.listitem_chooseplant, null)
        // Database
        val dbHelper = DataBaseHelper(context)
        val db = dbHelper.writableDatabase
        // Toasts
        val saveInfoToast = Toast.makeText(context, "Plant saved", Toast.LENGTH_SHORT)
        val errorToastInfo = Toast.makeText(context, "Null exception while saving", Toast.LENGTH_SHORT)
        // Contents
        val plantName : TextView = view.findViewById(R.id.chooseplant_name)
        val probability : TextView = view.findViewById(R.id.chooseplant_probability)
        val plantImage : ImageView = view.findViewById(R.id.chooseplant_image)
        val button : android.widget.Button = view.findViewById(R.id.chooseplant_button)

        plantName.text = identifiedPlantArrayList[position].plant_name
        probability.text = (identifiedPlantArrayList[position].probability*100).toInt().toString() + "%"
        // Setting the image
        if (identifiedPlantArrayList[position].plant_details.wiki_image?.value != null) {
            Picasso.with(view.context)
                .load(identifiedPlantArrayList[position].plant_details.wiki_image?.value)
                .into(plantImage)
        } else {
            plantImage.setImageResource(R.drawable.noimage)
        }

        button.setOnClickListener {
            try {
                dbHelper.addPlantToDB(db, identifiedPlantArrayList[position])
                saveInfoToast.show()
                context.startActivity(Intent(context, MyPlantsActivity::class.java))
                context.finish()
            } catch (e : SQLiteException) {
                errorToastInfo.show()
                throw e
            }
        }
        return view
    }
}