package com.example.myapplication.view

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import kotlin.math.min
import android.content.Context
class CircularProgressView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attrs, defStyle) {

    private var progress = 0f // 0.0 ~ 100.0

    private val strokeWidth = 26f

    private val progressPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = this@CircularProgressView.strokeWidth
        strokeCap = Paint.Cap.SQUARE
        color = Color.GREEN
    }

    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = this@CircularProgressView.strokeWidth
        strokeCap = Paint.Cap.SQUARE
        color = Color.GRAY
    }

    private val rectF = RectF()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val size = min(width, height).toFloat()
        val padding = strokeWidth / 2f
        rectF.set(padding, padding, size - padding, size - padding)

        // 先绘制进度圆弧（绿色），始终从 -90° 开始
        val progressAngle = (progress / 100f) * 360f
        canvas.drawArc(rectF, -90f, progressAngle , false, progressPaint)

        // 设置间隙 gap（单位：度），用于使灰色两端断开
        val gap = 10f

        // 只有当进度未达到 100% 时才绘制剩余的灰色弧段
        if (progressAngle < 360f) {
            // 灰色背景弧不从完整 360° 开始，而是去掉进度弧两端预留的间隙：
            // 从进度弧结束位置（-90 + progressAngle）加上 gap 开始
            // 灰色弧的 sweepAngle = 360 - progressAngle - 2 * gap
            val bgStartAngle = -90f + progressAngle + gap
            val bgSweepAngle = 360f - progressAngle - 2 * gap
            // 注意：如果 progress 为 0，这样灰色弧也会留出两个小 gap
            canvas.drawArc(rectF, bgStartAngle, bgSweepAngle, false, backgroundPaint)
        }
    }

    fun setProgress(value: Float) {
        progress = value.coerceIn(0f, 100f)
        invalidate()
    }
}




