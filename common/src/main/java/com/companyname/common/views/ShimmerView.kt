package com.companyname.common.views

import android.content.Context
import android.graphics.*
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.util.TypedValue
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import com.companyname.shared.entities.R
import kotlin.math.cos

const val SHIMMER_STEP_DP = 10f
const val SHIMMER_ANGLE = 20f
const val SHIMMER_WIDTH_DP = 20f
const val SHIMMER_TRANSLATION_LENGTH_DP = 300f

class ShimmerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {
    private val shimmerGradient: GradientDrawable
    private val shimmerMatrix = Matrix()
    private var currentTranslation = 0f
    private var centerColor: Int
    private var edgeColor: Int
    private val translationLength: Float
    private val translationStep: Float
    private val shimmerWidth: Float

    init {
        centerColor = ContextCompat.getColor(context, R.color.white)
        edgeColor = ContextCompat.getColor(context, R.color.light_grey)
        val colors = arrayOf(edgeColor, centerColor, edgeColor).toIntArray()
        shimmerGradient = GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, colors)
        translationLength = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
            SHIMMER_TRANSLATION_LENGTH_DP,
            resources.displayMetrics)
        translationStep = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
            SHIMMER_STEP_DP,
            resources.displayMetrics)
        shimmerWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
            SHIMMER_WIDTH_DP,
            resources.displayMetrics)

        if (attrs != null) {
            val typedArray =
                context.obtainStyledAttributes(attrs, R.styleable.ShimmerView)
            centerColor = typedArray.getColor(R.styleable.ShimmerView_centerColor, centerColor)
            edgeColor = typedArray.getColor(R.styleable.ShimmerView_edgeColor, edgeColor)
            typedArray.recycle()
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            val cosOfAngle = cos(SHIMMER_ANGLE.toDouble())
            val overflow = ((height/cosOfAngle - height)/2).toInt()
            shimmerGradient.setBounds(0,
                -overflow,
                shimmerWidth.toInt(),
                height + overflow)
            val shimmerCenterX = shimmerWidth / 2f
            val shimmerCenterY = height / 2f
            canvas.save()
            shimmerMatrix.setRotate(SHIMMER_ANGLE, shimmerCenterX, shimmerCenterY)
            currentTranslation = if (currentTranslation > translationLength)
                0f
            else
                currentTranslation + translationStep
            shimmerMatrix.postTranslate(currentTranslation, 0f)
            canvas.setMatrix(shimmerMatrix)
            shimmerGradient.draw(it)
            canvas.restore()
            postInvalidateOnAnimation()
        }
    }
}