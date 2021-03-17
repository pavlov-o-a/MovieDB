package com.companyname.movie.presentation.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Matrix
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

//scale type is always FIT_CENTER
//this imageView works in two modes:
//  1) (SCALE case) when drawable is slimmer than width it scales drawable with Matrix
//      to fit width(like CENTER_CROP ScaleType).
//      but with multiplier you can tune scaling.
//      multiplier = 1 means fit to view width.
//      multiplier = 0 means don't fit at all. left as it is
//  2) (TRANSLATE case) when drawable is shorter than height it translate drawable to top
class TopCenterOrScaleImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {
    private var scaleMultiplier = 1f
    private var topCenterMatrix = Matrix()
    private var modeListener: PosterModeListener? = null
    private var imageMatrixArray = FloatArray(9)

    init {
        scaleType = ScaleType.FIT_CENTER
    }

    override fun setScaleType(scaleType: ScaleType?) {
        super.setScaleType(ScaleType.FIT_CENTER)
    }

    fun setScaleMultiplier(m: Float) {
        if (m in 0f..1f) {
            if (scaleMultiplier != m) {
                scaleMultiplier = m
                invalidate()
            }
        }
    }

    override fun onDraw(canvas: Canvas?) {
        drawable?.let {
            imageMatrix.getValues(imageMatrixArray)
            val imgWidth =
                it.intrinsicWidth * imageMatrixArray[0] + it.intrinsicHeight * imageMatrixArray[1]
            val imgHeight =
                it.intrinsicWidth * imageMatrixArray[3] + it.intrinsicHeight * imageMatrixArray[4]
            topCenterMatrix.reset()
            //(TRANSLATE case)
            if (imgHeight < height) {
                modeListener?.modeIsTranslate()
                val dif = (height - imgHeight) / 2f
                topCenterMatrix.postTranslate(0f, -dif)
                canvas?.setMatrix(topCenterMatrix)
            }
            //(SCALE case)
            if (imgWidth < width) {
                modeListener?.modeIsScale()
                val dif = width.toFloat() / imgWidth
                val scale = 1 + (width.toFloat() / imgWidth - 1) * scaleMultiplier
                topCenterMatrix.postScale(scale, scale)
                val translateX = (width - imgWidth) / 2f * dif * scaleMultiplier
                topCenterMatrix.postTranslate(-translateX, 0f)
                canvas?.setMatrix(topCenterMatrix)
            }
        }
        super.onDraw(canvas)
    }

    fun setModeListener(listener: PosterModeListener) {
        modeListener = listener
    }
}

interface PosterModeListener {
    fun modeIsTranslate()
    fun modeIsScale()
}