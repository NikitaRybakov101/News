package com.example.news.ui.customView

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.news.ui.customView.interfacesCustomView.InterfaceLoadingProgressbar
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class CustomLoadingProgressbar @JvmOverloads constructor(context : Context, attrs : AttributeSet? = null, style: Int = 0) : View(context,attrs,style) ,
    InterfaceLoadingProgressbar {

    private val widthProgressBarDP = 10
    private var radiusProgressBarPix = 0

    private var canvas = Canvas()
    private var isStart = false

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)

        radiusProgressBarPix = width/2
        setMeasuredDimension(width, width)
    }

    private val animProgressBar = ValueAnimatorX.ofValue(0f, (2 * PI).toFloat()).apply {
        vectorFunction {
            if (currentX >= 2*PI) currentX = 0f
            10f
        }
        render { value -> drawLoadingProgressBar(canvas,value)  }
    }

    private val paint = Paint().apply { isAntiAlias = true }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        this.canvas = canvas

        if(isStart) {
            animProgressBar.render()
        }
    }

    private fun drawLoadingProgressBar(canvas: Canvas, radian: Float) {
        var angle = radian
        val density = 0.005f
        var alpha = 255f

        val c = (angle - (radian - 2f))/density

        while (angle > radian - 2f) {
            angle -= density
            alpha -= 255f/c
            paint.color = Color.argb(alpha.toInt(),0,230,230)

            val x = (radiusProgressBarPix) * cos(angle)
            val y = (radiusProgressBarPix) * sin(angle)

            val x1 = (radiusProgressBarPix - convertDpToPixels(widthProgressBarDP)) * cos(angle)
            val y1 = (radiusProgressBarPix - convertDpToPixels(widthProgressBarDP)) * sin(angle)

            canvas.drawLine(width/2f + x1,height/2f - y1, (width/2f + x), (height/2f - y),paint)

            paint.color = Color.argb(alpha.toInt(),230,0,230)

            canvas.drawLine(width/2f - x1,height/2f + y1, (width/2f - x), (height/2f + y),paint)
        }
        invalidate()
    }

    private fun convertDpToPixels(dp: Int) = (dp * context.resources.displayMetrics.density).toInt()

    override fun start() {
        isStart = true
        invalidate()
    }

    override fun stop() {
        animProgressBar.speed = 0f
        isStart = false
    }
}