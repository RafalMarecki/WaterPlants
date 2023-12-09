package com.example.waterplants.activities

import android.app.Dialog
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.waterplants.R
import com.example.waterplants.database.DataBaseHelper
import com.example.waterplants.databinding.ActivityPlantdetailsBinding

class PlantDetailsActivity : AppCompatActivity() {
    private lateinit var binding : ActivityPlantdetailsBinding
    private val timefertilizing = listOf("Day/s","Month/s")
    private val timewatering = listOf("Day/s","Month/s")
    private val numberfertilizing : MutableList<Int> = ArrayList()
    private val numberwatering : MutableList<Int> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlantdetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Database
        val dbHelper = DataBaseHelper(applicationContext)
        val db = dbHelper.writableDatabase
        // Toasts and helpfull variables
        val errorToast : Toast = Toast.makeText(applicationContext, "Error! No plant chosen.", Toast.LENGTH_SHORT)
        val toastSavedReminders = Toast.makeText(applicationContext, "Reminders saved.", Toast.LENGTH_SHORT)
        val toastErrorReminders = Toast.makeText(applicationContext, "Error while saving reminders.", Toast.LENGTH_SHORT)
        val toastDeleted = Toast.makeText(applicationContext, "Plant deleted.", Toast.LENGTH_SHORT)
        val toastErrorDeleted = Toast.makeText(applicationContext, "Error while deleting a plant.", Toast.LENGTH_SHORT)
        val daysInMonth = 30
        // Contents
        val picture : ImageView = binding.plantdetailsPicture
        val commonName : TextView = binding.commonName
        val scientificName : TextView = binding.latinName
        val date : TextView = binding.date
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
        for (i in 1..60) {
            numberwatering.add(i)
            numberfertilizing.add(i)
        }
        val adapterTimeWatering : ArrayAdapter<String> = ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, timewatering)
        val adapterTimeFertilizing : ArrayAdapter<String> = ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, timefertilizing)
        val adapterNumberWatering : ArrayAdapter<Int> = ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, numberwatering)
        val adapterNumberFertilizing : ArrayAdapter<Int> = ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, numberfertilizing)
        val spinnerTimewatering = binding.spinnerTimewatering
        val spinnerTimefertilizing = binding.spinnerTimefertilizing
        val spinnerNumberwatering = binding.spinnerNumberwatering
        val spinnerNumberfertilizing = binding.spinnerNumberfertilizing
        val saveButton = binding.plantdetailsSaveButton
        val deleteButton = binding.plantdetailsDeleteButton

        spinnerTimewatering.adapter = adapterTimeWatering
        spinnerTimefertilizing.adapter = adapterTimeFertilizing
        spinnerNumberwatering.adapter = adapterNumberWatering
        spinnerNumberfertilizing.adapter = adapterNumberFertilizing

        if (plantChosen != null) {
            // Watering
            if (plantChosen!!.daysWatering % daysInMonth == 0) {
                spinnerTimewatering.setSelection(1)
                spinnerNumberwatering.setSelection((plantChosen!!.daysWatering/daysInMonth) - 1)
            } else {
                spinnerTimewatering.setSelection(0)
                spinnerNumberwatering.setSelection(plantChosen!!.daysWatering - 1)
            }
            // Fertilizing
            if (plantChosen!!.daysFertilizing % daysInMonth == 0) {
                spinnerTimefertilizing.setSelection(1)
                spinnerNumberfertilizing.setSelection((plantChosen!!.daysFertilizing/daysInMonth) - 1)
            } else {
                spinnerTimefertilizing.setSelection(0)
                spinnerNumberfertilizing.setSelection(plantChosen!!.daysFertilizing - 1)
            }
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
            date.text = plantChosen?.dateAdded.toString()
            // Description
            if (plantChosen?.descriptionValue != null && plantChosen?.descriptionValue?.length != 0) {
                descriptionCardView.visibility = View.VISIBLE
                description.text = plantChosen?.descriptionValue
                citation.text = plantChosen?.descriptionCitation
                licenseName.text = plantChosen?.descriptionLicense
            } else {
                descriptionCardView.visibility = View.GONE
            }
            // Synonyms
            if (plantChosen?.synonyms != null && plantChosen?.synonyms?.length != 0) {
                synonymsCardView.visibility = View.VISIBLE
                synonyms.text = plantChosen?.synonyms
            } else {
                synonymsCardView.visibility = View.GONE
            }
            // Propagation methods
            if (plantChosen?.propagationMethods != null && plantChosen?.propagationMethods?.length != 0) {
                Log.d("PROPAGATIONMETHODS", "${plantChosen?.propagationMethods}")
                propagationMethodsCardView.visibility = View.VISIBLE
                propagationMethods.text = plantChosen?.propagationMethods
            } else {
                propagationMethodsCardView.visibility = View.GONE
            }
            // Edible parts
            if (plantChosen?.edibleParts != null && plantChosen?.edibleParts?.length != 0) {
                Log.d("EDIBLEPARTS", "${plantChosen?.edibleParts}")
                ediblePartsCardView.visibility = View.VISIBLE
                edibleParts.text = plantChosen?.edibleParts
            } else {
                ediblePartsCardView.visibility = View.GONE
            }
            // Structured name
            if ((plantChosen?.structuredNameGenus != null && plantChosen?.structuredNameSpecies != null) && (plantChosen?.structuredNameGenus?.get(index = 0) != '\n' && plantChosen?.structuredNameSpecies?.get(index = 0) != '\n')) {
                structuredNameCardView.visibility = View.VISIBLE
                genus.text = plantChosen?.structuredNameGenus
                species.text = plantChosen?.structuredNameSpecies
            } else {
                structuredNameCardView.visibility = View.GONE
            }
            // Taxonomy // TODO : CHANGED plantChosen?.taxonomyGenus TO plantChosen?.structuredNameGenus
            if ((plantChosen?.taxonomyClass != null && plantChosen?.taxonomyFamily != null && plantChosen?.structuredNameGenus != null && plantChosen?.taxonomyKingdom != null && plantChosen?.taxonomyPhylum != null && plantChosen?.taxonomyOrder != null) && (plantChosen?.taxonomyClass?.length != 0 && plantChosen?.taxonomyFamily?.length != 0 && plantChosen?.structuredNameGenus?.length != 0 && plantChosen?.taxonomyKingdom?.length != 0 && plantChosen?.taxonomyPhylum?.length != 0 && plantChosen?.taxonomyOrder?.length != 0)) {
                taxonomyCardView.visibility = View.VISIBLE
                taxonomyClass.text = plantChosen?.taxonomyClass
                taxonomyFamily.text = plantChosen?.taxonomyFamily
                // TODO : CHANGED plantChosen?.taxonomyGenus TO plantChosen?.structuredNameGenus
                taxonomyGenus.text = plantChosen?.structuredNameGenus
                taxonomyKingdom.text = plantChosen?.taxonomyKingdom
                taxonomyPhylum.text = plantChosen?.taxonomyPhylum
            } else {
                taxonomyCardView.visibility = View.GONE
            }
            // Saving reminders
            saveButton.setOnClickListener {
                val timeW = spinnerTimewatering.selectedItem.toString()
                val numberW = spinnerNumberwatering.selectedItem.toString()
                val timeF = spinnerTimefertilizing.selectedItem.toString()
                val numberF = spinnerNumberfertilizing.selectedItem.toString()
                var isMonthW = false
                if (timeW == timewatering[1]) {
                    isMonthW = true
                }
                var isMonthF = false
                if (timeF == timefertilizing[1]) {
                    isMonthF = true
                }
                try {
                    dbHelper.updateReminders(
                        db,
                        plantChosen!!.id,
                        isMonthW,
                        numberW,
                        isMonthF,
                        numberF
                    )
                    toastSavedReminders.show()
                } catch (e: SQLiteException) {
                    toastErrorReminders.show()
                    throw e
                }
            }
            // Deleting plant
            deleteButton.setOnClickListener {
                showDialogConfirmDelete(dbHelper, db)
//                try {
//                    dbHelper.deletePlant(db, plantChosen!!)
//                    toastDeleted.show()
//                    onBackPressed()
//                } catch (e : SQLiteException) {
//                    toastErrorDeleted.show()
//                    throw e
//                }
            }
        } else {
            onBackPressed()
            errorToast.show()
        }
    }

    private fun showDialogConfirmDelete (dbHelper: DataBaseHelper, db : SQLiteDatabase) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_delete_plant)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val buttonYes : Button = dialog.findViewById(R.id.yes_button)
        val buttonNo : Button = dialog.findViewById(R.id.no_button)

        buttonYes.setOnClickListener {
            try {
                dbHelper.deletePlant(db, plantChosen!!)
                dialog.dismiss()
                //toastDeleted.show()
                onBackPressed()
            } catch (e : SQLiteException) {
                dialog.dismiss()
                //toastErrorDeleted.show()
                throw e
            }
        }

        buttonNo.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.finish()
        plantChosen = null
    }
}