package com.example.waterplants.database

import android.content.ContentValues
import android.content.Context
import android.database.CursorWindow
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.BitmapFactory
import android.provider.BaseColumns
import android.util.Log
import androidx.core.database.getStringOrNull
import com.example.waterplants.*
import java.lang.reflect.Field
import java.time.LocalDate
import java.time.ZoneId

object MyPlantsTable : BaseColumns{
    const val TABLE_NAME = "MyPlants"
    const val TABLE_COLUMN_ID_API = "IdAPI"
    const val TABLE_COLUMN_PLANT_NAME = "PlantName"
    const val TABLE_COLUMN_PICTURE = "PlantPicture"
    const val TABLE_COLUMN_DATE_ADDED = "DateAdded"
    const val TABLE_COLUMN_DATE_WATERED = "DateWatered"
    const val TABLE_COLUMN_DAYS_WATERING = "DaysWatering"
    const val TABLE_COLUMN_DATE_FERTILIZED = "DateFertilized"
    const val TABLE_COLUMN_DAYS_FERTILIZING = "DaysFertilizing"
    const val TABLE_COLUMN_SCIENTIFIC_NAME = "ScientificName"
    const val TABLE_COLUMN_COMMON_NAMES = "CommonNames"
    const val TABLE_COLUMN_EDIBLE_PARTS = "EdibleParts"
    const val TABLE_COLUMN_PROPAGATION_METHODS = "PropagationMethods"
    const val TABLE_COLUMN_STRUCTURED_NAME_GENUS = "StructuredNameGenus"
    const val TABLE_COLUMN_STRUCTURED_NAME_SPECIES = "StrudturedNameSpecies"
    const val TABLE_COLUMN_SYNONYMS = "Synonyms"
    const val TABLE_COLUMN_TAXONOMY_CLASS = "TaxonomyClass"
    const val TABLE_COLUMN_TAXONOMY_FAMILY = "TaxonomyFamily"
    const val TABLE_COLUMN_TAXONOMY_GENUS = "TaxonomyGenus"
    const val TABLE_COLUMN_TAXONOMY_KINGDOM = "TaxonomyKingdom"
    const val TABLE_COLUMN_TAXONOMY_ORDER = "TaxonomyOrder"
    const val TABLE_COLUMN_TAXONOMY_PHYLUM = "TaxonomyPhylum"
    const val TABLE_COLUMN_WIKIDESCRIPTION_VALUE = "Description"
    const val TABLE_COLUMN_WIKIDESCRIPTION_LICENSE = "LicenseName"
    const val TABLE_COLUMN_WIKIDESCRIPTION_CITATION = "Citation"
}

