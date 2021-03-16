package com.companyname.common.views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import com.companyname.shared.entities.R

import kotlin.math.min

class RoundCornersContainer : ViewGroup {

    private var leftTop = 0f
    private var rightTop = 0f
    private var leftBottom = 0f
    private var rightBottom = 0f
    private var commonRadius = 0f
    private var isSquare = false
    private var oldWith = 0
    private var oldHeight = 0
    private var path = Path()

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        if (attrs != null) {
            val typedArray =
                context.obtainStyledAttributes(attrs, R.styleable.RoundCornersContainer)
            leftTop = typedArray.getDimensionPixelSize(R.styleable.RoundCornersContainer_leftTopRadius,0).toFloat()
            rightTop = typedArray.getDimensionPixelSize(R.styleable.RoundCornersContainer_rightTopRadius, 0).toFloat()
            leftBottom = typedArray.getDimensionPixelSize(R.styleable.RoundCornersContainer_leftBottomRadius, 0).toFloat()
            rightBottom = typedArray.getDimensionPixelSize(R.styleable.RoundCornersContainer_rightBottomRadius, 0).toFloat()
            commonRadius = typedArray.getDimensionPixelSize(R.styleable.RoundCornersContainer_commonRadius, 0).toFloat()
            isSquare = typedArray.getBoolean(R.styleable.RoundCornersContainer_isSquare, isSquare)
            typedArray.recycle()
        }
        init ()
    }

    private fun init() {
        this.outlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View, outline: Outline?) {
                if (!path.isEmpty && path.isConvex) outline?.setConvexPath(path)
            }
        }
        this.clipToOutline = true
        setWillNotDraw(false)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (childCount != 1) throw Exception("only one child is allowed")
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        val width = getChildAt(0).measuredWidth
        val height = getChildAt(0).measuredHeight
        if (isSquare) {
            val result = MeasureSpec.makeMeasureSpec(min(width, height), MeasureSpec.EXACTLY)
            measureChildren(result, result)
            setMeasuredDimension(result, result)
        } else {
            setMeasuredDimension(
                MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
            )
        }
        if (oldWith != measuredWidth || oldHeight != measuredHeight) {
            oldWith = measuredWidth
            oldHeight = measuredHeight
            remeasurePath()
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        getChildAt(0).layout(0,0, getChildAt(0).measuredWidth, getChildAt(0).measuredHeight)
    }

    private fun remeasurePath() {
        if (oldWith >= (kotlin.math.max(
                leftTop + rightTop,
                leftBottom + rightBottom
            )) && oldHeight >= (kotlin.math.max(leftTop + leftBottom, rightTop + rightBottom))
        ) {
            if (commonRadius > 0){
                leftBottom = commonRadius
                rightBottom = commonRadius
                leftTop = commonRadius
                rightTop = commonRadius
            }
            path = Path()
            path.moveTo(0f, leftTop)
            val leftTopRec = RectF(0f, 0f, leftTop * 2, leftTop * 2)
            path.arcTo(leftTopRec, 180f, 90f)
            path.lineTo(oldWith-rightTop, 0f)
            val rightTopRec = RectF(oldWith-rightTop * 2, 0f, oldWith.toFloat(), rightTop * 2)
            path.arcTo(rightTopRec, 270f, 90f)
            path.lineTo(oldWith.toFloat(), oldHeight-rightBottom)
            val rightBottomRec = RectF(oldWith-rightBottom * 2, oldHeight-rightBottom * 2, oldWith.toFloat(), oldHeight.toFloat())
            path.arcTo(rightBottomRec, 0f, 90f)
            path.lineTo(leftBottom, oldHeight.toFloat())
            val leftBottomRec = RectF(0f, oldHeight-leftBottom * 2, leftBottom * 2, oldHeight.toFloat())
            path.arcTo(leftBottomRec, 90f, 90f)
            path.lineTo(0f, leftTop)
            path.close()
        }
    }

    fun setLeftTopRadius(radius: Float) {
        leftTop = radius
        remeasurePath()
        invalidate()
    }

    fun setLeftBottomRadius(radius: Float) {
        leftBottom = radius
        remeasurePath()
        invalidate()
    }

    fun setRightTopRadius(radius: Float) {
        rightTop = radius
        remeasurePath()
        invalidate()
    }

    fun setRightBottomRadius(radius: Float) {
        rightBottom = radius
        remeasurePath()
        invalidate()
    }

    override fun draw(canvas: Canvas?) {
        canvas?.clipPath(path)
        super.draw(canvas)
    }
}