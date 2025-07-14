package com.example.myapplication.activity

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.bean.ChartData
import com.example.myapplication.databinding.ActivityChargingChartBinding
import com.example.myapplication.view.BarChartView

class ChargingChartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChargingChartBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChargingChartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val barChart = findViewById<BarChartView>(R.id.barChartView)
        val sampleData = List(96) { index ->
            val hour = index / 4
            val minute = (index % 4) * 15
            val timeStr = String.format("%02d:%02d", hour, minute)
            val value = if (index in 20..25) (5..9).random() else (15..80).random()
            val extra = if (index in 20..26) 1 else null
            ChartData(
                indexName = "Test",
                time = timeStr,
                indexValue = value,
                unit = "%",
                extra = extra
            )
        }
        barChart.setChartData(sampleData)
    }
}