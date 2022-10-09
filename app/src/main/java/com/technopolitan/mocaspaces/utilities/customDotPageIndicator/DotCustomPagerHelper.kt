package com.technopolitan.mocaspaces.utilities.customDotPageIndicator

import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView


class DotCustomPagerHelper constructor(private val dotPageIndicator: DotPageIndicator) :
    PagerSnapHelper() {


    override fun findTargetSnapPosition(
        layoutManager: RecyclerView.LayoutManager?,
        velocityX: Int,
        velocityY: Int
    ): Int {
        val position = super.findTargetSnapPosition(layoutManager, velocityX, velocityY)

        // Notify when page changed
        dotPageIndicator.onPageSelected(position)
        return position
    }
}