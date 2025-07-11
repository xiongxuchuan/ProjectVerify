package com.example.myapplication.view

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import kotlin.math.min
import android.content.Context
import android.graphics.Matrix
import android.graphics.Path
import kotlin.math.cos
import kotlin.math.sin

class CircularProgressView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attrs, defStyle) {
    // 环形view之间的间距
    private var gap = 6f

    private var progress = 0f
    // 环形view宽度
    private val strokeWidth = 26f

    private val progressPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = this@CircularProgressView.strokeWidth
        strokeCap = Paint.Cap.BUTT
        color = Color.GREEN
    }

    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = this@CircularProgressView.strokeWidth
        strokeCap = Paint.Cap.BUTT
        color = Color.GRAY
    }

    private val rectF = RectF()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val size = min(width, height).toFloat()
        val padding = strokeWidth / 2f
        rectF.set(padding, padding, size - padding, size - padding)
        val progressAngle = (progress / 100f) * 360f
        // step1. 绘制进度圆弧（绿色）
        canvas.drawArc(rectF, -90f, progressAngle, false, progressPaint)
        // step2. 绘制圆角矩形线帽（起点和终点）
        drawCapRoundView(progressAngle, size, canvas)
        if (progress == 0f) gap = 0f
        // step3. 绘制灰色背景弧（仅当进度未满时）
        if (progressAngle < 360f) {
            val bgStartAngle = -90f + progressAngle + gap
            val bgSweepAngle = 360f - progressAngle - 2 * gap
            canvas.drawArc(rectF, bgStartAngle, bgSweepAngle, false, backgroundPaint)
            // step4: 绘制灰色背景弧矩形线帽
            drawBgCapRoundView(size, bgStartAngle, canvas)
        }
    }

    private fun drawCapRoundView(
        progressAngle: Float,
        size: Float,
        canvas: Canvas
    ) {
        if (progressAngle > 0) {
            val centerX = width / 2f
            val centerY = height / 2f
            val radius = (size - strokeWidth) / 2f
            val startAngle = -90f
            val startX = centerX + radius * cos(Math.toRadians(startAngle.toDouble())).toFloat()
            val startY = centerY + radius * sin(Math.toRadians(startAngle.toDouble())).toFloat()
            val endAngle = -90f + progressAngle
            val endX = centerX + radius * cos(Math.toRadians(endAngle.toDouble())).toFloat()
            val endY = centerY + radius * sin(Math.toRadians(endAngle.toDouble())).toFloat()
            // 绘制起点矩形线帽
            drawRoundedCap(canvas, startX, startY, startAngle + 90f, Color.GREEN)
            // 绘制终点矩形线帽
            drawRoundedCap(canvas, endX, endY, endAngle + 90f, Color.GREEN)
        }
    }

    private fun drawBgCapRoundView(
        size: Float,
        bgStartAngle: Float,
        canvas: Canvas
    ) {
        val centerX = width / 2f
        val centerY = height / 2f
        val radius = (size - strokeWidth) / 2f
        val startX = centerX + radius * cos(Math.toRadians(bgStartAngle.toDouble())).toFloat()
        val startY = centerY + radius * sin(Math.toRadians(bgStartAngle.toDouble())).toFloat()
        val endAngle = -90 - gap
        val endX = centerX + radius * cos(Math.toRadians(endAngle.toDouble())).toFloat()
        val endY = centerY + radius * sin(Math.toRadians(endAngle.toDouble())).toFloat()
        drawRoundedCap(canvas, startX, startY, bgStartAngle + 90f, Color.GRAY) // +90° 使矩形垂直于切线
        drawRoundedCap(canvas, endX, endY, endAngle + 90f, Color.GRAY)
    }

    /**
     * 在指定位置绘制圆角矩形线帽
     * @param x 线帽中心 X 坐标
     * @param y 线帽中心 Y 坐标
     * @param angle 线帽的旋转角度（与进度条切线方向垂直）
     */
    private fun drawRoundedCap(canvas: Canvas, x: Float, y: Float, angle: Float, capColor: Int) {
        val capWidth = strokeWidth / 2
        val capHeight = strokeWidth
        val cornerRadius = 5F

        // 创建圆角矩形路径
        val capPath = Path().apply {
            addRoundRect(
                RectF(-capWidth / 2, -capHeight / 2, capWidth / 2, capHeight / 2),
                cornerRadius, cornerRadius,
                Path.Direction.CW
            )
        }

        // 旋转并平移路径到目标位置
        val matrix = Matrix()
        matrix.postRotate(angle, 0f, 0f)
        matrix.postTranslate(x, y)
        capPath.transform(matrix)

        // 绘制线帽和进度条颜色一致
        canvas.drawPath(capPath, Paint().apply {
            color = capColor
            style = Paint.Style.FILL
        })
    }

    fun setProgress(value: Float) {
        progress = value.coerceIn(0f, 100f)
        progress = adjustValue(progress)
        invalidate()
    }

    private fun adjustValue(value:Float) :Float {
        return if (value in 95F..99F) 95F else value
    }
}




