package com.example.myapplication.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.example.myapplication.R
import com.example.myapplication.bean.ChartData

class BatteryMeasurementView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {
    private val flashBitmap: Bitmap by lazy {
        BitmapFactory.decodeResource(resources, R.drawable.ic_lightning)
    }

    private val chartDataList = mutableListOf<ChartData>()

    fun setChartData(data: List<ChartData>) {
        chartDataList.clear()
        chartDataList.addAll(data)
        invalidate()
    }

    // 水平方向横线Paint
    private val solidLinePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = context.getColor(R.color.color_4A4A4E)
        strokeWidth = 1f
        style = Paint.Style.STROKE
    }

    private val dashedLinePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.GRAY
        strokeWidth = 1.5f
        style = Paint.Style.STROKE
        pathEffect = DashPathEffect(floatArrayOf(6f, 6f), 0f)
    }

    // 水平方向文本Paint
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#66FFFFFF")
        textSize = 26f
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
        color = Color.parseColor("#3386ED52") // 半透明绿色背景
        style = Paint.Style.FILL
    }

    private val timeLabels = listOf("00:00", "06:00", "12:00", "18:00", "24:00")
    private val percentLabels = listOf("100%", "50%", "0%")

    private val topPadding = 16f
    private val bottomPadding = 32f
    private val leftPadding = 20f
    private val rightPadding = 100f

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // 柱状图总数（例如 96）
        val totalBars = chartDataList.size.takeIf { it > 0 } ?: return
        // 实际用于绘图的宽度（= view.width - leftPadding - rightPadding）
        val chartWidth = width - leftPadding - rightPadding
        val chartHeight = height - topPadding - bottomPadding - 50f
        // 每根柱子的完整宽度（包含间距）
        val barWidth = chartWidth / totalBars.toFloat()

        // 绘制横线和进度文字
        val yPos = listOf(
            topPadding, topPadding + (chartHeight ) / 2, topPadding + (chartHeight )
        )
        yPos.forEachIndexed { index, y ->
            canvas.drawLine(leftPadding, y, width - 80f, y, solidLinePaint)
            canvas.drawText(percentLabels[index], width - 72f, y + textPaint.textSize / 2 - 4, textPaint)
        }

        // 绘制纵线和时间文字
        val timeCount = timeLabels.size
        val xGap = chartWidth / (timeCount - 1)
        for (i in timeLabels.indices) {
            val x = leftPadding + i * xGap
            val paint = if (i == 2) solidLinePaint else dashedLinePaint
            canvas.drawLine(x, topPadding, x, height.toFloat() , paint)
            val label = timeLabels[i]
            if (i != timeLabels.size - 1) {
                canvas.drawText(label, x + 4f, height - 8f, textPaint)
            }
        }

        // 画背景绿色区域（extra == 1）
        chartDataList.forEachIndexed { i, data ->
            if (data.extra == 1) {
                val left = leftPadding + i * barWidth
                val right = left + barWidth
                canvas.drawRect(left, topPadding, right, topPadding + chartHeight + 40f, bgGreenPaint)
            }
        }

        // 显示闪电图标
        var i = 0
        while (i <= chartDataList.size - 5) {
            // 创建一个 整数范围 [0, 1, 2, 3, 4]（从 0 开始，不包括 5）半开区间
            // all 对范围中的每个元素（0, 1, 2, 3, 4）执行给定的 Lambda 表达式，检查是否全部满足条件，如果所有元素都使Lambda返回true，则 all 返回 rue；否则返回 false
            if ((0 until 5).all { chartDataList.getOrNull(i + it)?.extra == 1 }) {
                // 找到起始段
                var end = i + 5
                while (end < chartDataList.size && chartDataList[end].extra == 1) {
                    end++
                }

                val midIndex = (i + end - 1) / 2
                val centerX = leftPadding + midIndex * barWidth + barWidth / 2
                val iconSize = 24f
                val topY = chartHeight + 30f

                // 绘制闪电图标（居中）
                flashBitmap.let {
                    val dstRect = RectF(
                        centerX - iconSize / 2,
                        topY,
                        centerX + iconSize / 2,
                        topY + iconSize
                    )
                    canvas.drawBitmap(it, null, dstRect, null)
                }
                // 跳到这一段之后
                i = end
            } else {
                i++
            }
        }

        // 绘制柱状图
        chartDataList.forEachIndexed { i, data ->
            val value = data.indexValue.coerceIn(0, 100)
            // 当前柱子绘制区域的左边界位置
            val barLeft = leftPadding + i * barWidth + barWidth * 0.1f
            // 当前柱子绘制区域的右边界位置
            val barRight = barLeft + barWidth * 0.8f
            val top = topPadding + chartHeight * (1 - value / 100f)
            val bottom = topPadding + chartHeight
            val radius = (barRight - barLeft) / 2f
            // 左上右下坐标
            val rect = RectF(barLeft, top, barRight, bottom)
            val path = Path()
            path.addRoundRect(
                rect,
                // 长度为8的数组0，1左上角。2，3右上角
                floatArrayOf(radius, radius, radius, radius, 0f, 0f, 0f, 0f),
                Path.Direction.CW
            )

            canvas.drawPath(path, if (value < 10) redPaint else greenPaint)
        }
    }
}

