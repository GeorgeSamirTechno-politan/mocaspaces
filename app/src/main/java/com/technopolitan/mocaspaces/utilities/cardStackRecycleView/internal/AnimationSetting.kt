package com.technopolitan.mocaspaces.utilities.cardStackRecycleView.internal

import android.view.animation.Interpolator
import com.technopolitan.mocaspaces.utilities.cardStackRecycleView.Direction

interface AnimationSetting {
    fun getDirection(): Direction
    fun getDuration(): Int
    fun getInterpolator(): Interpolator
}