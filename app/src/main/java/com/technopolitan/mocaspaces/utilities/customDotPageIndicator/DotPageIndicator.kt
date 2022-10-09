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


class DotPageIndicator(context: Context?, attrs: AttributeSet?) :
    LinearLayout(context, attrs) {
    private var indicatorCount = 0
    private var indicatorSize = 0
    private var indicatorMargin = 0
    private var lastSelected = 0
    private var recyclerView: RecyclerView? = null
    private lateinit var recyclerDataObserver: DotRecyclerDataObserver

    init {
        initValues()
    }

    private fun initValues() {
        val dm = resources.displayMetrics
        indicatorSize =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, INDICATOR_SIZE_DIP.toFloat(), dm)
                .toInt()
        indicatorMargin = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            INDICATOR_MARGIN_DIP.toFloat(),
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
            addIndicator(indicatorCount > MAX_INDICATORS, indicatorSize, margin)
        }
    }

    fun onPageSelected(position: Int) {
        if (position == indicatorCount) return
        if (indicatorCount > MAX_INDICATORS) {
            updateOverflowState(position)
        } else {
            updateSimpleState(position)
        }
    }

    private fun addIndicator(isOverflowState: Boolean, indicatorSize: Int, margin: Int) {
        val view = View(context)
        view.setBackgroundResource(R.drawable.dot_indicator)
        if (isOverflowState) {
            animateViewScale(view, STATE_NORMAL)
        } else {
            animateViewScale(view, STATE_NORMAL)
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
        if (lastSelected != -1) animateViewScale(getChildAt(lastSelected), STATE_NORMAL)
        animateViewScale(getChildAt(position), STATE_SELECTED)
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
        Arrays.fill(positionStates, STATE_GONE)
        val start = position - MAX_INDICATORS + 4
        var realStart = 0.coerceAtLeast(start)
        if (realStart + MAX_INDICATORS > indicatorCount) {
            realStart = indicatorCount - MAX_INDICATORS
            positionStates[indicatorCount - 1] = STATE_NORMAL
            positionStates[indicatorCount - 2] = STATE_NORMAL
        } else {
            if (realStart + MAX_INDICATORS - 2 < indicatorCount) {
                positionStates[realStart + MAX_INDICATORS - 2] = STATE_SMALL
            }
            if (realStart + MAX_INDICATORS - 1 < indicatorCount) {
                positionStates[realStart + MAX_INDICATORS - 1] = STATE_SMALLEST
            }
        }
        for (i in realStart until realStart + MAX_INDICATORS - 2) {
            positionStates[i] = STATE_NORMAL
        }
        if (position > 5) {
            positionStates[realStart] = STATE_SMALLEST
            positionStates[realStart + 1] = STATE_SMALL
        } else if (position == 5) {
            positionStates[realStart] = STATE_SMALL
        }
        positionStates[position] = STATE_SELECTED
        updateIndicators(positionStates)
        lastSelected = position
    }

    private fun updateIndicators(positionStates: FloatArray) {
        for (i in 0 until indicatorCount) {
            val v: View = getChildAt(i)
            val state = positionStates[i]
            if (state == STATE_GONE) {
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

    companion object {
        private const val MAX_INDICATORS = 9
        private const val INDICATOR_SIZE_DIP = 12
        private const val INDICATOR_MARGIN_DIP = 7

        // State Indicator for scale factor
        private const val STATE_GONE = 0f
        private const val STATE_SMALLEST = 0.2f
        private const val STATE_SMALL = 0.4f
        private const val STATE_NORMAL = 0.6f
        private const val STATE_SELECTED = 1.0f
    }
}