package com.example.waterplants

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

// Chyba dobrze zrobiony
// TODO:    - W mainie przejscie po kliknieciu do activity_myplants
//          - Feedowanie list tam przez adapter jakimis roslinami z tablicy
class PlantAdapter (private val context : Activity, private val arrayList: ArrayList<Plant>) : ArrayAdapter<Plant>(context, R.layout.listitem_myplants, arrayList){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View
    {
        val inflater : LayoutInflater = LayoutInflater.from(context)
        val view : View = inflater.inflate(R.layout.listitem_myplants, null)

        val imageView : ImageView = view.findViewById(R.id.plant_image)
        val textView : TextView = view.findViewById(R.id.plant_name)

        imageView.setImageBitmap(arrayList[position].picture)
        textView.text = arrayList[position].name

        return view
    }
}