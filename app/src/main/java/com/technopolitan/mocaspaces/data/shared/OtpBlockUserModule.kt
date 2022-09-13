package com.technopolitan.mocaspaces.data.shared

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.enums.AppKeys
import com.technopolitan.mocaspaces.modules.DateTimeModule
import com.technopolitan.mocaspaces.modules.DateTimeUnits
import com.technopolitan.mocaspaces.modules.DialogModule
import com.technopolitan.mocaspaces.modules.SharedPrefModule
import com.technopolitan.mocaspaces.utilities.DateTimeConstants
import dagger.Module
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.*
import javax.inject.Inject

@Module
class OtpBlockUserModule @Inject constructor(
    private var context: Context,
    private var dialogModule: DialogModule,
    private var sharedPrefModule: SharedPrefModule,
    private var dateTimeModule: DateTimeModule,
    private var countDownModule: CountDownModule
) {
    private var countDown: Int = 0
    private var tries: Int = 0
    private var triesPublished: PublishSubject<Int> = PublishSubject.create()
    private lateinit var resendTextView: TextView
    private lateinit var countDownTextView: TextView
    private lateinit var emailLayout: LinearLayout
    private lateinit var resendCallBack: (entity: Boolean) -> Unit
    private val maxTries = 4

    /*
    type 1 for mobile
    type 2 for email
     */
    private var type: Int = 1


    fun init(
        resendTextView: TextView,
        countDownTextView: TextView,
        resendCallBack: (entity: Boolean) -> Unit

    ) {
        this.type = 1
        this.resendTextView = resendTextView
        this.countDownTextView = countDownTextView
        this.resendCallBack = resendCallBack
        initTriesObserver()
        countDownModule.init(countDownTextView)
        initOTPTry()

    }

    fun init(
        emailLayout: LinearLayout,
        resendTextView: TextView,
        countDownTextView: TextView,
        resendCallBack: (entity: Boolean) -> Unit

    ) {
        this.emailLayout = emailLayout
        this.type = 2
        this.resendTextView = resendTextView
        this.countDownTextView = countDownTextView
        this.resendCallBack = resendCallBack
        initTriesObserver()
        countDownModule.init(countDownTextView)
        initOTPTry()

    }


    private fun initOTPTry() {
        when (type) {
            1 -> initOTPTryMobile()
            2 -> initOTPTryEmail()
        }
        triesPublished.onNext(tries)
    }

    private fun initOTPTryMobile() {
        if (sharedPrefModule.contain(AppKeys.OTPMobileTries.name)) {
            tries = sharedPrefModule.getIntFromShared(AppKeys.OTPMobileTries.name)
            if (tries == 0)
                enableSendOtpMobile()
        } else {
            sharedPrefModule.setIntToShared(AppKeys.OTPMobileTries.name, maxTries)
            tries = maxTries
        }
        initCountDown()
    }

    private fun initOTPTryEmail() {
        if (sharedPrefModule.contain(AppKeys.OTPEmailTries.name)) {
            tries = sharedPrefModule.getIntFromShared(AppKeys.OTPEmailTries.name)
            if (tries == 0)
                enableSendOtpEmail()
        } else {
            sharedPrefModule.setIntToShared(AppKeys.OTPEmailTries.name, maxTries)
            tries = maxTries
        }

        initCountDown()
    }

    private fun initCountDown() {
        when (tries) {
            4 -> updateCountDown(180)
            3 -> updateCountDown(300)
            2 -> updateCountDown(480)
            1 -> updateCountDown(10)
            0 -> {
                enableResend(enable = true, resendSMS = false)
                blockUser()
                showBlockedDialog()
            }
        }
    }

    private fun updateCountDown(countDown: Int) {
        this.countDown = countDown
        enableResend(enable = false, resendSMS = false)
        countDownModule.startCount(countDown) {
            enableResend(enable = true, resendSMS = true)
        }
    }

    private fun triesObservable(): Observable<Int> = triesPublished

    private fun initTriesObserver() {
        triesObservable()
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                updateTries()
                initCountDown()
            }
    }

    private fun updateTries() {
        when (type) {
            1 -> sharedPrefModule.setIntToShared(AppKeys.OTPMobileTries.name, tries)
            2 -> sharedPrefModule.setIntToShared(AppKeys.OTPEmailTries.name, tries)
        }
    }


    private fun enableSendOtpMobile() {
        if (sharedPrefModule.contain(AppKeys.BlockedMobile.name)) {
            if (mobileHasMoreThanTwoHours()) {
                resetTries(true)
            } else {
                showBlockedDialog()
            }
        }
    }


    private fun mobileHasMoreThanTwoHours(): Boolean {
        return if (sharedPrefModule.contain(AppKeys.BlockedDateTimeMobile.name)) {
            dateTimeModule.diffInDates(
                Calendar.getInstance().time,
                dateTimeModule.formatDate(
                    sharedPrefModule.getStringFromShared(AppKeys.BlockedDateTimeMobile.name),
                    DateTimeConstants.dateTimeFormat
                )!!, DateTimeUnits.HOURS
            ) > 2
        } else true
    }

    private fun enableSendOtpEmail() {
        if (sharedPrefModule.contain(AppKeys.BlockedEmail.name)) {
            if (emailHasMoreThanTwoHours()) {
                resetTries(true)
            }
        } else showBlockedDialog()
    }

    private fun emailHasMoreThanTwoHours(): Boolean {
        return if (sharedPrefModule.contain(AppKeys.BlockedDateTimeEmail.name))
            return dateTimeModule.diffInDates(
                Calendar.getInstance().time,
                dateTimeModule.formatDate(
                    sharedPrefModule.getStringFromShared(AppKeys.BlockedDateTimeEmail.name),
                    DateTimeConstants.dateTimeFormat
                )!!
            ) > 2
        else true
    }

    fun resetTries(resetTries: Boolean) {
        when (type) {
            1 -> sharedPrefModule.setIntToShared(AppKeys.OTPMobileTries.name, maxTries)
            2 -> sharedPrefModule.setIntToShared(AppKeys.OTPEmailTries.name, maxTries)
        }
        if (resetTries) {
            tries = maxTries
            triesPublished.onNext(tries)
        }

    }


    private fun blockUser() {
        when (type) {
            1 -> blockSendMobile()
            2 -> blockSendEmail()
        }
    }

    private fun blockSendMobile() {
        sharedPrefModule.setBooleanToShared(AppKeys.BlockedMobile.name, true)
        dateTimeModule.getTodayDateOrTime(DateTimeConstants.dateTimeFormat)?.let {
            sharedPrefModule.setStringToShared(
                AppKeys.BlockedDateTimeMobile.name,
                it
            )
        }
    }

    private fun blockSendEmail() {
        sharedPrefModule.setBooleanToShared(AppKeys.BlockedEmail.name, true)
        dateTimeModule.getTodayDateOrTime(DateTimeConstants.dateTimeFormat)?.let {
            sharedPrefModule.setStringToShared(
                AppKeys.BlockedDateTimeEmail.name,
                it
            )
        }
    }

    private fun enableResend(enable: Boolean, resendSMS: Boolean) {
        resendTextView.isEnabled = enable
        if (enable) {
            resendTextView.setTextColor(context.getColor(R.color.accent_color))
            if (type == 2)
                emailLayout.visibility = View.VISIBLE

        } else {
            resendTextView.setTextColor(context.getColor(R.color.light_black_color_68))
            if (type == 2)
                emailLayout.visibility = View.GONE
        }
        resendTextView.setOnClickListener {
            if (resendSMS) {
                decreaseTries()
                resendCallBack(true)
            } else if (tries == 0) {
                showBlockedDialog()
            }

        }
    }

    private fun decreaseTries() {
        tries -= 1
        triesPublished.onNext(tries)
    }

    fun showBlockedDialog() {
        dialogModule.showMessageDialog(
            context.getString(R.string.otp_send_block_message),
        ) {

        }
    }

    fun stopCount() {
        countDownModule.stopCount()
    }

    fun canAuthenticate(type: Int): Boolean {
        return when (type) {
            1 -> mobileHasMoreThanTwoHours()
            2 -> emailHasMoreThanTwoHours()
            else -> false
        }
    }
}