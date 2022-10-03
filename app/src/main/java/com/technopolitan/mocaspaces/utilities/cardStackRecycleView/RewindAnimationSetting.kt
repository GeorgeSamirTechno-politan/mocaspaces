package com.technopolitan.mocaspaces.utilities.cardStackRecycleView

import android.view.animation.DecelerateInterpolator
import android.view.animation.Interpolator
import com.technopolitan.mocaspaces.utilities.cardStackRecycleView.internal.AnimationSetting


class RewindAnimationSetting private constructor(
    private val direction: Direction,
    private val duration: Int,
    interpolator: Interpolator
) : AnimationSetting {
    private val interpolator: Interpolator

    init {
        this.interpolator = interpolator
    }

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
        private var direction = Direction.Bottom
        private var duration = Duration.Normal.duration
        private var interpolator: Interpolator = DecelerateInterpolator()
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

        fun build(): RewindAnimationSetting {
            return RewindAnimationSetting(
                direction,
                duration,
                interpolator
            )
        }
    }
}