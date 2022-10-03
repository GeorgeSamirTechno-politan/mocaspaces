package com.technopolitan.mocaspaces.utilities.cardStackRecycleView

import android.view.animation.AccelerateInterpolator
import android.view.animation.Interpolator
import com.technopolitan.mocaspaces.utilities.cardStackRecycleView.internal.AnimationSetting


class SwipeAnimationSetting constructor(
    private var direction: Direction,
    private var duration: Int = 0,
    private val interpolator: Interpolator
) : AnimationSetting {


    override fun getDirection(): Direction {
        return direction
    }

    override fun getDuration(): Int {
        return duration
    }

    override fun getInterpolator(): Interpolator {
        return interpolator
    }

    class Builder {
        private var direction = Direction.Right
        private var duration: Int = Duration.Normal.duration
        private var interpolator: Interpolator = AccelerateInterpolator()
        fun setDirection(direction: Direction): Builder {
            this.direction = direction
            return this
        }

        fun setDuration(duration: Int): Builder {
            this.duration = duration
            return this
        }

        fun setInterpolator(interpolator: Interpolator): Builder {
            this.interpolator = interpolator
            return this
        }

        fun build(): SwipeAnimationSetting {
            return SwipeAnimationSetting(
                direction,
                duration,
                interpolator
            )
        }
    }
}