package com.technopolitan.mocaspaces.utilities

import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import kotlin.math.abs

enum class AppBarState {
    EXPANDED, COLLAPSED, IDLE
}

abstract class AppBarStateChangeListener : OnOffsetChangedListener {

    private var mCurrentAppBarState = AppBarState.IDLE
    override fun onOffsetChanged(appBarLayout: AppBarLayout, i: Int) {
        mCurrentAppBarState = if (i == 0) {
            if (mCurrentAppBarState != AppBarState.EXPANDED) {
                onStateChanged(appBarLayout, AppBarState.EXPANDED)
            }
            AppBarState.EXPANDED
        } else if (abs(i) >= appBarLayout.totalScrollRange) {
            if (mCurrentAppBarState != AppBarState.COLLAPSED) {
                onStateChanged(appBarLayout, AppBarState.COLLAPSED)
            }
            AppBarState.COLLAPSED
        } else {
            if (mCurrentAppBarState != AppBarState.IDLE) {
                onStateChanged(appBarLayout, AppBarState.IDLE)
            }
            AppBarState.IDLE
        }
    }

    abstract fun onStateChanged(appBarLayout: AppBarLayout?, appBarState: AppBarState?)
}