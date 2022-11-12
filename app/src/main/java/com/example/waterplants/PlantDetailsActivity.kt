package com.example.waterplants

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.waterplants.databinding.ActivityPlantdetailsBinding

class PlantDetailsActivity : AppCompatActivity() {
    private lateinit var binding : ActivityPlantdetailsBinding
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

        val picture : ImageView = binding.plantdetailsPicture
        val commonName : TextView = binding.commonName
        val scientificName : TextView = binding.latinName
        val descriptionCardView : CardView = binding.plantdetailsDescriptionCardview
        val description : TextView = binding.plantdetailsDescription
        val citation : TextView = binding.plantdetailsCitation
        val licenseName : TextView = binding.plantdetailsLicenseName
        val synonymsCardView : CardView = binding.plantdetailsSynonymsCardview
        val synonyms : TextView = binding.plantdetailsSynonyms
        val propagationMethodsCardView : CardView = binding.plantdetailsPropagationmethodsCardview
        val propagationMethods : TextView = binding.plantdetailsPropagationmethods
        val ediblePartsCardView : CardView = binding.plantdetailsEdiblepartsCardview
        val edibleParts : TextView = binding.plantdetailsEdibleparts
        val structuredNameCardView : CardView = binding.plantdetailsStructurednameCardview
        val genus : TextView = binding.plantdetailsStructurednameGenus
        val species : TextView = binding.plantdetailsStructurednameSpecies
        val taxonomyCardView : CardView = binding.plantdetailsTaxonomyCardview
        val taxonomyClass : TextView = binding.plantdetailsTaxonomyClass
        val taxonomyFamily : TextView = binding.plantdetailsTaxonomyFamily
        val taxonomyGenus : TextView = binding.plantdetailsTaxonomyGenus
        val taxonomyKingdom : TextView = binding.plantdetailsTaxonomyKingdom
        val taxonomyPhylum : TextView = binding.plantdetailsTaxonomyPhylum
        val adapterTimeWatering : ArrayAdapter<String> = ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, timewatering)
        val adapterTimeFertilizing : ArrayAdapter<String> = ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, timefertilizing)
        val adapterNumberWatering : ArrayAdapter<Int> = ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, numberwatering)
        val adapterNumberFertilizing : ArrayAdapter<Int> = ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, numberfertilizing)
        binding.spinnerTimewatering.adapter = adapterTimeWatering
        binding.spinnerTimefertilizing.adapter = adapterTimeFertilizing
        binding.spinnerNumberwatering.adapter = adapterNumberWatering
        binding.spinnerNumberfertilizing.adapter = adapterNumberFertilizing

        if (plantChosen != null) {

            // Picture
            if (plantChosen?.picture != null) {
                picture.setImageBitmap(plantChosen?.picture)
            }
            else {
                picture.setImageResource(R.drawable.noimage)
            }

            // Plant details
            commonName.text = plantChosen?.name
            scientificName.text = plantChosen?.scientificName
            // Description
            if (plantChosen?.descriptionValue != null) {
                descriptionCardView.visibility = View.VISIBLE
                description.text = plantChosen?.descriptionValue
                citation.text = plantChosen?.descriptionCitation
                licenseName.text = plantChosen?.descriptionLicense
            } else {
                descriptionCardView.visibility = View.GONE
            }
            // Synonyms
            if (plantChosen?.synonyms != null) {
                synonymsCardView.visibility = View.VISIBLE
                synonyms.text = plantChosen?.synonyms
            } else {
                synonymsCardView.visibility = View.GONE
            }
            // Propagation methods
            if (plantChosen?.propagationMethods != null) {
                propagationMethodsCardView.visibility = View.VISIBLE
                propagationMethods.text = plantChosen?.propagationMethods
            } else {
                propagationMethodsCardView.visibility = View.GONE
            }
            // Edible parts
            if (plantChosen?.edibleParts != null) {
                ediblePartsCardView.visibility = View.VISIBLE
                edibleParts.text = plantChosen?.edibleParts
            } else {
                ediblePartsCardView.visibility = View.GONE
            }
            // Structured name
            if (plantChosen?.structuredNameGenus != null && plantChosen?.structuredNameSpecies != null) {
                structuredNameCardView.visibility = View.VISIBLE
                genus.text = plantChosen?.structuredNameGenus
                species.text = plantChosen?.structuredNameSpecies
            } else {
                structuredNameCardView.visibility = View.GONE
            }
            // Taxonomy
            if (plantChosen?.taxonomyClass != null && plantChosen?.taxonomyFamily != null && plantChosen?.taxonomyGenus != null && plantChosen?.taxonomyKingdom != null && plantChosen?.taxonomyPhylum != null && plantChosen?.taxonomyOrder != null) {
                taxonomyCardView.visibility = View.VISIBLE
                taxonomyClass.text = plantChosen?.taxonomyClass
                taxonomyFamily.text = plantChosen?.taxonomyFamily
                taxonomyGenus.text = plantChosen?.taxonomyGenus
                taxonomyKingdom.text = plantChosen?.taxonomyKingdom
                taxonomyPhylum.text = plantChosen?.taxonomyPhylum
            } else {
                taxonomyCardView.visibility = View.GONE
            }
        }
    }
}