package com.technopolitan.mocaspaces.customWheelPicker

import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.widget.Scroller
import com.technopolitan.mocaspaces.R
import java.util.*


open class WheelPicker(context: Context, attrs: AttributeSet?) : View(context, attrs),
    IDebug, IWheelPicker, Runnable {
    private val mHandler: Handler = Handler(Looper.getMainLooper())
    private val mPaint: Paint?
    private val mScroller: Scroller
    private var mTracker: VelocityTracker? = null


    private var isTouchTriggered = false


    private var mOnItemSelectedListener: OnItemSelectedListener? = null
    private var mOnWheelChangeListener: OnWheelChangeListener? = null
    private val mRectDrawn: Rect
    private val mRectIndicatorHead: Rect
    private val mRectIndicatorFoot: Rect
    private val mRectCurrentItem: Rect
    private val mCamera: Camera
    private val mMatrixRotate: Matrix
    private val mMatrixDepth: Matrix

    private var mData: List<*>?


    private var mMaxWidthText: String?


    private var mVisibleItemCount: Int
    private var mDrawnItemCount = 0


    private var mHalfDrawnItemCount = 0


    private var mTextMaxWidth = 0
    private var mTextMaxHeight = 0


    private var mItemTextColor: Int
    private var mSelectedItemTextColor: Int


    private var mItemTextSize: Int


    private var mIndicatorSize: Int


    private var mIndicatorColor: Int


    private var mCurtainColor: Int


    private var mItemSpace: Int


    private var mItemAlign: Int


    private var mItemHeight = 0
    private var mHalfItemHeight = 0


    private var mHalfWheelHeight = 0


    private var mSelectedItemPosition: Int

    private var mCurrentItemPosition = 0

    private var mMinFlingY = 0
    private var mMaxFlingY = 0

    private var mMinimumVelocity = 50
    private var mMaximumVelocity = 8000

    private var mWheelCenterX = 0
    private var mWheelCenterY = 0

    private var mDrawnCenterX = 0
    private var mDrawnCenterY = 0

    private var mScrollOffsetY = 0

    private var mTextMaxWidthPosition: Int

    private var mLastPointY = 0

    private var mDownPointY = 0

    private var mTouchSlop = 8

    /**
     * @see .setSameWidth
     */
    private var hasSameWidth: Boolean

    /**
     * @see .setIndicator
     */
    private var hasIndicator: Boolean

    /**
     * @see .setCurtain
     */
    private var hasCurtain: Boolean

    /**
     *
     * @see .setAtmospheric
     */
    private var hasAtmospheric: Boolean

    /**
     * ????????????????????????
     *
     * @see .setCyclic
     */
    private var isCyclic: Boolean

    /**
     *
     * @see .setCurved
     */
    private var isCurved: Boolean
    private var isClick = false

    private var isForceFinishScroll = false

    /**
     * Font typeface path from assets
     */
    private val fontPath: String?
    private var isDebug = false

    constructor(context: Context) : this(context, null)

    private fun updateVisibleItemCount() {
        if (mVisibleItemCount < 2) throw ArithmeticException("Wheel's visible item count can not be less than 2!")

        // Be sure count of visible item is odd number
        if (mVisibleItemCount % 2 == 0) mVisibleItemCount += 1
        mDrawnItemCount = mVisibleItemCount + 2
        mHalfDrawnItemCount = mDrawnItemCount / 2
    }

    private fun computeTextSize() {
        mTextMaxHeight = 0
        mTextMaxWidth = mTextMaxHeight
        if (hasSameWidth) {
            mTextMaxWidth = mPaint!!.measureText(mData!![0].toString()).toInt()
        } else if (isPosInRang(mTextMaxWidthPosition)) {
            mTextMaxWidth = mPaint!!.measureText(mData!![mTextMaxWidthPosition].toString()).toInt()
        } else if (!TextUtils.isEmpty(mMaxWidthText)) {
            mTextMaxWidth = mPaint!!.measureText(mMaxWidthText).toInt()
        } else {
            for (obj: Any? in mData!!) {
                val text = obj.toString()
                val width = mPaint!!.measureText(text).toInt()
                mTextMaxWidth = Math.max(mTextMaxWidth, width)
            }
        }
        val metrics = mPaint!!.fontMetrics
        mTextMaxHeight = (metrics.bottom - metrics.top).toInt()
    }

    private fun updateItemTextAlign() {
        when (mItemAlign) {
            ALIGN_LEFT -> mPaint!!.textAlign = Paint.Align.LEFT
            ALIGN_RIGHT -> mPaint!!.textAlign = Paint.Align.RIGHT
            else -> mPaint!!.textAlign = Paint.Align.CENTER
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val modeWidth = MeasureSpec.getMode(widthMeasureSpec)
        val modeHeight = MeasureSpec.getMode(heightMeasureSpec)
        val sizeWidth = MeasureSpec.getSize(widthMeasureSpec)
        val sizeHeight = MeasureSpec.getSize(heightMeasureSpec)

        // Correct sizes of original content
        var resultWidth = mTextMaxWidth
        var resultHeight = mTextMaxHeight * mVisibleItemCount + mItemSpace * (mVisibleItemCount - 1)

        // Correct view sizes again if curved is enable
        if (isCurved) {
            resultHeight = (2 * resultHeight / Math.PI).toInt()
        }
        if (isDebug) Log.i(
            TAG,
            "Wheel's content size is ($resultWidth:$resultHeight)"
        )

        // Consideration padding influence the view sizes
        resultWidth += paddingLeft + paddingRight
        resultHeight += paddingTop + paddingBottom
        if (isDebug) Log.i(TAG, "Wheel's size is ($resultWidth:$resultHeight)")

        // Consideration sizes of parent can influence the view sizes
        resultWidth = measureSize(modeWidth, sizeWidth, resultWidth)
        resultHeight = measureSize(modeHeight, sizeHeight, resultHeight)
        setMeasuredDimension(resultWidth, resultHeight)
    }

    private fun measureSize(mode: Int, sizeExpect: Int, sizeActual: Int): Int {
        var realSize: Int
        if (mode == MeasureSpec.EXACTLY) {
            realSize = sizeExpect
        } else {
            realSize = sizeActual
            if (mode == MeasureSpec.AT_MOST) realSize = Math.min(realSize, sizeExpect)
        }
        return realSize
    }

    override fun onSizeChanged(w: Int, h: Int, oldW: Int, oldH: Int) {
        // Set content region
        mRectDrawn[paddingLeft, paddingTop, width - paddingRight] = height - paddingBottom
        if (isDebug) Log.i(
            TAG, "Wheel's drawn rect size is (" + mRectDrawn.width() + ":" +
                    mRectDrawn.height() + ") and location is (" + mRectDrawn.left + ":" +
                    mRectDrawn.top + ")"
        )

        // Get the center coordinates of content region
        mWheelCenterX = mRectDrawn.centerX()
        mWheelCenterY = mRectDrawn.centerY()

        // Correct item drawn center
        computeDrawnCenter()
        mHalfWheelHeight = mRectDrawn.height() / 2
        mItemHeight = mRectDrawn.height() / mVisibleItemCount
        mHalfItemHeight = mItemHeight / 2

        // Initialize fling max Y-coordinates
        computeFlingLimitY()
        // Correct region of indicator
        computeIndicatorRect()
        // Correct region of current select item
        computeCurrentItemRect()
    }

    private fun computeDrawnCenter() {
        when (mItemAlign) {
            ALIGN_LEFT -> mDrawnCenterX = mRectDrawn.left
            ALIGN_RIGHT -> mDrawnCenterX = mRectDrawn.right
            else -> mDrawnCenterX = mWheelCenterX
        }
        mDrawnCenterY = (mWheelCenterY - (mPaint!!.ascent() + mPaint.descent()) / 2).toInt()
    }

    private fun computeFlingLimitY() {
        val currentItemOffset = mSelectedItemPosition * mItemHeight
        mMinFlingY =
            if (isCyclic) Int.MIN_VALUE else -mItemHeight * (mData!!.size - 1) + currentItemOffset
        mMaxFlingY = if (isCyclic) Int.MAX_VALUE else currentItemOffset
    }

    private fun computeIndicatorRect() {
        if (!hasIndicator) return
        val halfIndicatorSize = mIndicatorSize / 2
        val indicatorHeadCenterY = mWheelCenterY + mHalfItemHeight
        val indicatorFootCenterY = mWheelCenterY - mHalfItemHeight
        mRectIndicatorHead[mRectDrawn.left, indicatorHeadCenterY - halfIndicatorSize, mRectDrawn.right] =
            indicatorHeadCenterY + halfIndicatorSize
        mRectIndicatorFoot[mRectDrawn.left, indicatorFootCenterY - halfIndicatorSize, mRectDrawn.right] =
            indicatorFootCenterY + halfIndicatorSize
    }

    private fun computeCurrentItemRect() {
        if (!hasCurtain && mSelectedItemTextColor == -1) return
        mRectCurrentItem[mRectDrawn.left, mWheelCenterY - mHalfItemHeight, mRectDrawn.right] =
            mWheelCenterY + mHalfItemHeight
    }

    override fun onDraw(canvas: Canvas) {
        if (null != mOnWheelChangeListener) mOnWheelChangeListener!!.onWheelScrolled(mScrollOffsetY)
        if (mData!!.size == 0) return
        val drawnDataStartPos = -mScrollOffsetY / mItemHeight - mHalfDrawnItemCount
        var drawnDataPos = drawnDataStartPos + mSelectedItemPosition
        var drawnOffsetPos = -mHalfDrawnItemCount
        while (drawnDataPos < drawnDataStartPos + mSelectedItemPosition + mDrawnItemCount) {
            var data: String = ""
            if (isCyclic) {
                var actualPos = drawnDataPos % mData!!.size
                actualPos = if (actualPos < 0) actualPos + mData!!.size else actualPos
                data = mData!![actualPos].toString()
            } else {
                if (isPosInRang(drawnDataPos)) data = mData!![drawnDataPos].toString()
            }
            mPaint!!.color = mItemTextColor
            mPaint.style = Paint.Style.FILL
            val mDrawnItemCenterY =
                mDrawnCenterY + drawnOffsetPos * mItemHeight + mScrollOffsetY % mItemHeight
            var distanceToCenter = 0
            if (isCurved) {
                // Correct ratio of item's drawn center to wheel center
                val ratio = (mDrawnCenterY - Math.abs(mDrawnCenterY - mDrawnItemCenterY) -
                        mRectDrawn.top) * 1.0f / (mDrawnCenterY - mRectDrawn.top)

                // Correct unit
                var unit = 0
                if (mDrawnItemCenterY > mDrawnCenterY) unit =
                    1 else if (mDrawnItemCenterY < mDrawnCenterY) unit = -1
                var degree = -(1 - ratio) * 90 * unit
                if (degree < -90) degree = -90f
                if (degree > 90) degree = 90f
                distanceToCenter = computeSpace(degree.toInt())
                var transX = mWheelCenterX
                when (mItemAlign) {
                    ALIGN_LEFT -> transX = mRectDrawn.left
                    ALIGN_RIGHT -> transX = mRectDrawn.right
                }
                val transY = mWheelCenterY - distanceToCenter
                mCamera.save()
                mCamera.rotateX(degree)
                mCamera.getMatrix(mMatrixRotate)
                mCamera.restore()
                mMatrixRotate.preTranslate(-transX.toFloat(), -transY.toFloat())
                mMatrixRotate.postTranslate(transX.toFloat(), transY.toFloat())
                mCamera.save()
                mCamera.translate(0.toFloat(), 0.toFloat(), computeDepth(degree.toInt()).toFloat())
                mCamera.getMatrix(mMatrixDepth)
                mCamera.restore()
                mMatrixDepth.preTranslate(-transX.toFloat(), -transY.toFloat())
                mMatrixDepth.postTranslate(transX.toFloat(), transY.toFloat())
                mMatrixRotate.postConcat(mMatrixDepth)
            }
            if (hasAtmospheric) {
                var alpha = ((mDrawnCenterY - Math.abs(mDrawnCenterY - mDrawnItemCenterY)) *
                        1.0f / mDrawnCenterY * 255).toInt()
                alpha = if (alpha < 0) 0 else alpha
                mPaint.alpha = alpha
            }
            // Correct item's drawn centerY base on curved state
            val drawnCenterY = if (isCurved) mDrawnCenterY - distanceToCenter else mDrawnItemCenterY

            // Judges need to draw different color for current item or not
            if (mSelectedItemTextColor != -1) {
                canvas.save()
                if (isCurved) canvas.concat(mMatrixRotate)
                canvas.clipRect(mRectCurrentItem, Region.Op.DIFFERENCE)
                canvas.drawText(
                    data, mDrawnCenterX.toFloat(), drawnCenterY.toFloat(),
                    mPaint
                )
                canvas.restore()
                mPaint.color = mSelectedItemTextColor
                canvas.save()
                if (isCurved) canvas.concat(mMatrixRotate)
                canvas.clipRect(mRectCurrentItem)
                canvas.drawText(
                    data, mDrawnCenterX.toFloat(), drawnCenterY.toFloat(),
                    mPaint
                )
                canvas.restore()
            } else {
                canvas.save()
                canvas.clipRect(mRectDrawn)
                if (isCurved) canvas.concat(mMatrixRotate)
                canvas.drawText(
                    data, mDrawnCenterX.toFloat(), drawnCenterY.toFloat(),
                    mPaint
                )
                canvas.restore()
            }
            if (isDebug) {
                canvas.save()
                canvas.clipRect(mRectDrawn)
                mPaint.color = -0x11cccd
                val lineCenterY = mWheelCenterY + drawnOffsetPos * mItemHeight
                canvas.drawLine(
                    mRectDrawn.left.toFloat(),
                    lineCenterY.toFloat(),
                    mRectDrawn.right.toFloat(),
                    lineCenterY.toFloat(),
                    mPaint
                )
                mPaint.color = -0xcccc12
                mPaint.style = Paint.Style.STROKE
                val top = lineCenterY - mHalfItemHeight
                canvas.drawRect(
                    mRectDrawn.left.toFloat(),
                    top.toFloat(),
                    mRectDrawn.right.toFloat(),
                    (top + mItemHeight).toFloat(),
                    mPaint
                )
                canvas.restore()
            }
            drawnDataPos++
            drawnOffsetPos++
        }
        // Need to draw curtain or not
        if (hasCurtain) {
            mPaint!!.color = mCurtainColor
            mPaint.style = Paint.Style.FILL
            canvas.drawRect(mRectCurrentItem, mPaint)
        }
        // Need to draw indicator or not
        if (hasIndicator) {
            mPaint!!.color = mIndicatorColor
            mPaint.style = Paint.Style.FILL
            canvas.drawRect(mRectIndicatorHead, mPaint)
            canvas.drawRect(mRectIndicatorFoot, mPaint)
        }
        if (isDebug) {
            mPaint!!.color = 0x4433EE33
            mPaint.style = Paint.Style.FILL
            canvas.drawRect(0f, 0f, paddingLeft.toFloat(), height.toFloat(), mPaint)
            canvas.drawRect(0f, 0f, width.toFloat(), paddingTop.toFloat(), mPaint)
            canvas.drawRect(
                (width - paddingRight).toFloat(), 0f, width.toFloat(), height.toFloat(),
                mPaint
            )
            canvas.drawRect(
                0f, (height - paddingBottom).toFloat(), width.toFloat(), height.toFloat(),
                mPaint
            )
        }
    }

    private fun isPosInRang(position: Int): Boolean {
        return position >= 0 && position < mData!!.size
    }

    private fun computeSpace(degree: Int): Int {
        return (Math.sin(Math.toRadians(degree.toDouble())) * mHalfWheelHeight).toInt()
    }

    private fun computeDepth(degree: Int): Int {
        return (mHalfWheelHeight - Math.cos(Math.toRadians(degree.toDouble())) * mHalfWheelHeight).toInt()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                isTouchTriggered = true
                if (null != parent) parent.requestDisallowInterceptTouchEvent(true)
                if (null == mTracker) mTracker = VelocityTracker.obtain() else mTracker!!.clear()
                mTracker!!.addMovement(event)
                if (!mScroller.isFinished) {
                    mScroller.abortAnimation()
                    isForceFinishScroll = true
                }
                run {
                    mLastPointY = event.y.toInt()
                    mDownPointY = mLastPointY
                }
            }
            MotionEvent.ACTION_MOVE -> {
                if (Math.abs(mDownPointY - event.y) < mTouchSlop) {
                    isClick = true
                    return true
                }
                isClick = false
                mTracker!!.addMovement(event)
                if (null != mOnWheelChangeListener) mOnWheelChangeListener!!.onWheelScrollStateChanged(
                    SCROLL_STATE_DRAGGING
                )

                // Scroll WheelPicker's content
                val move = event.y - mLastPointY
                if (Math.abs(move) < 1) return true
                mScrollOffsetY += move.toInt()
                mLastPointY = event.y.toInt()
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                if (null != parent) parent.requestDisallowInterceptTouchEvent(false)
                if (isClick && !isForceFinishScroll) return true
                mTracker!!.addMovement(event)
                mTracker!!.computeCurrentVelocity(1000)
                // Judges the WheelPicker is scroll or fling base on current velocity
                isForceFinishScroll = false
                val velocity = mTracker!!.yVelocity.toInt()
                if (Math.abs(velocity) > mMinimumVelocity) {
                    mScroller.fling(0, mScrollOffsetY, 0, velocity, 0, 0, mMinFlingY, mMaxFlingY)
                    mScroller.finalY = mScroller.finalY +
                            computeDistanceToEndPoint(mScroller.finalY % mItemHeight)
                } else {
                    mScroller.startScroll(
                        0, mScrollOffsetY, 0,
                        computeDistanceToEndPoint(mScrollOffsetY % mItemHeight)
                    )
                }
                // Correct coordinates
                if (!isCyclic) if (mScroller.finalY > mMaxFlingY) mScroller.finalY =
                    mMaxFlingY else if (mScroller.finalY < mMinFlingY) mScroller.finalY =
                    mMinFlingY
                mHandler.post(this)
                if (null != mTracker) {
                    mTracker!!.recycle()
                    mTracker = null
                }
            }
            MotionEvent.ACTION_CANCEL -> {
                if (null != parent) parent.requestDisallowInterceptTouchEvent(false)
                if (null != mTracker) {
                    mTracker!!.recycle()
                    mTracker = null
                }
            }
        }
        return true
    }

    private fun computeDistanceToEndPoint(remainder: Int): Int {
        if (Math.abs(remainder) > mHalfItemHeight) return if (mScrollOffsetY < 0) -mItemHeight - remainder else mItemHeight - remainder else return -remainder
    }

    override fun run() {
        if (null == mData || mData!!.size == 0) return
        if (mScroller.isFinished && !isForceFinishScroll) {
            if (mItemHeight == 0) return
            var position = (-mScrollOffsetY / mItemHeight + mSelectedItemPosition) % mData!!.size
            position = if (position < 0) position + mData!!.size else position
            if (isDebug) Log.i(
                TAG,
                position.toString() + ":" + mData!![position] + ":" + mScrollOffsetY
            )
            mCurrentItemPosition = position
            if (null != mOnItemSelectedListener && isTouchTriggered) mOnItemSelectedListener!!.onItemSelected(
                this,
                mData!![position], position
            )
            if (null != mOnWheelChangeListener && isTouchTriggered) {
                mOnWheelChangeListener!!.onWheelSelected(position)
                mOnWheelChangeListener!!.onWheelScrollStateChanged(SCROLL_STATE_IDLE)
            }
        }
        if (mScroller.computeScrollOffset()) {
            if (null != mOnWheelChangeListener) mOnWheelChangeListener!!.onWheelScrollStateChanged(
                SCROLL_STATE_SCROLLING
            )
            mScrollOffsetY = mScroller.currY
            postInvalidate()
            mHandler.postDelayed(this, 16)
        }
    }

    override fun setDebug(isDebug: Boolean) {
        this.isDebug = isDebug
    }

    override fun getVisibleItemCount(): Int {
        return mVisibleItemCount
    }

    override fun setVisibleItemCount(count: Int) {
        mVisibleItemCount = count
        updateVisibleItemCount()
        requestLayout()
    }

    override fun isCyclic(): Boolean {
        return isCyclic
    }

    override fun setCyclic(isCyclic: Boolean) {
        this.isCyclic = isCyclic
        computeFlingLimitY()
        invalidate()
    }

    override fun setOnItemSelectedListener(listener: OnItemSelectedListener?) {
        mOnItemSelectedListener = listener
    }

    override fun getSelectedItemPosition(): Int {
        return mSelectedItemPosition
    }

    override fun setSelectedItemPosition(position: Int) {
        setSelectedItemPosition(position, true)
    }

    private fun setSelectedItemPosition(position: Int, animated: Boolean) {
        var position = position
        isTouchTriggered = false
        if (animated && mScroller.isFinished) { // We go non-animated regardless of "animated" parameter if scroller is in motion
            val length = getData()!!.size
            var itemDifference = position - mCurrentItemPosition
            if (itemDifference == 0) return
            if (isCyclic && Math.abs(itemDifference) > length / 2) { // Find the shortest path if it's cyclic
                itemDifference += if (itemDifference > 0) -length else length
            }
            mScroller.startScroll(0, mScroller.currY, 0, -itemDifference * mItemHeight)
            mHandler.post(this)
        } else {
            if (!mScroller.isFinished) mScroller.abortAnimation()
            position = Math.min(position, mData!!.size - 1)
            position = Math.max(position, 0)
            mSelectedItemPosition = position
            mCurrentItemPosition = position
            mScrollOffsetY = 0
            computeFlingLimitY()
            requestLayout()
            invalidate()
        }
    }

    override fun getCurrentItemPosition(): Int {
        return mCurrentItemPosition
    }

    override fun getData(): List<*>? {
        return mData
    }

    override fun setData(data: List<*>?) {
        if (null == data) throw NullPointerException("WheelPicker's data can not be null!")
        mData = data

        // ????????????
        if (mSelectedItemPosition > data.size - 1 || mCurrentItemPosition > data.size - 1) {
            mCurrentItemPosition = data.size - 1
            mSelectedItemPosition = mCurrentItemPosition
        } else {
            mSelectedItemPosition = mCurrentItemPosition
        }
        mScrollOffsetY = 0
        computeTextSize()
        computeFlingLimitY()
        requestLayout()
        invalidate()
    }

    override fun setSameWidth(hasSameWidth: Boolean) {
        this.hasSameWidth = hasSameWidth
        computeTextSize()
        requestLayout()
        invalidate()
    }

    override fun hasSameWidth(): Boolean {
        return hasSameWidth
    }

    override fun setOnWheelChangeListener(listener: OnWheelChangeListener?) {
        mOnWheelChangeListener = listener
    }

    override fun getMaximumWidthText(): String? {
        return mMaxWidthText
    }

    override fun setMaximumWidthText(text: String?) {
        if (null == text) throw NullPointerException("Maximum width text can not be null!")
        mMaxWidthText = text
        computeTextSize()
        requestLayout()
        invalidate()
    }

    override fun getMaximumWidthTextPosition(): Int {
        return mTextMaxWidthPosition
    }

    override fun setMaximumWidthTextPosition(position: Int) {
        if (!isPosInRang(position)) throw ArrayIndexOutOfBoundsException(
            "Maximum width text Position must in [0, " +
                    mData!!.size + "), but current is " + position
        )
        mTextMaxWidthPosition = position
        computeTextSize()
        requestLayout()
        invalidate()
    }

    override fun getSelectedItemTextColor(): Int {
        return mSelectedItemTextColor
    }

    override fun setSelectedItemTextColor(color: Int) {
        mSelectedItemTextColor = color
        computeCurrentItemRect()
        invalidate()
    }

    override fun getItemTextColor(): Int {
        return mItemTextColor
    }

    override fun setItemTextColor(color: Int) {
        mItemTextColor = color
        invalidate()
    }

    override fun getItemTextSize(): Int {
        return mItemTextSize
    }

    override fun setItemTextSize(size: Int) {
        mItemTextSize = size
        mPaint!!.textSize = mItemTextSize.toFloat()
        computeTextSize()
        requestLayout()
        invalidate()
    }

    override fun getItemSpace(): Int {
        return mItemSpace
    }

    override fun setItemSpace(space: Int) {
        mItemSpace = space
        requestLayout()
        invalidate()
    }

    override fun setIndicator(hasIndicator: Boolean) {
        this.hasIndicator = hasIndicator
        computeIndicatorRect()
        invalidate()
    }

    override fun hasIndicator(): Boolean {
        return hasIndicator
    }

    override fun getIndicatorSize(): Int {
        return mIndicatorSize
    }

    override fun setIndicatorSize(size: Int) {
        mIndicatorSize = size
        computeIndicatorRect()
        invalidate()
    }

    override fun getIndicatorColor(): Int {
        return mIndicatorColor
    }

    override fun setIndicatorColor(color: Int) {
        mIndicatorColor = color
        invalidate()
    }

    override fun setCurtain(hasCurtain: Boolean) {
        this.hasCurtain = hasCurtain
        computeCurrentItemRect()
        invalidate()
    }

    override fun hasCurtain(): Boolean {
        return hasCurtain
    }

    override fun getCurtainColor(): Int {
        return mCurtainColor
    }

    override fun setCurtainColor(color: Int) {
        mCurtainColor = color
        invalidate()
    }

    override fun setAtmospheric(hasAtmospheric: Boolean) {
        this.hasAtmospheric = hasAtmospheric
        invalidate()
    }

    override fun hasAtmospheric(): Boolean {
        return hasAtmospheric
    }

    override fun isCurved(): Boolean {
        return isCurved
    }

    override fun setCurved(isCurved: Boolean) {
        this.isCurved = isCurved
        requestLayout()
        invalidate()
    }

    override fun getItemAlign(): Int {
        return mItemAlign
    }

    override fun setItemAlign(align: Int) {
        mItemAlign = align
        updateItemTextAlign()
        computeDrawnCenter()
        invalidate()
    }

    override fun getTypeface(): Typeface? {
        return if (null != mPaint) mPaint.typeface else null
    }

    override fun setTypeface(tf: Typeface?) {
        if (null != mPaint) mPaint.typeface = tf
        computeTextSize()
        requestLayout()
        invalidate()
    }

    /**
     * @version 1.1.0
     */
    interface OnItemSelectedListener {
        /**

         * @param picker
         * @param data
         * @param position
         */
        fun onItemSelected(picker: WheelPicker?, data: Any?, position: Int)
    }

    /**
     *
     * New project structure
     * @since 2016-06-17
     */
    interface OnWheelChangeListener {
        /**
         * Invoke when WheelPicker scroll stopped
         * WheelPicker will return a distance offset which between current scroll position and
         * initial position, this offset is a positive or a negative, positive means WheelPicker is
         * scrolling from bottom to top, negative means WheelPicker is scrolling from top to bottom
         *
         * Distance offset which between current scroll position and initial position
         */
        fun onWheelScrolled(offset: Int)

        /**
         *
         * Invoke when WheelPicker scroll stopped
         * This method will be called when WheelPicker stop and return current selected item data's
         * position in list
         *
         *
         * Current selected item data's position in list
         */
        fun onWheelSelected(position: Int)

        /**
         *
         * Invoke when WheelPicker's scroll state changed
         * The state of WheelPicker always between idle, dragging, and scrolling, this method will
         * be called when they switch
         *
         *
         * State of WheelPicker, only one of the following
         * [WheelPicker.SCROLL_STATE_IDLE]
         * Express WheelPicker in state of idle
         * [WheelPicker.SCROLL_STATE_DRAGGING]
         * Express WheelPicker in state of dragging
         * [WheelPicker.SCROLL_STATE_SCROLLING]
         * Express WheelPicker in state of scrolling
         */
        fun onWheelScrollStateChanged(state: Int)
    }

    companion object {
        /**
         * @see OnWheelChangeListener.onWheelScrollStateChanged
         */
        val SCROLL_STATE_IDLE = 0
        val SCROLL_STATE_DRAGGING = 1
        val SCROLL_STATE_SCROLLING = 2

        /**

         * @see .setItemAlign
         */
        val ALIGN_CENTER = 0
        val ALIGN_LEFT = 1
        val ALIGN_RIGHT = 2
        private val TAG = WheelPicker::class.java.simpleName
    }

    init {
        val a: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.WheelPicker)
        val idData = a.getResourceId(R.styleable.WheelPicker_wheel_data, 0)
        mData = Arrays.asList(
            *resources
                .getStringArray(if (idData == 0) R.array.WheelArrayWeek else idData)
        )
        mItemTextSize = a.getDimensionPixelSize(
            R.styleable.WheelPicker_wheel_item_text_size,
            resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._18sdp)
        )
        mVisibleItemCount = a.getInt(R.styleable.WheelPicker_wheel_visible_item_count, 7)
        mSelectedItemPosition = a.getInt(R.styleable.WheelPicker_wheel_selected_item_position, 0)
        hasSameWidth = a.getBoolean(R.styleable.WheelPicker_wheel_same_width, false)
        mTextMaxWidthPosition =
            a.getInt(R.styleable.WheelPicker_wheel_maximum_width_text_position, -1)
        mMaxWidthText = a.getString(R.styleable.WheelPicker_wheel_maximum_width_text)
        mSelectedItemTextColor =
            a.getColor(R.styleable.WheelPicker_wheel_selected_item_text_color, -1)
        mItemTextColor = a.getColor(R.styleable.WheelPicker_wheel_item_text_color, -0x777778)
        mItemSpace = a.getDimensionPixelSize(
            R.styleable.WheelPicker_wheel_item_space,
            resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._5sdp)
        )
        isCyclic = a.getBoolean(R.styleable.WheelPicker_wheel_cyclic, false)
        hasIndicator = a.getBoolean(R.styleable.WheelPicker_wheel_indicator, false)
        mIndicatorColor = a.getColor(R.styleable.WheelPicker_wheel_indicator_color, -0x11cccd)
        mIndicatorSize = a.getDimensionPixelSize(
            R.styleable.WheelPicker_wheel_indicator_size,
            resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._1sdp)
        )
        hasCurtain = a.getBoolean(R.styleable.WheelPicker_wheel_curtain, false)
        mCurtainColor = a.getColor(R.styleable.WheelPicker_wheel_curtain_color, -0x77000001)
        hasAtmospheric = a.getBoolean(R.styleable.WheelPicker_wheel_atmospheric, false)
        isCurved = a.getBoolean(R.styleable.WheelPicker_wheel_curved, false)
        mItemAlign = a.getInt(R.styleable.WheelPicker_wheel_item_align, ALIGN_CENTER)
        fontPath = a.getString(R.styleable.WheelPicker_wheel_font_path)
        a.recycle()

        // Update relevant parameters when the count of visible item changed
        updateVisibleItemCount()
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG or Paint.LINEAR_TEXT_FLAG)
        mPaint.textSize = mItemTextSize.toFloat()
        if (fontPath != null) {
            val typeface = Typeface.createFromAsset(context.assets, fontPath)
            setTypeface(typeface)
        }

        // Update alignment of text
        updateItemTextAlign()
        // Correct sizes of text
        computeTextSize()
        mScroller = Scroller(getContext())
        mRectDrawn = Rect()
        mRectIndicatorHead = Rect()
        mRectIndicatorFoot = Rect()
        mRectCurrentItem = Rect()
        mCamera = Camera()
        mMatrixRotate = Matrix()
        mMatrixDepth = Matrix()
    }
}