object CommandSQL {
    const val CREATE_TABLE =
        "CREATE TABLE IF NOT EXISTS ${MyPlantsTable.TABLE_NAME} " +
                "(${BaseColumns._ID} INTEGER PRIMARY KEY, " +
                "${MyPlantsTable.TABLE_COLUMN_ID_API} INTEGER NOT NULL, " +
                "${MyPlantsTable.TABLE_COLUMN_PLANT_NAME} TEXT NOT NULL, " +
                "${MyPlantsTable.TABLE_COLUMN_PICTURE} BLOB NOT NULL, " +
                "${MyPlantsTable.TABLE_COLUMN_DATE_ADDED} TEXT NOT NULL, " +
                "${MyPlantsTable.TABLE_COLUMN_DATE_WATERED} TEXT NOT NULL, " +
                "${MyPlantsTable.TABLE_COLUMN_DAYS_WATERING} INTEGER NOT NULL, " +
                "${MyPlantsTable.TABLE_COLUMN_DATE_FERTILIZED} TEXT NOT NULL, " +
                "${MyPlantsTable.TABLE_COLUMN_DAYS_FERTILIZING} INTEGER NOT NULL, " +
                "${MyPlantsTable.TABLE_COLUMN_SCIENTIFIC_NAME} TEXT, " +
                "${MyPlantsTable.TABLE_COLUMN_COMMON_NAMES} TEXT, " +
                "${MyPlantsTable.TABLE_COLUMN_EDIBLE_PARTS} TEXT, " +
                "${MyPlantsTable.TABLE_COLUMN_PROPAGATION_METHODS} TEXT, " +
                "${MyPlantsTable.TABLE_COLUMN_STRUCTURED_NAME_GENUS} TEXT, " +
                "${MyPlantsTable.TABLE_COLUMN_STRUCTURED_NAME_SPECIES} TEXT, " +
                "${MyPlantsTable.TABLE_COLUMN_SYNONYMS} TEXT, " +
                "${MyPlantsTable.TABLE_COLUMN_TAXONOMY_CLASS} TEXT, " +
                "${MyPlantsTable.TABLE_COLUMN_TAXONOMY_FAMILY} TEXT, " +
                "${MyPlantsTable.TABLE_COLUMN_TAXONOMY_GENUS} TEXT, " +
                "${MyPlantsTable.TABLE_COLUMN_TAXONOMY_KINGDOM} TEXT, " +
                "${MyPlantsTable.TABLE_COLUMN_TAXONOMY_ORDER} TEXT, " +
                "${MyPlantsTable.TABLE_COLUMN_TAXONOMY_PHYLUM} TEXT, " +
                "${MyPlantsTable.TABLE_COLUMN_WIKIDESCRIPTION_VALUE} TEXT, " +
                "${MyPlantsTable.TABLE_COLUMN_WIKIDESCRIPTION_LICENSE} TEXT, " +
                "${MyPlantsTable.TABLE_COLUMN_WIKIDESCRIPTION_CITATION} TEXT)"

    const val DROP_TABLE = "DROP TABLE IF EXISTS ${MyPlantsTable.TABLE_NAME}"
    const val SELECT = "SELECT * FROM ${MyPlantsTable.TABLE_NAME}"
    const val SELECT_PLANTS_TO_WATER = "SELECT * FROM ${MyPlantsTable.TABLE_NAME} WHERE ${MyPlantsTable.TABLE_COLUMN_DATE_WATERED}<=DATE(?, '-' || ${MyPlantsTable.TABLE_COLUMN_DAYS_WATERING} || ' days')"
    const val SELECT_PLANTS_TO_FERTILIZE = "SELECT * FROM ${MyPlantsTable.TABLE_NAME} WHERE ${MyPlantsTable.TABLE_COLUMN_DATE_FERTILIZED}<=DATE(?, '-' || ${MyPlantsTable.TABLE_COLUMN_DAYS_FERTILIZING} || ' days')"
    const val SELECT_PLANTS_WHERE_ID_API = "SELECT * FROM ${MyPlantsTable.TABLE_NAME} WHERE ${MyPlantsTable.TABLE_COLUMN_ID_API}=?"
}

