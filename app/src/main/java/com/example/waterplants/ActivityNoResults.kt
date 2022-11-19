package com.example.waterplants

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

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