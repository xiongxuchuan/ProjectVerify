package com.example.myapplication.activity

import com.example.myapplication.view.CircularProgressView
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R

class OuterHistoryActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_outer_history)
        val progressView = findViewById<CircularProgressView>(R.id.cpv)
        progressView.setProgress(98F)
    }
}