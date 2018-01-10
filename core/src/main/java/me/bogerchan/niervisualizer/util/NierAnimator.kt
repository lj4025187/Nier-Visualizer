package me.bogerchan.niervisualizer.util

import android.animation.TimeInterpolator
import android.os.SystemClock
import android.view.animation.LinearInterpolator

/**
 * Created by BogerChan on 2017/12/3.
 */
class NierAnimator(var interpolator: TimeInterpolator = LinearInterpolator(),
                   var duration: Int = 2000,
                   var values: FloatArray = floatArrayOf(1F)) {

    private var mFraction = 0F
    private var mLastRecordTime = 0L
    private var isRunning = false
    private var mFractionDuration = 0F

    fun start() {
        mLastRecordTime = SystemClock.elapsedRealtime()
        isRunning = true
        mFractionDuration = duration.toFloat() / (values.size - 1)
    }

    fun stop() {
        mFraction = .0F
        isRunning = false
    }

    fun pause() {
        isRunning = false
    }

    fun computeCurrentValue(): Float {
        if (!isRunning) {
            return mFraction
        }
        val curTime = SystemClock.elapsedRealtime()
        val interval = curTime - mLastRecordTime
        val curIdx = (interval % duration / mFractionDuration).toInt()
        mFraction = interval % mFractionDuration / mFractionDuration
        mFraction = interpolator.getInterpolation(mFraction)
        mFraction = values[curIdx] + (values[curIdx + 1] - values[curIdx]) * mFraction
        return mFraction
    }
}