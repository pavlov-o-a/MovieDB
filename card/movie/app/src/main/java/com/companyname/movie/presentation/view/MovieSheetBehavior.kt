package com.companyname.movie.presentation.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.math.MathUtils
import androidx.core.view.ViewCompat
import androidx.customview.widget.ViewDragHelper
import kotlin.math.abs

class MovieSheetBehavior(context: Context, attrs: AttributeSet) :
    CoordinatorLayout.Behavior<ConstraintLayout>(context, attrs) {
    private var minTop = 0
    private var maxTop = 1080
    private var initialTop = 0
    private var childId = 0
    private var childHeight = 1500
    private var dragHelper: ViewDragHelper? = null
    private var initialY = 0
    private var childMoved = false

    companion object {
        fun getShowMovieCardMultiplier(child: View, parent: View): Float {
            val maxTop = getMaxTop(parent)
            return 2f - child.top.toFloat() * 2f / maxTop
        }

        private fun getInitialTop(parent: View): Int {
            return parent.height / 2
        }

        private fun getMaxTop(parent: View): Int {
            return parent.height
        }

        private fun getMinTop(child: View, parent: View): Int {
            return parent.height - child.height
        }
    }

    override fun onMeasureChild(
        parent: CoordinatorLayout,
        child: ConstraintLayout,
        parentWidthMeasureSpec: Int,
        widthUsed: Int,
        parentHeightMeasureSpec: Int,
        heightUsed: Int
    ): Boolean {
        val parentHeightSpec = View.MeasureSpec.getSize(parentHeightMeasureSpec)
        parent.onMeasureChild(
            child,
            parentWidthMeasureSpec,
            widthUsed,
            View.MeasureSpec.makeMeasureSpec(parentHeightSpec, View.MeasureSpec.UNSPECIFIED),
            heightUsed
        )
        return true
    }

    override fun onLayoutChild(
        parent: CoordinatorLayout,
        child: ConstraintLayout,
        layoutDirection: Int
    ): Boolean {
        updateConstants(child, parent)
        childId = child.id
        var childTop = 0
        if (childMoved)
            childTop = child.top
        parent.onLayoutChild(child, layoutDirection)
        if (!childMoved)
            ViewCompat.offsetTopAndBottom(child, initialTop)
        else
            ViewCompat.offsetTopAndBottom(child, childTop)
        if (dragHelper == null) dragHelper = ViewDragHelper.create(parent, dragCallback)
        return true
    }

    private fun updateConstants(child: View, parent: View) {
        initialTop = getInitialTop(parent)
        maxTop = getMaxTop(parent)
        minTop = getMinTop(child, parent)
        childHeight = child.measuredHeight
    }

    override fun onInterceptTouchEvent(
        parent: CoordinatorLayout,
        child: ConstraintLayout,
        event: MotionEvent
    ): Boolean {
        updateConstants(child, parent)
        if (event.action == MotionEvent.ACTION_DOWN)
            initialY = event.y.toInt()
        if (dragHelper?.shouldInterceptTouchEvent(event) == true)
            return true
        return event.action == MotionEvent.ACTION_MOVE
                && dragHelper != null
                && abs(initialY - event.y) > dragHelper?.touchSlop ?: 4000
    }

    override fun onTouchEvent(
        parent: CoordinatorLayout,
        child: ConstraintLayout,
        event: MotionEvent
    ): Boolean {
        dragHelper?.processTouchEvent(event)

        if (event.action == MotionEvent.ACTION_MOVE) {
            if (abs(initialY - event.y) > dragHelper?.touchSlop ?: 4000) {
                dragHelper?.captureChildView(child, event.getPointerId(event.actionIndex))
            }
        }
        return true
    }

    private fun settle(child: View) {
        dragHelper?.let { dh ->
            dh.smoothSlideViewTo(child, child.left, initialTop)
            ViewCompat.postOnAnimation(child, SettleRunnable(child, dh))
        }
    }

    private fun fling(child: View) {
        dragHelper?.let { dh ->
            dh.flingCapturedView(
                child.left,
                minTop,
                child.left,
                initialTop
            )
            ViewCompat.postOnAnimation(child, SettleRunnable(child, dh))
        }
    }

    private val dragCallback = object : ViewDragHelper.Callback() {
        override fun tryCaptureView(child: View, pointerId: Int): Boolean {
            return child.id == childId
        }

        override fun onViewDragStateChanged(state: Int) {
            if (state == ViewDragHelper.STATE_DRAGGING) {
                childMoved = true
            }
        }

        override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
            return MathUtils.clamp(top, minTop, maxTop)
        }

        override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
            return child.left
        }

        override fun getViewVerticalDragRange(child: View): Int {
            return childHeight
        }

        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            if (releasedChild.top > initialTop) {
                settle(releasedChild)
            } else {
                fling(releasedChild)
            }
        }
    }

    private class SettleRunnable(private val view: View, private val dragHelper: ViewDragHelper) :
        Runnable {
        override fun run() {
            if (dragHelper.continueSettling(true))
                ViewCompat.postOnAnimation(view, this)
        }
    }
}