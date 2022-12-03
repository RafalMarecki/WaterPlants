package com.example.waterplants.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.waterplants.R

class ActivityNoResults : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_noresults)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.finish()
    }

}