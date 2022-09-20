package com.technopolitan.mocaspaces.utilities

import android.os.Handler
import android.os.Looper
import androidx.viewpager2.widget.ViewPager2


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