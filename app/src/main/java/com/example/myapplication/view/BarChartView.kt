package com.example.myapplication.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.graphics.RectF
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi
import com.example.myapplication.R
import com.example.myapplication.bean.ChartData


import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class ChartData(
    val indexName: String,
    val time: String,
    val indexValue: Int,
    val unit: String,
    val extra: Int?
)

class BarChartView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {
    private val solidLinePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.GRAY
        strokeWidth = 1.5f
        style = Paint.Style.STROKE
    }

    private val dashedLinePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.GRAY
        strokeWidth = 1.5f
        style = Paint.Style.STROKE
        pathEffect = DashPathEffect(floatArrayOf(6f, 6f), 0f)
    }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.LTGRAY
        textSize = 24f
    }

    private val timeLabels = listOf("00:00", "06:00", "12:00", "18:00", "24:00")
    private val percentLabels = listOf("100%", "50%", "0%")

    // paddings to avoid clipping
    private val topPadding = 16f
    private val bottomPadding = 32f
    private val leftPadding = 20f
    private val rightPadding = 60f

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val contentWidth = width - leftPadding - rightPadding
        val contentHeight = height - topPadding - bottomPadding

        // === Horizontal lines (100%, 50%, 0%) ===
        val yPositions = listOf(
            topPadding,                           // 100%
            topPadding + contentHeight / 2,       // 50%
            topPadding + contentHeight            // 0%
        )
        yPositions.forEachIndexed { index, y ->
            canvas.drawLine(leftPadding, y, width - rightPadding, y, solidLinePaint)
            val label = percentLabels[index]
            val textY = y + textPaint.textSize / 2 - 4
            canvas.drawText(label, width - rightPadding + 4f, textY, textPaint)
        }

        // === Vertical lines (00:00 - 24:00, 5 lines) ===
        val columnCount = timeLabels.size
        val xInterval = contentWidth / (columnCount - 1)

        for (i in timeLabels.indices) {
            val x = leftPadding + i * xInterval
            val paint = if (i == 2) solidLinePaint else dashedLinePaint  // middle is solid
            canvas.drawLine(x, topPadding, x, topPadding + contentHeight, paint)

            // Draw time label
            val label = timeLabels[i]
            val textWidth = textPaint.measureText(label)
            canvas.drawText(label, x - textWidth / 2, height - 8f, textPaint)
        }
    }
}

