package com.technopolitan.mocaspaces.data.shared

import android.content.Context
import android.os.CountDownTimer
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.TextView
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.enums.AppKeys
import com.technopolitan.mocaspaces.modules.DateTimeModule
import com.technopolitan.mocaspaces.modules.DialogModule
import com.technopolitan.mocaspaces.modules.RXModule
import com.technopolitan.mocaspaces.modules.SharedPrefModule
import com.technopolitan.mocaspaces.utilities.DateTimeConstants
import dagger.Module
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@Module
class OTPDataModule @Inject constructor(
    private var context: Context,
    private var dialogModule: DialogModule,
    private var rxModule: RXModule,
    private var sharedPrefModule: SharedPrefModule,
    private var dateTimeModule: DateTimeModule
) {

    private lateinit var mobileTextView: TextView
    private lateinit var changeNumberTextView: TextView
    private lateinit var otpFirstEditText: EditText
    private lateinit var otpSecondEditText: EditText
    private lateinit var otpThirdEditText: EditText
    private lateinit var otpFourthEditText: EditText
    private lateinit var remainingTextView: TextView
    private lateinit var resendTextView: TextView
    private lateinit var errorTextView: TextView
    private lateinit var firstOtpObserver: Observable<String>
    private lateinit var secondOtpObserver: Observable<String>
    private lateinit var thirdOtpObserver: Observable<String>
    private lateinit var fourthOtpObserver: Observable<String>
    private lateinit var otp: String
    private var remainingInSeconds: Int = 180
    private lateinit var resendCallBack: (entity: Boolean) -> Unit
    private lateinit var validOtpCallBack: (entity: Boolean) -> Unit
    private lateinit var shakeAnimation: Animation
    private var tries: Int = 0

    private val count: CountDownTimer = object : CountDownTimer(getRemainingInMils(), 1000) {
        override fun onTick(millisUntilFinished: Long) {
            remainingTextView.text = context.getString(
                R.string.remaining,
                secondsString(TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished).toInt())
            )
        }

        override fun onFinish() {
            resendTextView.setTextColor(context.getColor(R.color.accent_color))
            enableResend(true, tries> 0)
        }
    }

    fun init(
        mobileTextView: TextView,
        changeNumberTextView: TextView,
        otpFirstEditText: EditText,
        otpSecondEditText: EditText,
        otpThirdEditText: EditText,
        otpFourthEditText: EditText,
        remainingTextView: TextView,
        resendTextView: TextView,
        errorTextView: TextView,
        otp: String,
        resendCallBack: (entity: Boolean) -> Unit,
        validOtpCallBack: (entity: Boolean) -> Unit
    ) {
        this.errorTextView = errorTextView
        this.mobileTextView = mobileTextView
        this.changeNumberTextView = changeNumberTextView
        this.otpFirstEditText = otpFirstEditText
        this.otpSecondEditText = otpSecondEditText
        this.otpThirdEditText = otpThirdEditText
        this.otpFourthEditText = otpFourthEditText
        this.otp = otp
        this.remainingTextView = remainingTextView
        this.resendTextView = resendTextView
        this.resendCallBack = resendCallBack
        this.validOtpCallBack = validOtpCallBack
        initObservers()
        shakeAnimation = AnimationUtils.loadAnimation(context, R.anim.shake)
        enableResend(enable = false, resendSMS = false)
        initOTPTry()
        startCount()
    }


    private fun updateRemainingObserver() {
        resendTextView.setTextColor(context.getColor(R.color.light_black_color_68))
        startCount()
    }

    private fun initOTPTry() {
        tries = if (sharedPrefModule.contain(AppKeys.OTPTrys.name)) {
            sharedPrefModule.getIntFromShared(AppKeys.OTPTrys.name)
        }else
            3
    }

    private fun startCount() {
        if (sharedPrefModule.contain(AppKeys.Blocked.name) && sharedPrefModule.getBooleanFromShared(
                AppKeys.Blocked.name
            ) && tries > 0
        ) {
            if (dateTimeModule.diffInHours(
                    Calendar.getInstance().time,
                    dateTimeModule.formatDate(
                        sharedPrefModule.getStringFromShared(AppKeys.BlockedDateTime.name),
                        DateTimeConstants.dateTimeFormat
                    )!!

                ) > 2
            ) {
                sharedPrefModule.setBooleanToShared(AppKeys.Blocked.name, false)
                sharedPrefModule.setStringToShared(AppKeys.BlockedDateTime.name, "")
                count.start()
            } else {
                enableResend(enable = true, resendSMS = false)
                showBlockedDialog()
            }

        } else count.start()

    }


    private fun getRemainingInMils() = TimeUnit.SECONDS.toMillis(remainingInSeconds.toLong())

    private fun enableResend(enable: Boolean, resendSMS: Boolean) {
        resendTextView.isEnabled = enable
        resendTextView.setOnClickListener {
            if (resendSMS) {
                resendCallBack(true)
            } else if (tries == 0) {
                showBlockedDialog()
            }


        }
    }

    private fun secondsString(seconds: Int): String =
        "${TimeUnit.SECONDS.toMinutes(seconds.toLong())}: ${seconds % 60}"

    fun updateRemaining(otp: String) {
        this.otp = otp
        when (tries) {
            3 -> {
                tries = 2
                remainingInSeconds = 300
                updateRemainingObserver()
            }
            2 -> {
                tries = 1
                remainingInSeconds = 480
                updateRemainingObserver()
            }
            1 -> {
                tries = 0
                remainingInSeconds = 960
                updateRemainingObserver()
            }
            0 -> blockUser()
        }
        sharedPrefModule.setIntToShared(AppKeys.OTPTrys.name, tries)
    }

    private fun showBlockedDialog() {
        dialogModule.showMessageDialog(
            context.getString(R.string.otp_send_block_message),
            context.getString(R.string.warning)
        )
    }

    private fun blockUser() {
        enableResend(enable = true, resendSMS = false)
        sharedPrefModule.setBooleanToShared(AppKeys.Blocked.name, true)
        dateTimeModule.getTodayDateOrTime(DateTimeConstants.dateTimeFormat)?.let {
            sharedPrefModule.setStringToShared(
                AppKeys.BlockedDateTime.name,
                it
            )
        }
        showBlockedDialog()
    }


    private fun initObservers() {
        initFirstObserver()
        initSecondObserver()
        initThirdObserver()
        initFourthObserver()
        combineObserver()
    }

    private fun initFirstObserver() {
        firstOtpObserver =
            rxModule.getTextWatcherObservable(otpFirstEditText).subscribeOn(Schedulers.io())
                .observeOn(
                    AndroidSchedulers.mainThread()
                )
        firstOtpObserver.subscribe {
            if (it.isNotEmpty())
                otpSecondEditText.requestFocus()
        }
    }

    private fun initSecondObserver() {
        secondOtpObserver =
            rxModule.getTextWatcherObservable(otpSecondEditText).subscribeOn(Schedulers.io())
                .observeOn(
                    AndroidSchedulers.mainThread()
                )
        secondOtpObserver.subscribe {
            if (it.isNotEmpty())
                otpThirdEditText.requestFocus()
            else otpFirstEditText.requestFocus()
        }
    }

    private fun initThirdObserver() {
        thirdOtpObserver =
            rxModule.getTextWatcherObservable(otpThirdEditText).subscribeOn(Schedulers.io())
                .observeOn(
                    AndroidSchedulers.mainThread()
                )
        thirdOtpObserver.subscribe {
            if (it.isNotEmpty())
                otpFourthEditText.requestFocus()
            else otpSecondEditText.requestFocus()
        }
    }

    private fun initFourthObserver() {
        fourthOtpObserver =
            rxModule.getTextWatcherObservable(otpFourthEditText).subscribeOn(Schedulers.io())
                .observeOn(
                    AndroidSchedulers.mainThread()
                )
        fourthOtpObserver.subscribe {
            if (it.isEmpty())
                otpThirdEditText.requestFocus()
        }
    }

    private fun combineObserver() {
        Observable.combineLatest(
            firstOtpObserver,
            secondOtpObserver,
            thirdOtpObserver,
            fourthOtpObserver
        ) { _: String, _: String, _: String, _: String ->
            return@combineLatest isSameOtp()
        }.subscribe {
            validateOtp()
        }
    }

    private fun validateOtp() {
        when {
            isSameOtp() && noAllEmpty() -> {
                validOtpCallBack(true)
                clearAnimationEditText()
            }
            !noAllEmpty() -> clearAnimationEditText()
            else -> animateErrorEditText()
        }
    }

    private fun isSameOtp(): Boolean =
        (otpFirstEditText.text.toString() + otpSecondEditText.text.toString() + otpThirdEditText.text.toString() +
                otpFourthEditText.text.toString()) == otp

    private fun noAllEmpty(): Boolean =
        otpFirstEditText.text.toString().isNotEmpty() && otpSecondEditText.text.toString()
            .isNotEmpty() && otpThirdEditText.text.toString().isNotEmpty() &&
                otpFourthEditText.text.toString().isNotEmpty()

    private fun animateErrorEditText() {
        otpFirstEditText.startAnimation(shakeAnimation)
        otpSecondEditText.startAnimation(shakeAnimation)
        otpThirdEditText.startAnimation(shakeAnimation)
        otpFourthEditText.startAnimation(shakeAnimation)
        errorTextView.visibility = View.VISIBLE
    }

    private fun clearAnimationEditText() {
        errorTextView.visibility = View.GONE
    }

}