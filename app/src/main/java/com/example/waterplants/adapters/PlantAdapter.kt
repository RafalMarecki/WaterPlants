package com.example.waterplants.adapters

import android.app.Activity
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.waterplants.R
import com.example.waterplants.classes.Plant
import com.example.waterplants.utils.downscaleBitmap

class PlantAdapter (private val context : Activity, private val arrayList: ArrayList<Plant>) : ArrayAdapter<Plant>(context,
    R.layout.listitem_myplants, arrayList){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View
    {
        val inflater : LayoutInflater = LayoutInflater.from(context)
        val view : View = inflater.inflate(R.layout.listitem_myplants, null)

        val imageView : ImageView = view.findViewById(R.id.plant_image)
        val textView : TextView = view.findViewById(R.id.plant_name)
        val bitmap : Bitmap = downscaleBitmap(arrayList[position].picture, 400)
        imageView.setImageBitmap(bitmap)
        textView.text = arrayList[position].name

        return view
    }
}