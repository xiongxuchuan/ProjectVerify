package com.example.myapplication.activity

import android.graphics.Color
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.R
import com.example.myapplication.view.SolarOffsetView

class SolarOffsetActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.solar_offset_layout)
        val sov1 = findViewById<SolarOffsetView>(R.id.sov1)
        sov1.setProgressColor(Color.parseColor("#FAE15B"))
        sov1.setProgress(1F)
        val sov2 = findViewById<SolarOffsetView>(R.id.sov2)
        sov2.setProgressColor(Color.parseColor("#578CFF"))
        sov2.setProgress(0F)
    }
}