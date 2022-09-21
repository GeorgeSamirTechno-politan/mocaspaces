package com.technopolitan.mocaspaces.utilities

import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs


fun ViewPager2.autoScroll(interval: Long) {
    val handler = Handler(Looper.getMainLooper())
    var scrollPosition = 0
    val runnable = object : Runnable {

        override fun run() {

            /**
             * Calculate "scroll position" with
             * adapter pages count and current
             * value of scrollPosition.
             */
            val count = adapter?.itemCount ?: 0
            setCurrentItem(scrollPosition++ % count, true)
            handler.postDelayed(this, interval)
        }
    }


    handler.post(runnable)

}

fun ViewPager2.showHorizontalWithVertical(
    nextItemVisibleResId: Int,
    currentItemHorizontalMarginResId: Int
) {
    val nextItemVisiblePx = context.resources.getDimension(nextItemVisibleResId)
    val currentItemHorizontalMarginPx =
        context.resources.getDimension(currentItemHorizontalMarginResId)
    val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
    setPageTransformer { page: View, position: Float ->
        page.translationX = -pageTranslationX * position
        // Next line scales the item's height. You can remove it if you don't want this effect
//            page.scaleY = 1 - (0.25f * abs(position))
        // If you want a fading effect uncomment the next line:
        // page.alpha = 0.25f + (1 - abs(position))
    }
}

fun ViewPager2.zoomOutPageTransformer(enable: Boolean) {
    if (enable) {
        val minScale = 0.85f
        val miniAlpha = 0.5f
        setPageTransformer { view, position ->
            view.apply {
                val pageWidth = width
                val pageHeight = height
                when {
                    position < -1 -> { // [-Infinity,-1)
                        // This page is way off-screen to the left.
                        alpha = 0f
                    }
                    position <= 1 -> { // [-1,1]
                        // Modify the default slide transition to shrink the page as well
                        val scaleFactor = minScale.coerceAtLeast(1 - abs(position))
                        val vertMargin = pageHeight * (1 - scaleFactor) / 2
                        val horzMargin = pageWidth * (1 - scaleFactor) / 2
                        translationX = if (position < 0) {
                            horzMargin - vertMargin / 2
                        } else {
                            horzMargin + vertMargin / 2
                        }

                        // Scale the page down (between MIN_SCALE and 1)
                        scaleX = scaleFactor
                        scaleY = scaleFactor

                        // Fade the page relative to its size.
                        alpha = (miniAlpha +
                                (((scaleFactor - minScale) / (1 - minScale)) * (1 - miniAlpha)))
                    }
                    else -> { // (1,+Infinity]
                        // This page is way off-screen to the right.
                        alpha = 0f
                    }
                }
            }
        }
    }
}

fun ViewPager2.depthPageTransformer(enable: Boolean) {

    if (enable) {
        val minScale = 0.75f
        setPageTransformer { view, position ->
            view.apply {
                val pageWidth = width
                when {
                    position < -1 -> { // [-Infinity,-1)
                        // This page is way off-screen to the left.
                        alpha = 0f
                    }
                    position <= 0 -> { // [-1,0]
                        // Use the default slide transition when moving to the left page
                        alpha = 1f
                        translationX = 0f
                        translationZ = 0f
                        scaleX = 1f
                        scaleY = 1f
                    }
                    position <= 1 -> { // (0,1]
                        // Fade the page out.
                        alpha = 1 - position

                        // Counteract the default slide transition
                        translationX = pageWidth * -position
                        // Move it behind the left page
                        translationZ = -1f

                        // Scale the page down (between MIN_SCALE and 1)
                        val scaleFactor = (minScale + (1 - minScale) * (1 - abs(position)))
                        scaleX = scaleFactor
                        scaleY = scaleFactor
                    }
                    else -> { // (1,+Infinity]
                        // This page is way off-screen to the right.
                        alpha = 0f
                    }
                }
            }
        }
    }
}