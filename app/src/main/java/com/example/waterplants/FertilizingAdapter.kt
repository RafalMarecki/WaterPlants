package com.example.waterplants

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.waterplants.database.DataBaseHelper

class FertilizingAdapter (private val context : Activity, private val arrayList: ArrayList<Plant>) : ArrayAdapter<Plant>(context, R.layout.listitem_fertilizing, arrayList){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.listitem_fertilizing, null)

        val dbHelper = DataBaseHelper(context)
        val db = dbHelper.writableDatabase

        val plant = arrayList[position]
        val imageView: ImageView = view.findViewById(R.id.fertilizing_image)
        val nameText: TextView = view.findViewById(R.id.fertilizing_name)
        val button: Button = view.findViewById(R.id.fertilizing_button)

        imageView.setImageBitmap(plant.picture)
        nameText.text = plant.name

        button.setOnClickListener {
            dbHelper.updateFertilizing(db, plant)
            Toast.makeText(
                context,
                "${plant.dateAdded}, ${plant.dateFertilized}, ${plant.daysFertilizing}",
                Toast.LENGTH_SHORT
            ).show()
            button.text = "DONE"
            button.isClickable = false
            button.setBackgroundResource(R.drawable.shape_watered_button)
        }
        return view
    }
}