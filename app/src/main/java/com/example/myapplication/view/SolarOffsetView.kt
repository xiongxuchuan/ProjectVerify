package com.example.myapplication.view


import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.example.myapplication.R

class SolarOffsetView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val rectF = RectF()
    private val path = Path()

    // 默认颜色
    private var progressColor: Int = ContextCompat.getColor(context, R.color.teal_200)
    private var backgroundColor: Int = Color.TRANSPARENT

    // 圆角半径
    private var cornerRadius: Float = 16f

    // 进度值 (0.0f - 1.0f)
    private var progress: Float = 0.5f

    // 高度范围
    private val minHeightDp = 2f
    private val maxHeightDp = 146f

    init {
        // 设置默认背景色
        setBackgroundColor(Color.TRANSPARENT)
    }

    /**
     * 设置进度值 (0.0f - 1.0f)
     */
    fun setProgress(value: Float) {
        progress = value.coerceIn(0f, 1f)
        invalidate()
    }

    /**
     * 设置进度条颜色
     */
    fun setProgressColor(color: Int) {
        progressColor = color
        invalidate()
    }

    /**
     * 设置圆角半径
     */
    fun setCornerRadius(radius: Float) {
        cornerRadius = radius
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)

        // 根据进度计算高度
        val height = (minHeightDp + (maxHeightDp - minHeightDp) * progress).dpToPx()

        setMeasuredDimension(width, height.toInt())
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val width = width.toFloat()
        val height = height.toFloat()

        // 绘制圆角矩形
        paint.color = progressColor
        paint.style = Paint.Style.FILL

        rectF.set(0f, 0f, width, height)

        // 创建圆角路径
        path.reset()
        path.addRoundRect(rectF, cornerRadius, cornerRadius, Path.Direction.CW)

        canvas.drawPath(path, paint)
    }

    /**
     * dp转px
     */
    private fun Float.dpToPx(): Float {
        return this * resources.displayMetrics.density
    }

    /**
     * px转dp
     */
    private fun Float.pxToDp(): Float {
        return this / resources.displayMetrics.density
    }
}

