package com.example.waterplants

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class ChoosePlantAdapter(private val context : Activity, private val identifiedPlantArrayList : ArrayList<IdentifiedPlant>) : ArrayAdapter<IdentifiedPlant>(context, R.layout.listitem_chooseplant, identifiedPlantArrayList){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater : LayoutInflater = LayoutInflater.from(context)
        val view : View = inflater.inflate(R.layout.listitem_chooseplant, null)

        val plantName : TextView = view.findViewById(R.id.chooseplant_name)
        val probability : TextView = view.findViewById(R.id.chooseplant_probability)
        val plantImage : ImageView = view.findViewById(R.id.chooseplant_image)

        plantName.text = identifiedPlantArrayList[position].plant_name
        probability.text = (identifiedPlantArrayList[position].probability*100).toInt().toString() + "%"
        // Setting the image
        if (identifiedPlantArrayList[position].plant_details.wiki_image?.value != null) {
            Picasso.with(view.context)
                .load(identifiedPlantArrayList[position].plant_details.wiki_image?.value)
                .into(plantImage)
        }else if(identifiedPlantArrayList[position].similar_images?.get(index = 0)?.url != null)
        {
            Picasso.with(view.context)
                .load(identifiedPlantArrayList[position].similar_images?.get(index = 0)?.url)
                .into(plantImage)
        } else
        {
            plantImage.setImageResource(R.drawable.noimage)
        }
        return view
    }
}