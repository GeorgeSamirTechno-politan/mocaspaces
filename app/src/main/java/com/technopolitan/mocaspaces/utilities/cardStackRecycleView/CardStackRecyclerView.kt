package com.technopolitan.mocaspaces.utilities.cardStackRecycleView

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import androidx.recyclerview.widget.RecyclerView
import com.technopolitan.mocaspaces.utilities.Constants
import com.technopolitan.mocaspaces.utilities.cardStackRecycleView.internal.CardStackDataObserver
import com.technopolitan.mocaspaces.utilities.cardStackRecycleView.internal.CardStackSnapHelper

class CardStackRecyclerView @JvmOverloads constructor(
    context: Context, attributeSet: AttributeSet? = null,
    defaultStyle: Int = 0
) : RecyclerView(context, attributeSet, defaultStyle) {
    private val observer: CardStackDataObserver = CardStackDataObserver(this)

    init {
        contentDescription = javaClass.name
        initialize()
    }

    override fun setLayoutManager(layout: LayoutManager?) {
        if (layout != null) {
            super.setLayoutManager(layout)
        } else {
            super.setLayoutManager(getDefaultLayoutManager())
        }
    }

    private fun getDefaultLayoutManager(): CardStackLayoutManager {
        return CardStackLayoutManager(context).apply {
            setOverlayInterpolator(LinearInterpolator())
            if (Constants.appLanguage == "en")
                setStackFrom(StackFrom.Right)
            else setStackFrom(StackFrom.Left)
            setVisibleCount(4)
            setDirections(Direction.FREEDOM)
            setTranslationInterval(12.0f)
            setScaleInterval(0.95f)
            setMaxDegree(-180.0f)
            setSwipeThreshold(0.1f)
            setCanScrollHorizontal(false)
            setCanScrollVertical(true)
            setSwipeAnimationSetting(getSwipeAnimationSetting())
            setRewindAnimationSetting(getRewindAnimationSetting())
            setSwipeableMethod(SwipeableMethod.None)
        }
    }

    private fun getSwipeAnimationSetting(): SwipeAnimationSetting =
        SwipeAnimationSetting.Builder()
            .setDirection(direction())
            .setDuration(Duration.Slow.duration)
            .setInterpolator(AccelerateInterpolator())
            .build()


    private fun getRewindAnimationSetting(): RewindAnimationSetting =
        RewindAnimationSetting.Builder()
            .setDirection(direction())
            .setDuration(Duration.Slow.duration)
            .setInterpolator(DecelerateInterpolator())
            .build()

    private fun direction() = if (Constants.appLanguage == "en") Direction.Right else Direction.Left


    override fun setAdapter(adapter: Adapter<*>?) {
        if (layoutManager == null) {
            layoutManager = CardStackLayoutManager(context)
        }
        // Imitate RecyclerView's implementation
        // http://tools.oesf.biz/android-9.0.0_r1.0/xref/frameworks/base/core/java/com/android/internal/widget/RecyclerView.java#1005
        if (getAdapter() != null) {
            getAdapter()!!.unregisterAdapterDataObserver(observer)
            getAdapter()!!.onDetachedFromRecyclerView(this)
        }
        adapter?.registerAdapterDataObserver(observer)
        super.setAdapter(adapter)
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val manager = layoutManager as CardStackLayoutManager?
            manager?.updateProportion(event.x, event.y)
        }
        return super.onInterceptTouchEvent(event)
    }

    fun swipe() {
        if (layoutManager is CardStackLayoutManager) {
            val manager = layoutManager as CardStackLayoutManager?
            smoothScrollToPosition(manager!!.topPosition + 1)
        }
    }

    fun rewind() {
        if (layoutManager is CardStackLayoutManager) {
            val manager = layoutManager as CardStackLayoutManager?
            smoothScrollToPosition(manager!!.topPosition - 1)
        }
    }

    private fun initialize() {
        CardStackSnapHelper().attachToRecyclerView(this)
        overScrollMode = OVER_SCROLL_NEVER
    }

}