class DataBaseHelper(context: Context) : SQLiteOpenHelper(context, MyPlantsTable.TABLE_NAME, null, 1){
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CommandSQL.CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(CommandSQL.DROP_TABLE)
        onCreate(db)
    }

    fun setCursorSize() {
        try {
            val field : Field = CursorWindow::class.java.getDeclaredField("sCursorWindowSize")
            field.isAccessible = true
            field.set(null, 100 * 1024 * 1024)
        } catch (e: java.lang.Exception) {
            throw e
        }
    }

    fun addPlantToDB (db: SQLiteDatabase?, identifiedPlant: IdentifiedPlant) {
        val image = downscaleBitmap(BitmapFactory.decodeFile(photoFile.absolutePath), 1200)
        val image_bytes = convertBitmapToByteArray(image)
        val res = responseIdentify.get(index = 0)
        val values = ContentValues()
        values.put("${MyPlantsTable.TABLE_COLUMN_ID_API}", identifiedPlant.id)
        values.put("${MyPlantsTable.TABLE_COLUMN_PLANT_NAME}", identifiedPlant.plant_name)
        values.put("${MyPlantsTable.TABLE_COLUMN_PICTURE}", image_bytes)
        values.put("${MyPlantsTable.TABLE_COLUMN_DATE_ADDED}", res.meta_data.date)
        values.put("${MyPlantsTable.TABLE_COLUMN_DATE_WATERED}", res.meta_data.date)
        values.put("${MyPlantsTable.TABLE_COLUMN_DAYS_WATERING}", returnPlantDaysWatering(identifiedPlant))
        values.put("${MyPlantsTable.TABLE_COLUMN_DATE_FERTILIZED}", res.meta_data.date)
        values.put("${MyPlantsTable.TABLE_COLUMN_DAYS_FERTILIZING}", 30)
        values.put("${MyPlantsTable.TABLE_COLUMN_SCIENTIFIC_NAME}", identifiedPlant.plant_details.scientific_name)
        values.put("${MyPlantsTable.TABLE_COLUMN_COMMON_NAMES}", nullableListStringIntoString(identifiedPlant.plant_details.common_names))
        values.put("${MyPlantsTable.TABLE_COLUMN_EDIBLE_PARTS}", nullableListStringIntoString(identifiedPlant.plant_details.edible_parts))
        values.put("${MyPlantsTable.TABLE_COLUMN_PROPAGATION_METHODS}", nullableListStringIntoString(identifiedPlant.plant_details.propagation_methods))
        values.put("${MyPlantsTable.TABLE_COLUMN_STRUCTURED_NAME_GENUS}", identifiedPlant.plant_details.structured_name?.genus)
        values.put("${MyPlantsTable.TABLE_COLUMN_STRUCTURED_NAME_SPECIES}", identifiedPlant.plant_details.structured_name?.species)
        values.put("${MyPlantsTable.TABLE_COLUMN_SYNONYMS}", nullableListStringIntoString(identifiedPlant.plant_details.synonyms))
        values.put("${MyPlantsTable.TABLE_COLUMN_TAXONOMY_CLASS}", identifiedPlant.plant_details.taxonomy?.`class`)
        values.put("${MyPlantsTable.TABLE_COLUMN_TAXONOMY_FAMILY}", identifiedPlant.plant_details.taxonomy?.family)
        values.put("${MyPlantsTable.TABLE_COLUMN_TAXONOMY_GENUS}", identifiedPlant.plant_details.taxonomy?.genus)
        values.put("${MyPlantsTable.TABLE_COLUMN_TAXONOMY_KINGDOM}", identifiedPlant.plant_details.taxonomy?.kingdom)
        values.put("${MyPlantsTable.TABLE_COLUMN_TAXONOMY_ORDER}", identifiedPlant.plant_details.taxonomy?.order)
        values.put("${MyPlantsTable.TABLE_COLUMN_TAXONOMY_PHYLUM}", identifiedPlant.plant_details.taxonomy?.phylum)
        values.put("${MyPlantsTable.TABLE_COLUMN_WIKIDESCRIPTION_VALUE}", identifiedPlant.plant_details.wiki_description?.value)
        values.put("${MyPlantsTable.TABLE_COLUMN_WIKIDESCRIPTION_LICENSE}", identifiedPlant.plant_details.wiki_description?.license_name)
        values.put("${MyPlantsTable.TABLE_COLUMN_WIKIDESCRIPTION_CITATION}", identifiedPlant.plant_details.wiki_description?.citation)

        try {
            db?.insertOrThrow(MyPlantsTable.TABLE_NAME, null, values)
            Log.d("SAVE", "saved")
        } catch (e : SQLiteException) {
            throw e
        }
        finally {
            db?.close()
        }
    }

    fun deletePlant (db: SQLiteDatabase, plant : Plant) {
        val whereClause = "${MyPlantsTable.TABLE_COLUMN_ID_API}=?"
        db.delete(MyPlantsTable.TABLE_NAME, whereClause, arrayOf(plant.idApi.toString()))
    }

    fun selectPlants (db: SQLiteDatabase) : ArrayList<Plant> {
        var plantList : ArrayList<Plant> = ArrayList()
        db.let {
            val cursor = db.rawQuery(CommandSQL.SELECT, null)
            var start = cursor.moveToFirst()
            while(start)
            {
                try {
                    val id = cursor.getInt(cursor.getColumnIndexOrThrow(BaseColumns._ID))
                    val idApi =
                        cursor.getInt(cursor.getColumnIndexOrThrow(MyPlantsTable.TABLE_COLUMN_ID_API))
                    val plantName =
                        cursor.getString(cursor.getColumnIndexOrThrow(MyPlantsTable.TABLE_COLUMN_PLANT_NAME))
                    val picture = downscaleBitmap( convertByteArrayToBitmap(
                        cursor.getBlob(
                            cursor.getColumnIndexOrThrow(MyPlantsTable.TABLE_COLUMN_PICTURE)
                        )
                    ), 1200)
                    val dateAdded =
                        cursor.getString(cursor.getColumnIndexOrThrow(MyPlantsTable.TABLE_COLUMN_DATE_ADDED))
                    val dateWatered = cursor.getString(cursor.getColumnIndexOrThrow(MyPlantsTable.TABLE_COLUMN_DATE_WATERED))
                    val daysWatering =
                        cursor.getInt(cursor.getColumnIndexOrThrow(MyPlantsTable.TABLE_COLUMN_DAYS_WATERING))
                    val dateFertilized = cursor.getString(cursor.getColumnIndexOrThrow(MyPlantsTable.TABLE_COLUMN_DATE_FERTILIZED))
                    val daysFertilizing =
                        cursor.getInt(cursor.getColumnIndexOrThrow(MyPlantsTable.TABLE_COLUMN_DAYS_FERTILIZING))
                    val scientificName =
                        cursor.getStringOrNull(cursor.getColumnIndexOrThrow(MyPlantsTable.TABLE_COLUMN_SCIENTIFIC_NAME))
                    val commonNames =
                        cursor.getStringOrNull(cursor.getColumnIndexOrThrow(MyPlantsTable.TABLE_COLUMN_SCIENTIFIC_NAME))
                    val edibleParts =
                        cursor.getStringOrNull(cursor.getColumnIndexOrThrow(MyPlantsTable.TABLE_COLUMN_EDIBLE_PARTS))
                    val propagationMethods =
                        cursor.getStringOrNull(cursor.getColumnIndexOrThrow(MyPlantsTable.TABLE_COLUMN_PROPAGATION_METHODS))
                    val structGenus =
                        cursor.getStringOrNull(cursor.getColumnIndexOrThrow(MyPlantsTable.TABLE_COLUMN_STRUCTURED_NAME_GENUS))
                    val structSpecies =
                        cursor.getStringOrNull(cursor.getColumnIndexOrThrow(MyPlantsTable.TABLE_COLUMN_STRUCTURED_NAME_SPECIES))
                    val synonyms =
                        cursor.getStringOrNull(cursor.getColumnIndexOrThrow(MyPlantsTable.TABLE_COLUMN_SYNONYMS))
                    val taxonomyClass =
                        cursor.getStringOrNull(cursor.getColumnIndexOrThrow(MyPlantsTable.TABLE_COLUMN_TAXONOMY_CLASS))
                    val taxonomyFamily =
                        cursor.getStringOrNull(cursor.getColumnIndexOrThrow(MyPlantsTable.TABLE_COLUMN_TAXONOMY_FAMILY))
                    val taxonomyGenus =
                        cursor.getStringOrNull(cursor.getColumnIndexOrThrow(MyPlantsTable.TABLE_COLUMN_TAXONOMY_GENUS))
                    val taxonomyKingdom =
                        cursor.getStringOrNull(cursor.getColumnIndexOrThrow(MyPlantsTable.TABLE_COLUMN_TAXONOMY_KINGDOM))
                    val taxonomyOrder =
                        cursor.getStringOrNull(cursor.getColumnIndexOrThrow(MyPlantsTable.TABLE_COLUMN_TAXONOMY_ORDER))
                    val taxonomyPhylum =
                        cursor.getStringOrNull(cursor.getColumnIndexOrThrow(MyPlantsTable.TABLE_COLUMN_TAXONOMY_PHYLUM))
                    val descValue =
                        cursor.getStringOrNull(cursor.getColumnIndexOrThrow(MyPlantsTable.TABLE_COLUMN_WIKIDESCRIPTION_VALUE))
                    val descLicense =
                        cursor.getStringOrNull(cursor.getColumnIndexOrThrow(MyPlantsTable.TABLE_COLUMN_WIKIDESCRIPTION_LICENSE))
                    val descCitation =
                        cursor.getStringOrNull(cursor.getColumnIndexOrThrow(MyPlantsTable.TABLE_COLUMN_WIKIDESCRIPTION_CITATION))

                    plantList?.add(
                        Plant(
                            id,
                            idApi,
                            plantName,
                            picture,
                            LocalDate.parse(dateAdded),
                            LocalDate.parse(dateWatered),
                            daysWatering,
                            LocalDate.parse(dateFertilized),
                            daysFertilizing,
                            scientificName,
                            commonNames,
                            edibleParts,
                            propagationMethods,
                            structGenus,
                            structSpecies,
                            synonyms,
                            taxonomyClass,
                            taxonomyFamily,
                            taxonomyGenus,
                            taxonomyKingdom,
                            taxonomyOrder,
                            taxonomyPhylum,
                            descValue,
                            descLicense,
                            descCitation
                        )
                    )
                } catch (e : SQLiteException)
                {
                    Log.d("SQL EXCEPTION", "SQL EXCEPTION")
                }
                start = cursor.moveToNext()
            }
            cursor.close()
//            db.close()
        }
        return plantList
    }

    fun selectPlantByIdAPI(db: SQLiteDatabase, idAPI : Int) : Plant? {
        try {
            val cursor = db.rawQuery(CommandSQL.SELECT_PLANTS_WHERE_ID_API, arrayOf(idAPI.toString()))
            Log.d("Cursor", "$cursor")
            cursor.moveToFirst()

            val id = cursor.getInt(cursor.getColumnIndexOrThrow(BaseColumns._ID))
            val idApi =
                cursor.getInt(cursor.getColumnIndexOrThrow(MyPlantsTable.TABLE_COLUMN_ID_API))
            val plantName =
                cursor.getString(cursor.getColumnIndexOrThrow(MyPlantsTable.TABLE_COLUMN_PLANT_NAME))
            val picture = convertByteArrayToBitmap(
                cursor.getBlob(
                    cursor.getColumnIndexOrThrow(MyPlantsTable.TABLE_COLUMN_PICTURE)
                )
            )
            val dateAdded =
                cursor.getString(cursor.getColumnIndexOrThrow(MyPlantsTable.TABLE_COLUMN_DATE_ADDED))
            val dateWatered = cursor.getString(cursor.getColumnIndexOrThrow(MyPlantsTable.TABLE_COLUMN_DATE_WATERED))
            val daysWatering =
                cursor.getInt(cursor.getColumnIndexOrThrow(MyPlantsTable.TABLE_COLUMN_DAYS_WATERING))
            val dateFertilized = cursor.getString(cursor.getColumnIndexOrThrow(MyPlantsTable.TABLE_COLUMN_DATE_FERTILIZED))
            val daysFertilizing =
                cursor.getInt(cursor.getColumnIndexOrThrow(MyPlantsTable.TABLE_COLUMN_DAYS_FERTILIZING))
            val scientificName =
                cursor.getStringOrNull(cursor.getColumnIndexOrThrow(MyPlantsTable.TABLE_COLUMN_SCIENTIFIC_NAME))
            val commonNames =
                cursor.getStringOrNull(cursor.getColumnIndexOrThrow(MyPlantsTable.TABLE_COLUMN_SCIENTIFIC_NAME))
            val edibleParts =
                cursor.getStringOrNull(cursor.getColumnIndexOrThrow(MyPlantsTable.TABLE_COLUMN_EDIBLE_PARTS))
            val propagationMethods =
                cursor.getStringOrNull(cursor.getColumnIndexOrThrow(MyPlantsTable.TABLE_COLUMN_PROPAGATION_METHODS))
            val structGenus =
                cursor.getStringOrNull(cursor.getColumnIndexOrThrow(MyPlantsTable.TABLE_COLUMN_STRUCTURED_NAME_GENUS))
            val structSpecies =
                cursor.getStringOrNull(cursor.getColumnIndexOrThrow(MyPlantsTable.TABLE_COLUMN_STRUCTURED_NAME_SPECIES))
            val synonyms =
                cursor.getStringOrNull(cursor.getColumnIndexOrThrow(MyPlantsTable.TABLE_COLUMN_SYNONYMS))
            val taxonomyClass =
                cursor.getStringOrNull(cursor.getColumnIndexOrThrow(MyPlantsTable.TABLE_COLUMN_TAXONOMY_CLASS))
            val taxonomyFamily =
                cursor.getStringOrNull(cursor.getColumnIndexOrThrow(MyPlantsTable.TABLE_COLUMN_TAXONOMY_FAMILY))
            val taxonomyGenus =
                cursor.getStringOrNull(cursor.getColumnIndexOrThrow(MyPlantsTable.TABLE_COLUMN_TAXONOMY_GENUS))
            val taxonomyKingdom =
                cursor.getStringOrNull(cursor.getColumnIndexOrThrow(MyPlantsTable.TABLE_COLUMN_TAXONOMY_KINGDOM))
            val taxonomyOrder =
                cursor.getStringOrNull(cursor.getColumnIndexOrThrow(MyPlantsTable.TABLE_COLUMN_TAXONOMY_ORDER))
            val taxonomyPhylum =
                cursor.getStringOrNull(cursor.getColumnIndexOrThrow(MyPlantsTable.TABLE_COLUMN_TAXONOMY_PHYLUM))
            val descValue =
                cursor.getStringOrNull(cursor.getColumnIndexOrThrow(MyPlantsTable.TABLE_COLUMN_WIKIDESCRIPTION_VALUE))
            val descLicense =
                cursor.getStringOrNull(cursor.getColumnIndexOrThrow(MyPlantsTable.TABLE_COLUMN_WIKIDESCRIPTION_LICENSE))
            val descCitation =
                cursor.getStringOrNull(cursor.getColumnIndexOrThrow(MyPlantsTable.TABLE_COLUMN_WIKIDESCRIPTION_CITATION))

            val plant = Plant(
                    id,
                    idApi,
                    plantName,
                    picture,
                    LocalDate.parse(dateAdded),
                    LocalDate.parse(dateWatered),
                    daysWatering,
                    LocalDate.parse(dateFertilized),
                    daysFertilizing,
                    scientificName,
                    commonNames,
                    edibleParts,
                    propagationMethods,
                    structGenus,
                    structSpecies,
                    synonyms,
                    taxonomyClass,
                    taxonomyFamily,
                    taxonomyGenus,
                    taxonomyKingdom,
                    taxonomyOrder,
                    taxonomyPhylum,
                    descValue,
                    descLicense,
                    descCitation
                )
            cursor.close()
            return plant
        } catch (e : SQLiteException) {
            return null
            throw e
            Log.d("SQL EXCEPTION", "SQL EXCEPTION")
        }
    }

    fun selectPlantsToWaterOrFertilize (db: SQLiteDatabase, query : String) : ArrayList<Plant> {
        var plantList : ArrayList<Plant> = ArrayList()
        plantList.clear()
        db.let {
            val date = LocalDate.now(ZoneId.systemDefault()).toString()
            val cursor = db.rawQuery(query, arrayOf(date))
            var start = cursor.moveToFirst()
            while(start) {
                try {
                    val id = cursor.getInt(cursor.getColumnIndexOrThrow(BaseColumns._ID))
                    val idApi =
                        cursor.getInt(cursor.getColumnIndexOrThrow(MyPlantsTable.TABLE_COLUMN_ID_API))
                    val plantName =
                        cursor.getString(cursor.getColumnIndexOrThrow(MyPlantsTable.TABLE_COLUMN_PLANT_NAME))
                    val picture = convertByteArrayToBitmap(
                        cursor.getBlob(
                            cursor.getColumnIndexOrThrow(MyPlantsTable.TABLE_COLUMN_PICTURE)
                        )
                    )
                    val dateAdded =
                        cursor.getString(cursor.getColumnIndexOrThrow(MyPlantsTable.TABLE_COLUMN_DATE_ADDED))
                    val dateWatered = cursor.getString(cursor.getColumnIndexOrThrow(MyPlantsTable.TABLE_COLUMN_DATE_WATERED))
                    val daysWatering =
                        cursor.getInt(cursor.getColumnIndexOrThrow(MyPlantsTable.TABLE_COLUMN_DAYS_WATERING))
                    val dateFertilized = cursor.getString(cursor.getColumnIndexOrThrow(MyPlantsTable.TABLE_COLUMN_DATE_FERTILIZED))
                    val daysFertilizing =
                        cursor.getInt(cursor.getColumnIndexOrThrow(MyPlantsTable.TABLE_COLUMN_DAYS_FERTILIZING))
                    val scientificName =
                        cursor.getStringOrNull(cursor.getColumnIndexOrThrow(MyPlantsTable.TABLE_COLUMN_SCIENTIFIC_NAME))
                    val commonNames =
                        cursor.getStringOrNull(cursor.getColumnIndexOrThrow(MyPlantsTable.TABLE_COLUMN_SCIENTIFIC_NAME))
                    val edibleParts =
                        cursor.getStringOrNull(cursor.getColumnIndexOrThrow(MyPlantsTable.TABLE_COLUMN_EDIBLE_PARTS))
                    val propagationMethods =
                        cursor.getStringOrNull(cursor.getColumnIndexOrThrow(MyPlantsTable.TABLE_COLUMN_PROPAGATION_METHODS))
                    val structGenus =
                        cursor.getStringOrNull(cursor.getColumnIndexOrThrow(MyPlantsTable.TABLE_COLUMN_STRUCTURED_NAME_GENUS))
                    val structSpecies =
                        cursor.getStringOrNull(cursor.getColumnIndexOrThrow(MyPlantsTable.TABLE_COLUMN_STRUCTURED_NAME_SPECIES))
                    val synonyms =
                        cursor.getStringOrNull(cursor.getColumnIndexOrThrow(MyPlantsTable.TABLE_COLUMN_SYNONYMS))
                    val taxonomyClass =
                        cursor.getStringOrNull(cursor.getColumnIndexOrThrow(MyPlantsTable.TABLE_COLUMN_TAXONOMY_CLASS))
                    val taxonomyFamily =
                        cursor.getStringOrNull(cursor.getColumnIndexOrThrow(MyPlantsTable.TABLE_COLUMN_TAXONOMY_FAMILY))
                    val taxonomyGenus =
                        cursor.getStringOrNull(cursor.getColumnIndexOrThrow(MyPlantsTable.TABLE_COLUMN_TAXONOMY_GENUS))
                    val taxonomyKingdom =
                        cursor.getStringOrNull(cursor.getColumnIndexOrThrow(MyPlantsTable.TABLE_COLUMN_TAXONOMY_KINGDOM))
                    val taxonomyOrder =
                        cursor.getStringOrNull(cursor.getColumnIndexOrThrow(MyPlantsTable.TABLE_COLUMN_TAXONOMY_ORDER))
                    val taxonomyPhylum =
                        cursor.getStringOrNull(cursor.getColumnIndexOrThrow(MyPlantsTable.TABLE_COLUMN_TAXONOMY_PHYLUM))
                    val descValue =
                        cursor.getStringOrNull(cursor.getColumnIndexOrThrow(MyPlantsTable.TABLE_COLUMN_WIKIDESCRIPTION_VALUE))
                    val descLicense =
                        cursor.getStringOrNull(cursor.getColumnIndexOrThrow(MyPlantsTable.TABLE_COLUMN_WIKIDESCRIPTION_LICENSE))
                    val descCitation =
                        cursor.getStringOrNull(cursor.getColumnIndexOrThrow(MyPlantsTable.TABLE_COLUMN_WIKIDESCRIPTION_CITATION))

                    plantList?.add(
                        Plant(
                            id,
                            idApi,
                            plantName,
                            picture,
                            LocalDate.parse(dateAdded),
                            LocalDate.parse(dateWatered),
                            daysWatering,
                            LocalDate.parse(dateFertilized),
                            daysFertilizing,
                            scientificName,
                            commonNames,
                            edibleParts,
                            propagationMethods,
                            structGenus,
                            structSpecies,
                            synonyms,
                            taxonomyClass,
                            taxonomyFamily,
                            taxonomyGenus,
                            taxonomyKingdom,
                            taxonomyOrder,
                            taxonomyPhylum,
                            descValue,
                            descLicense,
                            descCitation
                        )
                    )
                } catch (e : SQLiteException) {
                    throw e
                    Log.d("SQL EXCEPTION", "SQL EXCEPTION")
                }
                start = cursor.moveToNext()
            }
            cursor.close()
            db.close()
        }
        return plantList
    }

    fun updateWatering (db: SQLiteDatabase, plant : Plant) {
        val date = LocalDate.now(ZoneId.systemDefault()).toString()
        val id = plant.idApi.toString()

        val values = ContentValues()
        values.put(MyPlantsTable.TABLE_COLUMN_DATE_WATERED, date)
        try {
            val result = db.update(
                MyPlantsTable.TABLE_NAME,
                values,
                "${MyPlantsTable.TABLE_COLUMN_ID_API}=?",
                arrayOf(id))
            Log.d("RESULT", "$result")
        } catch (e : SQLiteException) {
            throw e
        } finally {
            db.close()
        }
    }

    fun updateFertilizing (db: SQLiteDatabase, plant : Plant) {
        val date = LocalDate.now(ZoneId.systemDefault()).toString()
        val id = plant.idApi.toString()

        val values = ContentValues()
        values.put(MyPlantsTable.TABLE_COLUMN_DATE_FERTILIZED, date)
        try {
            val result = db.update(
                MyPlantsTable.TABLE_NAME,
                values,
                "${MyPlantsTable.TABLE_COLUMN_ID_API}=?",
                arrayOf(id))
            Log.d("RESULT", "$result")
        } catch (e : SQLiteException) {
            throw e
        } finally {
            db.close()
        }
    }

    fun updateReminders (db: SQLiteDatabase, idAPI : Int, timeWateringIsMonth : Boolean, numberWatering : String, timeFertilizingIsMonth : Boolean, numberFertilizing : String) {
        var daysWatering : String
        var daysFertilizing : String
        val values = ContentValues()

        if (timeWateringIsMonth){
            daysWatering = (numberWatering.toInt()*30).toString()
        } else {
            daysWatering = numberWatering
        }
        if (timeFertilizingIsMonth){
            daysFertilizing = (numberFertilizing.toInt()*30).toString()
        } else {
            daysFertilizing = numberFertilizing
        }
        values.put(MyPlantsTable.TABLE_COLUMN_DAYS_WATERING, daysWatering)
        values.put(MyPlantsTable.TABLE_COLUMN_DAYS_FERTILIZING, daysFertilizing)
        try {
            val result = db.update(
                MyPlantsTable.TABLE_NAME,
                values,
                "${MyPlantsTable.TABLE_COLUMN_ID_API}=?",
                arrayOf(idAPI.toString()))
            Log.d("RESULT", "$result")
        } catch (e : SQLiteException) {
            throw e
        } finally {
            db.close()
        }
    }
}






