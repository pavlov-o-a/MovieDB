package com.companyname.movie.presentation.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.math.MathUtils
import com.companyname.movie.R

class PosterBehavior(context: Context, attrs: AttributeSet) :
    CoordinatorLayout.Behavior<TopCenterOrScaleImageView>(context, attrs) {

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: TopCenterOrScaleImageView,
        dependency: View
    ): Boolean {
        return dependency.id == R.id.dataContainer
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: TopCenterOrScaleImageView,
        dependency: View
    ): Boolean {
        val multiplier = MovieSheetBehavior.getShowMovieCardMultiplier(dependency, parent)
        child.setScaleMultiplier(MathUtils.clamp(multiplier, 0f, 1f))
        return multiplier in 0f..1f

    }
}