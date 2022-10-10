package com.technopolitan.mocaspaces.utilities.cardStackRecycleView

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.recyclerview.widget.RecyclerView

class CardStackCirclePagerIndicatorDecoration(
    private val activeColorResId: Int,
    private val inActiveColorResId: Int,
    private val spaceBetweenIndicatorsAndRecyclerResDimen: Int,
    private val context: Context,
    private val staticItemCount: Int = 0,
    private val isInfiniteScroll: Boolean = false
) : RecyclerView.ItemDecoration() {


    /**
     * Indicator radius.
     */
    private val mIndicatorRadius =
        context.resources.getDimension(com.intuit.sdp.R.dimen._4sdp)

    /**
     * Indicator width.
     */
    private val mIndicatorItemLength =
        context.resources.getDimension(com.intuit.sdp.R.dimen._10sdp)

//    private val indicatorRadius = context.resources.getDimension()

    /**
     * Padding between indicators.
     */
    private val mIndicatorItemPadding =
        context.resources.getDimension(com.intuit.sdp.R.dimen._10sdp)

    /**
     * Some more natural animation interpolation
     */
    private val mInterpolator = AccelerateDecelerateInterpolator()

    private val mPaint = Paint()

    init {
        mPaint.strokeCap = Paint.Cap.ROUND
        mPaint.strokeWidth = mIndicatorRadius
        mPaint.style = Paint.Style.STROKE
        mPaint.isAntiAlias = true
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        val itemCount =
            if (isInfiniteScroll)
                staticItemCount
            else parent.adapter!!.itemCount

        // center horizontally, calculate width and subtract half from center
        val totalLength = mIndicatorItemLength * itemCount
        val paddingBetweenItems = 0.coerceAtLeast(itemCount - 1) * mIndicatorItemPadding
        val indicatorTotalWidth = totalLength + paddingBetweenItems
        val indicatorStartX = (parent.width - indicatorTotalWidth) / 2f

        // center vertically in the allotted space
        val indicatorPosY = parent.height - context.resources.getDimensionPixelSize(
            spaceBetweenIndicatorsAndRecyclerResDimen
        ) / 2f

        drawInactiveIndicators(c, indicatorStartX, indicatorPosY, itemCount)


        // find active page (which should be highlighted)

        val layoutManager = parent.layoutManager as CardStackLayoutManager?
        val activePosition = layoutManager?.topPosition!!
        if (activePosition == RecyclerView.NO_POSITION) {
            return
        }

        // find offset of active page (if the user is scrolling)
        val activeChild = layoutManager.findViewByPosition(activePosition)
        val left = activeChild!!.left
        val width = activeChild.width

        // on swipe the active item will be positioned from [-width, 0]
        // interpolate offset for smooth animation
        val progress = mInterpolator.getInterpolation(left * -1 / width.toFloat())
        val position = if (isInfiniteScroll) activePosition % staticItemCount else activePosition
        drawHighlights(c, indicatorStartX, indicatorPosY, position, progress, itemCount)
    }

    private fun drawInactiveIndicators(
        c: Canvas,
        indicatorStartX: Float,
        indicatorPosY: Float,
        itemCount: Int
    ) {
        mPaint.color = context.getColor(inActiveColorResId)

        // width of item indicator including padding
        val itemWidth = mIndicatorItemLength + mIndicatorItemPadding

        var start = indicatorStartX
        for (i in 0 until itemCount) {
            // draw the line for every item
            c.drawCircle(start, indicatorPosY, mIndicatorRadius, mPaint)
            start += itemWidth
        }
    }

    private fun drawHighlights(
        c: Canvas, indicatorStartX: Float, indicatorPosY: Float,
        highlightPosition: Int, progress: Float, itemCount: Int
    ) {
        mPaint.color = context.getColor(activeColorResId)

        // width of item indicator including padding
        val itemWidth = mIndicatorItemLength + mIndicatorItemPadding

        if (progress == 0f) {
            // no swipe, draw a normal indicator
            val highlightStart = indicatorStartX + itemWidth * highlightPosition

            c.drawCircle(
                highlightStart, indicatorPosY,
                mIndicatorRadius, mPaint
            )

        } else {
            var highlightStart = indicatorStartX + itemWidth * highlightPosition
            // calculate partial highlight
            val partialLength = mIndicatorItemLength * progress

            // draw the cut off highlight

            c.drawCircle(
                highlightStart + partialLength, indicatorPosY,
                mIndicatorRadius, mPaint
            )

            // draw the highlight overlapping to the next item as well
            if (highlightPosition < itemCount - 1) {
                highlightStart += itemWidth

                c.drawCircle(
                    highlightStart, indicatorPosY,
                    mIndicatorRadius, mPaint
                )
            }
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.bottom =
            context.resources.getDimensionPixelSize(spaceBetweenIndicatorsAndRecyclerResDimen)
    }

}