package com.example.waterplants

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.waterplants.databinding.ActivityChooseplantdetailsBinding
import com.squareup.picasso.Picasso

class ChoosePlantDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChooseplantdetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseplantdetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val picture : ImageView = binding.chooseplantdetailsPicture
        val commonName : TextView = binding.chooseplantdetailsCommonName
        val scientificName : TextView = binding.chooseplantdetailsScientificname
        val probability : TextView = binding.chooseplantdetailsProbability
        val description : TextView = binding.chooseplantdetailsDescription
        val citation : TextView = binding.chooseplantdetailsCitation
        val licenseName : TextView = binding.chooseplantdetailsLicenseName
        val synonyms : TextView = binding.chooseplantdetailsSynonyms
        val propagationMethods : TextView = binding.chooseplantdetailsPropagationmethods
        val edibleParts : TextView = binding.chooseplantdetailsEdibleparts
        // Structured name
        val genus : TextView = binding.chooseplantdetailsStructurednameGenus
        val species : TextView = binding.chooseplantdetailsStructurednameSpecies
        // Taxonomy
        val taxonomyClass : TextView = binding.chooseplantdetailsTaxonomyClass
        val taxonomyFamily : TextView = binding.chooseplantdetailsTaxonomyFamily
        val taxonomyGenus : TextView = binding.chooseplantdetailsTaxonomyGenus
        val taxonomyKingdom : TextView = binding.chooseplantdetailsTaxonomyKingdom
        val taxonomyPhylum : TextView = binding.chooseplantdetailsTaxonomyPhylum

        // Picture
        if (chosenPlant.plant_details.wiki_image?.value != null) {
            Picasso.with(applicationContext)
                .load(chosenPlant.plant_details.wiki_image?.value)
                .into(picture)
        } else if (chosenPlant.similar_images?.get(index = 0)?.url != null)
        {
            Picasso.with(applicationContext)
                .load(chosenPlant.similar_images?.get(index = 0)?.url)
                .into(picture)
        } else
        {
            picture.setImageResource(R.drawable.noimage)
        }
        // Plant details
        commonName.text = chosenPlant.plant_name
        scientificName.text = chosenPlant.plant_details.scientific_name
        probability.text = (chosenPlant.probability*100).toInt().toString() + "%"
        description.text = chosenPlant.plant_details.wiki_description?.value
        citation.text = chosenPlant.plant_details.wiki_description?.citation
        licenseName.text = chosenPlant.plant_details.wiki_description?.license_name
        // `/n` separated
        synonyms.text = nullableListStringIntoString(chosenPlant.plant_details.synonyms)
        propagationMethods.text = nullableListStringIntoString(chosenPlant.plant_details.propagation_methods)
        edibleParts.text = nullableListStringIntoString(chosenPlant.plant_details.edible_parts)
        // Structured name
        genus.text = chosenPlant.plant_details.structured_name?.genus
        species.text = chosenPlant.plant_details.structured_name?.species
        // Taxonomy
        taxonomyClass.text = chosenPlant.plant_details.taxonomy?.`class`
        taxonomyFamily.text = chosenPlant.plant_details.taxonomy?.family
        taxonomyGenus.text = chosenPlant.plant_details.taxonomy?.genus
        taxonomyKingdom.text = chosenPlant.plant_details.taxonomy?.kingdom
        taxonomyPhylum.text = chosenPlant.plant_details.taxonomy?.phylum
    }

    fun nullableListStringIntoString (list : List<String>?) : String {
        var text = ""
        list?.let {
            for (i in it.indices)
            {
                if (i == 0)
                {
                    text += it.get(index = i)
                } else
                {
                    text += "\n" + it.get(index = i)
                }
            }
        }
        return text
    }
}