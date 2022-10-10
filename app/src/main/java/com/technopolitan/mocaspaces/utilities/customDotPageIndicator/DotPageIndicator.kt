package com.technopolitan.mocaspaces.utilities.customDotPageIndicator


import android.content.Context
import android.transition.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.technopolitan.mocaspaces.R
import java.util.*


class DotPageIndicator(context: Context, attrs: AttributeSet?) :
    LinearLayout(context, attrs) {
    private var indicatorCount = 0
    private var indicatorSize = 0
    private var indicatorMargin = 0
    private var lastSelected = 0
    private var recyclerView: RecyclerView? = null
    private lateinit var recyclerDataObserver: DotRecyclerDataObserver
    private var maxIndicators = 9
    private var indicatorSizeDp = 12
    private var indicatorMarginSizeDp = 7

    // State Indicator for scale factor
    private val stateGone = 0f
    private val stateSmallest = 0.2f
    private val stateSmall = 0.4f
    private val stateNormal = 0.6f
    private val stateSelected = 1.0f

    init {
        initValues()
        maxIndicators = 4
        indicatorSizeDp = context.resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._7sdp)
        indicatorMarginSizeDp =
            context.resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._7sdp)
    }

    private fun initValues() {
        val dm = resources.displayMetrics
        indicatorSize =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, indicatorSizeDp.toFloat(), dm)
                .toInt()
        indicatorMargin = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            indicatorMarginSizeDp.toFloat(),
            dm
        ).toInt()
        recyclerDataObserver = DotRecyclerDataObserver(this)
    }

    fun attachToRecyclerView(recyclerView: RecyclerView?) {
        DotCustomPagerHelper(this).attachToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
        this.recyclerView!!.adapter!!.registerAdapterDataObserver(recyclerDataObserver)
        initDotsIndicator()
    }

    private fun initDotsIndicator() {
        lastSelected = -1
        indicatorCount = recyclerView!!.adapter!!.itemCount
        createDotsIndicator(indicatorSize, indicatorMargin)
        onPageSelected(0)
    }

    private fun createDotsIndicator(indicatorSize: Int, margin: Int) {
        removeAllViews()
        if (indicatorCount <= 1) return
        for (i in 0 until indicatorCount) {
            addIndicator(indicatorCount > maxIndicators, indicatorSize, margin)
        }
    }

    fun onPageSelected(position: Int) {
        if (position == indicatorCount) return
        if (indicatorCount > maxIndicators) {
            updateOverflowState(position)
        } else {
            updateSimpleState(position)
        }
    }

    private fun addIndicator(isOverflowState: Boolean, indicatorSize: Int, margin: Int) {
        val view = View(context)
        view.setBackgroundResource(R.drawable.dot_indicator)
        if (isOverflowState) {
            animateViewScale(view, stateNormal)
        } else {
            animateViewScale(view, stateNormal)
        }
        val params = MarginLayoutParams(indicatorSize, indicatorSize)
        params.leftMargin = margin
        params.rightMargin = margin
        addView(view, params)
    }

    fun updateIndicatorsCount() {
        if (indicatorCount != recyclerView!!.adapter!!.itemCount) initDotsIndicator()
    }

    private fun updateSimpleState(position: Int) {
        if (lastSelected != -1) animateViewScale(getChildAt(lastSelected), stateNormal)
        animateViewScale(getChildAt(position), stateSelected)
        lastSelected = position
    }

    private fun updateOverflowState(position: Int) {
        if (indicatorCount == 0) return
        val transition: Transition = TransitionSet()
            .setOrdering(TransitionSet.ORDERING_TOGETHER)
            .addTransition(ChangeBounds())
            .addTransition(Fade())
        TransitionManager.beginDelayedTransition(this, transition)
        val positionStates = FloatArray(indicatorCount + 1)
        Arrays.fill(positionStates, stateGone)
        val start = position - maxIndicators + 4
        var realStart = 0.coerceAtLeast(start)
        if (realStart + maxIndicators > indicatorCount) {
            realStart = indicatorCount - maxIndicators
            positionStates[indicatorCount - 1] = stateNormal
            positionStates[indicatorCount - 2] = stateNormal
        } else {
            if (realStart + maxIndicators - 2 < indicatorCount) {
                positionStates[realStart + maxIndicators - 2] = stateSmall
            }
            if (realStart + maxIndicators - 1 < indicatorCount) {
                positionStates[realStart + maxIndicators - 1] = stateSmallest
            }
        }
        for (i in realStart until realStart + maxIndicators - 2) {
            positionStates[i] = stateNormal
        }
        if (position > 5) {
            positionStates[realStart] = stateSmallest
            positionStates[realStart + 1] = stateSmall
        } else if (position == 5) {
            positionStates[realStart] = stateSmall
        }
        positionStates[position] = stateSelected
        updateIndicators(positionStates)
        lastSelected = position
    }

    private fun updateIndicators(positionStates: FloatArray) {
        for (i in 0 until indicatorCount) {
            val v: View = getChildAt(i)
            val state = positionStates[i]
            if (state == stateGone) {
                v.visibility = GONE
            } else {
                v.visibility = VISIBLE
                animateViewScale(v, state)
            }
        }
    }

    private fun animateViewScale(view: View?, scale: Float) {
        if (view == null) return
        view.animate().scaleX(scale).scaleY(scale)
    }

    override fun onDetachedFromWindow() {
        if (recyclerView != null) {
            try {
                recyclerView!!.adapter!!.unregisterAdapterDataObserver(recyclerDataObserver)
            } catch (ignored: IllegalStateException) {
            }
        }
        super.onDetachedFromWindow()
    }

    companion object
}