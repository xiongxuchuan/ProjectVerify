package com.example.myapplication.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.myapplication.bean.ChartData

class BarChartView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {
    private val chartDataList = mutableListOf<ChartData>()

    fun setChartData(data: List<ChartData>) {
        chartDataList.clear()
        chartDataList.addAll(data)
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

    private val bgGreenPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#2033FF33") // 半透明绿色背景
        style = Paint.Style.FILL
    }

    private val flashIconPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.YELLOW
        textSize = 28f
    }

    private val timeLabels = listOf("00:00", "06:00", "12:00", "18:00", "24:00")
    private val percentLabels = listOf("100%", "50%", "0%")

    private val topPadding = 16f
    private val bottomPadding = 32f
    private val leftPadding = 20f
    private val rightPadding = 60f

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val totalBars = chartDataList.size.takeIf { it > 0 } ?: return
        val chartWidth = width - leftPadding - rightPadding
        val chartHeight = height - topPadding - bottomPadding
        val barWidth = chartWidth / totalBars.toFloat()

        // === 绘制横线 ===
        val yPos = listOf(
            topPadding, topPadding + chartHeight / 2, topPadding + chartHeight
        )
        yPos.forEachIndexed { index, y ->
            canvas.drawLine(leftPadding, y, width - rightPadding, y, solidLinePaint)
            canvas.drawText(percentLabels[index], width - rightPadding + 4f, y + textPaint.textSize / 2 - 4, textPaint)
        }

        // === 绘制纵线和时间文字 ===
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

        // === 画背景绿色区域（extra == 1） ===
        chartDataList.forEachIndexed { i, data ->
            if (data.extra == 1) {
                val left = leftPadding + i * barWidth
                val right = left + barWidth
                canvas.drawRect(left, topPadding, right, topPadding + chartHeight, bgGreenPaint)
            }
        }

        // === 连续5个 extra == 1 显示闪电图标 ===
        var i = 0
        while (i <= chartDataList.size - 5) {
            if ((0 until 5).all { chartDataList[i + it].extra == 1 }) {
                val centerIndex = i + 2
                val x = leftPadding + centerIndex * barWidth + barWidth / 2
                canvas.drawText("⚡", x - 12f, topPadding + 24f, flashIconPaint)
                i += 5
            } else {
                i++
            }
        }

        // === 绘制柱状图 ===
        chartDataList.forEachIndexed { i, data ->
            val value = data.indexValue.coerceIn(0, 100)
            val left = leftPadding + i * barWidth + barWidth * 0.1f
            val right = left + barWidth * 0.8f
            val top = topPadding + chartHeight * (1 - value / 100f)
            val bottom = topPadding + chartHeight

            val paint = if (value < 10) redPaint else greenPaint
            canvas.drawRect(left, top, right, bottom, paint)
        }
    }
}

