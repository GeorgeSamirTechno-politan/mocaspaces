package com.technopolitan.mocaspaces.data.shared

import android.content.Context
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.modules.NavigationModule
import com.technopolitan.mocaspaces.modules.RXModule
import com.technopolitan.mocaspaces.modules.SmsIdentifierModule
import com.technopolitan.mocaspaces.modules.UtilityModule
import dagger.Module
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject


@Module
class OTPDataModule @Inject constructor(
    private var context: Context,
    private var rxModule: RXModule,
    private var otpBlockUserModule: OtpBlockUserModule,
    private var navigationModule: NavigationModule,
    private var smsIdentifierModule: SmsIdentifierModule,
    private var utilityModule: UtilityModule
) {

    private lateinit var changeNumberTextView: TextView
    private lateinit var otpFirstEditText: EditText
    private lateinit var otpSecondEditText: EditText
    private lateinit var otpThirdEditText: EditText
    private lateinit var otpFourthEditText: EditText
    private lateinit var countDownTextView: TextView
    private lateinit var countDownText: TextView
    private lateinit var errorTextView: TextView
    private lateinit var emailLayout: LinearLayout
    private lateinit var firstOtpObserver: Observable<String>
    private lateinit var secondOtpObserver: Observable<String>
    private lateinit var thirdOtpObserver: Observable<String>
    private lateinit var fourthOtpObserver: Observable<String>
    private lateinit var resendCallBack: (entity: Boolean) -> Unit
    private lateinit var validOtpCallBack: (entity: String) -> Unit
    private lateinit var shakeAnimation: Animation

    /*
    type 1 for mobile
    type 2 for email
     */
    private var type: Int = 1


    fun initMobile(
        changeNumberTextView: TextView,
        otpFirstEditText: EditText,
        otpSecondEditText: EditText,
        otpThirdEditText: EditText,
        otpFourthEditText: EditText,
        countDownTextView: TextView,
        resendTextView: TextView,
        errorTextView: TextView,
        resendCallBack: (entity: Boolean) -> Unit,
        validOtpCallBack: (entity: String) -> Unit,
    ) {
        this.errorTextView = errorTextView
        this.changeNumberTextView = changeNumberTextView
        this.otpFirstEditText = otpFirstEditText
        this.otpSecondEditText = otpSecondEditText
        this.otpThirdEditText = otpThirdEditText
        this.otpFourthEditText = otpFourthEditText
        this.countDownTextView = countDownTextView
        this.countDownText = resendTextView
        this.resendCallBack = resendCallBack
        this.validOtpCallBack = validOtpCallBack
        this.type = 1
        initObservers()
        otpBlockUserModule.init(resendTextView, countDownTextView, resendCallBack)
        shakeAnimation = AnimationUtils.loadAnimation(context, R.anim.shake)
        clickOnChangeNumber()
        handleSmsReceiver()

    }

    fun initEmail(
        emailLayout: LinearLayout,
        changeNumberTextView: TextView,
        otpFirstEditText: EditText,
        otpSecondEditText: EditText,
        otpThirdEditText: EditText,
        otpFourthEditText: EditText,
        countDownTextView: TextView,
        resendTextView: TextView,
        errorTextView: TextView,
        resendCallBack: (entity: Boolean) -> Unit,
        validOtpCallBack: (entity: String) -> Unit,
    ) {
        this.emailLayout = emailLayout
        this.errorTextView = errorTextView
        this.changeNumberTextView = changeNumberTextView
        this.otpFirstEditText = otpFirstEditText
        this.otpSecondEditText = otpSecondEditText
        this.otpThirdEditText = otpThirdEditText
        this.otpFourthEditText = otpFourthEditText
        this.countDownTextView = countDownTextView
        this.countDownText = resendTextView
        this.resendCallBack = resendCallBack
        this.validOtpCallBack = validOtpCallBack
        this.type = 2
        initObservers()
        otpBlockUserModule.init(emailLayout, resendTextView, countDownTextView, resendCallBack)
        shakeAnimation = AnimationUtils.loadAnimation(context, R.anim.shake)
        clickOnChangeNumber()
        handleSmsReceiver()

    }

    private fun handleSmsReceiver() {
        if (type == 1) {
            smsIdentifierModule.init {
                otpFirstEditText.setText(it.substring(0))
                otpSecondEditText.setText(it.substring(1))
                otpThirdEditText.setText(it.substring(2))
                otpFourthEditText.setText(it.substring(3))
            }
        }
    }

    private fun clickOnChangeNumber() {
        changeNumberTextView.setOnClickListener {
            utilityModule.setStatusBar(R.color.white)
            when (type) {
                1 -> navigationModule.popBack()
                2 -> {

                    navigationModule.popBack()
                    navigationModule.popBack()
                }
            }
        }
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
//            isSameOtp() &&
                    noAllEmpty() -> {
                otpBlockUserModule.resetTries(false)
                otpBlockUserModule.stopCount()
                clearAnimationEditText()
                validOtpCallBack(otp())
            }
            !noAllEmpty() -> clearAnimationEditText()
            else -> animateErrorEditText()
        }
    }

    private fun otp(): String = otpFirstEditText.text.toString() + otpSecondEditText.text.toString() + otpThirdEditText.text.toString() +
                otpFourthEditText.text.toString()

    private fun isSameOtp(): Boolean = true
//        (otpFirstEditText.text.toString() + otpSecondEditText.text.toString() + otpThirdEditText.text.toString() +
//                otpFourthEditText.text.toString()) == otp

    private fun noAllEmpty(): Boolean =
        otpFirstEditText.text.toString().isNotEmpty() && otpSecondEditText.text.toString()
            .isNotEmpty() && otpThirdEditText.text.toString().isNotEmpty() &&
                otpFourthEditText.text.toString().isNotEmpty()

    fun animateErrorEditText() {
        otpFirstEditText.startAnimation(shakeAnimation)
        otpSecondEditText.startAnimation(shakeAnimation)
        otpThirdEditText.startAnimation(shakeAnimation)
        otpFourthEditText.startAnimation(shakeAnimation)
        otpFirstEditText.setTextColor(context.getColor(R.color.error_color))
        otpSecondEditText.setTextColor(context.getColor(R.color.error_color))
        otpThirdEditText.setTextColor(context.getColor(R.color.error_color))
        otpFourthEditText.setTextColor(context.getColor(R.color.error_color))
        errorTextView.visibility = View.VISIBLE
    }

    private fun clearAnimationEditText() {
        errorTextView.visibility = View.GONE
        val color =
            if (type == 1) context.getColor(R.color.black) else context.getColor(R.color.white)
        otpFirstEditText.setTextColor(color)
        otpSecondEditText.setTextColor(color)
        otpThirdEditText.setTextColor(color)
        otpFourthEditText.setTextColor(color)

    }

//    fun updateOtp(otp: String) {
//        this.otp = otp
//    }

//    fun updatePermissionResult(it: Boolean?) {
//        smsIdentifierModule.updatePermissionResult(it)
//    }


}