package com.technopolitan.mocaspaces.transitionButton

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.*
import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.util.Property
import android.view.animation.DecelerateInterpolator
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator


class CircularAnimatedDrawable(color: Int, private val mBorderWidth: Float) :
    Drawable(), Animatable {
    private val fBounds = RectF()
    private var mObjectAnimatorSweep: ObjectAnimator? = null
    private var mObjectAnimatorAngle: ObjectAnimator? = null
    private var mModeAppearing = false
    private val mPaint: Paint
    private var mCurrentGlobalAngleOffset = 0f
    private var mCurrentGlobalAngle = 0f
    private var mCurrentSweepAngle = 0f
    private var mRunning = false
    override fun draw(canvas: Canvas) {
        var startAngle = mCurrentGlobalAngle - mCurrentGlobalAngleOffset
        var sweepAngle = mCurrentSweepAngle
        if (!mModeAppearing) {
            startAngle = startAngle + sweepAngle
            sweepAngle = 360 - sweepAngle - MIN_SWEEP_ANGLE
        } else {
            sweepAngle += MIN_SWEEP_ANGLE.toFloat()
        }
        canvas.drawArc(fBounds, startAngle, sweepAngle, false, mPaint)
    }

    override fun setAlpha(alpha: Int) {
        mPaint.alpha = alpha
    }

    override fun setColorFilter(cf: ColorFilter?) {
        mPaint.colorFilter = cf
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSPARENT
    }

    private fun toggleAppearingMode() {
        mModeAppearing = !mModeAppearing
        if (mModeAppearing) {
            mCurrentGlobalAngleOffset = (mCurrentGlobalAngleOffset + MIN_SWEEP_ANGLE * 2) % 360
        }
    }

    override fun onBoundsChange(bounds: Rect) {
        super.onBoundsChange(bounds)
        fBounds.left = bounds.left + mBorderWidth / 2.5f + .5f
        fBounds.right = bounds.right - mBorderWidth / 2.5f - .5f
        fBounds.top = bounds.top + mBorderWidth / 2.5f + .5f
        fBounds.bottom = bounds.bottom - mBorderWidth / 2.5f - .5f
    }

    private val mAngleProperty: Property<CircularAnimatedDrawable, Float> =
        object : Property<CircularAnimatedDrawable, Float>(
            Float::class.java, "angle"
        ) {
            override fun get(`object`: CircularAnimatedDrawable): Float {
                return `object`.currentGlobalAngle
            }

            override fun set(`object`: CircularAnimatedDrawable, value: Float) {
                `object`.currentGlobalAngle = value
            }
        }
    private val mSweepProperty: Property<CircularAnimatedDrawable, Float> =
        object : Property<CircularAnimatedDrawable, Float>(
            Float::class.java, "arc"
        ) {
            override fun get(`object`: CircularAnimatedDrawable): Float {
                return `object`.currentSweepAngle
            }

            override fun set(`object`: CircularAnimatedDrawable, value: Float) {
                `object`.currentSweepAngle = value
            }
        }

    private fun setupAnimations() {
        mObjectAnimatorAngle = ObjectAnimator.ofFloat(this, mAngleProperty, 360f)
        with(mObjectAnimatorAngle) {
            this?.interpolator = ANGLE_INTERPOLATOR
            this?.duration = ANGLE_ANIMATOR_DURATION.toLong()
            this?.repeatMode = ValueAnimator.RESTART
            this?.setRepeatCount(ValueAnimator.INFINITE)
        }
        mObjectAnimatorSweep =
            ObjectAnimator.ofFloat(this, mSweepProperty, 360f - MIN_SWEEP_ANGLE * 2)
        with(mObjectAnimatorSweep) {
            this?.interpolator = SWEEP_INTERPOLATOR
            this?.duration = SWEEP_ANIMATOR_DURATION.toLong()
            this?.repeatMode = ValueAnimator.RESTART
            this?.repeatCount = ValueAnimator.INFINITE
            this?.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {}
                override fun onAnimationEnd(animation: Animator) {}
                override fun onAnimationCancel(animation: Animator) {}
                override fun onAnimationRepeat(animation: Animator) {
                    toggleAppearingMode()
                }
            })
        }
    }

    override fun start() {
        if (isRunning) {
            return
        }
        mRunning = true
        mObjectAnimatorAngle!!.start()
        mObjectAnimatorSweep!!.start()
        invalidateSelf()
    }

    override fun stop() {
        if (!isRunning) {
            return
        }
        mRunning = false
        mObjectAnimatorAngle!!.cancel()
        mObjectAnimatorSweep!!.cancel()
        invalidateSelf()
    }

    override fun isRunning(): Boolean {
        return mRunning
    }

    var currentGlobalAngle: Float
        get() = mCurrentGlobalAngle
        set(currentGlobalAngle) {
            mCurrentGlobalAngle = currentGlobalAngle
            invalidateSelf()
        }
    var currentSweepAngle: Float
        get() = mCurrentSweepAngle
        set(currentSweepAngle) {
            mCurrentSweepAngle = currentSweepAngle
            invalidateSelf()
        }

    companion object {
        private val ANGLE_INTERPOLATOR: Interpolator = LinearInterpolator()
        private val SWEEP_INTERPOLATOR: Interpolator = DecelerateInterpolator()
        private const val ANGLE_ANIMATOR_DURATION = 2000
        private const val SWEEP_ANIMATOR_DURATION = 600
        const val MIN_SWEEP_ANGLE = 30
    }

    init {
        mPaint = Paint()
        mPaint.isAntiAlias = true
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = mBorderWidth
        mPaint.color = color
        setupAnimations()
    }
}