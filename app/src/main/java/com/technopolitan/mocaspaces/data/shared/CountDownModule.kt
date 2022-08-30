package com.technopolitan.mocaspaces.data.shared

import android.content.Context
import android.os.CountDownTimer
import android.widget.TextView
import com.technopolitan.mocaspaces.R
import dagger.Module
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@Module
class CountDownModule @Inject constructor(private var context: Context) {

    private var countDown: Int = 0
    private var countDownTextView: TextView? = null
    private lateinit var callBackOnFinished: (entity: Boolean) -> Unit
    private lateinit var count: CountDownTimer

    fun init(
        countDownTextView: TextView? = null,
    ) {
        this.countDownTextView = countDownTextView
    }


    private fun initCountDownTimer() {
        count = object : CountDownTimer(getRemainingInMils(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                countDownTextView?.let {
                    it.text = context.getString(
                        R.string.remaining,
                        secondsString(TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished).toInt())
                    )
                }
            }

            override fun onFinish() {
                callBackOnFinished(true)
            }
        }
    }

    fun startCount(countDown: Int, callBackOnFinished: (entity: Boolean) -> Unit) {
        this.countDown = countDown
        this.callBackOnFinished = callBackOnFinished
        initCountDownTimer()
        count.start()
    }

    fun stopCount() {
        count.cancel()
    }

    private fun secondsString(timeInSeconds: Int): String {
        val minutesString: String
        val secondsString: String
        val minutes = TimeUnit.SECONDS.toMinutes(timeInSeconds.toLong())
        minutesString = if (minutes < 10) {
            "0${minutes}"
        } else
            minutes.toString()
        val second = timeInSeconds % 60
        secondsString = if (second < 10)
            "0$second"
        else second.toString()
        return "$minutesString:$secondsString"
    }


    private fun getRemainingInMils() = TimeUnit.SECONDS.toMillis(countDown.toLong())
}