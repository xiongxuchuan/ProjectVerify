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
data class ChartData(
    val indexName: String,
    val time: String,
    val indexValue: Int,
    val unit: String,
    val extra: Int?
)

data class BarData(
    val value: Float,     // 0.0 ~ 100.0
    val index: Int        // 0~95，用于定位时间
)

class BarChartView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {
    private val barDataList = mutableListOf<BarData>()

    fun setData(data: List<BarData>) {
        barDataList.clear()
        barDataList.addAll(data)
        invalidate()
    }

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

    private val greenPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#A8FF60")
        style = Paint.Style.FILL
    }

    private val redPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.RED
        style = Paint.Style.FILL
    }

    private val timeLabels = listOf("00:00", "06:00", "12:00", "18:00", "24:00")
    private val percentLabels = listOf("100%", "50%", "0%")

    // layout paddings
    private val topPadding = 16f
    private val bottomPadding = 32f
    private val leftPadding = 20f
    private val rightPadding = 60f

    private val totalBars = 96

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val chartWidth = width - leftPadding - rightPadding
        val chartHeight = height - topPadding - bottomPadding

        // === Horizontal lines ===
        val yPos = listOf(
            topPadding,                           // 100%
            topPadding + chartHeight / 2,         // 50%
            topPadding + chartHeight              // 0%
        )
        yPos.forEachIndexed { index, y ->
            canvas.drawLine(leftPadding, y, width - rightPadding, y, solidLinePaint)
            canvas.drawText(percentLabels[index], width - rightPadding + 4f, y + textPaint.textSize / 2 - 4, textPaint)
        }

        // === Vertical lines and time labels ===
        val timeCount = timeLabels.size
        val xGap = chartWidth / (timeCount - 1)

        for (i in timeLabels.indices) {
            val x = leftPadding + i * xGap
            val paint = if (i == 2) solidLinePaint else dashedLinePaint
            canvas.drawLine(x, topPadding, x, topPadding + chartHeight, paint)
            val label = timeLabels[i]
            val textWidth = textPaint.measureText(label)
            canvas.drawText(label, x - textWidth / 2, height - 8f, textPaint)
        }

        // === Bars ===
        if (barDataList.isNotEmpty()) {
            val barWidth = chartWidth / totalBars.toFloat()

            for (bar in barDataList) {
                val index = bar.index
                val value = bar.value.coerceIn(0f, 100f)

                val barLeft = leftPadding + index * barWidth + barWidth * 0.1f
                val barRight = barLeft + barWidth * 0.8f
                val barTop = topPadding + chartHeight * (1 - value / 100f)
                val barBottom = topPadding + chartHeight

                val paint = if (value < 10f) redPaint else greenPaint
                canvas.drawRect(barLeft, barTop, barRight, barBottom, paint)
            }
        }
    }
}

