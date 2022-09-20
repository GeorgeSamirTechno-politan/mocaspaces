package com.technopolitan.mocaspaces.ui.resetPasswordOtp

import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.technopolitan.mocaspaces.bases.BaseViewModel
import com.technopolitan.mocaspaces.data.ApiStatus
import com.technopolitan.mocaspaces.data.remote.CheckMobileRemote
import com.technopolitan.mocaspaces.data.remote.VerifyMobileOtpForgotPasswordRemote
import com.technopolitan.mocaspaces.data.shared.OTPDataModule
import javax.inject.Inject

class ResetPasswordOtpViewModel @Inject constructor(
    private var otpDataModule: OTPDataModule,
    private var checkMobileRemote: CheckMobileRemote,
    private var verifyMobileOtpForgotPasswordRemote: VerifyMobileOtpForgotPasswordRemote
) : BaseViewModel<String>() {

    private var verifyMobileOtpMediator: MediatorLiveData<ApiStatus<String>> = MediatorLiveData()

    fun initOTPDataModule(
        changeNumberTextView: TextView,
        otpFirstEditText: EditText,
        otpSecondEditText: EditText,
        otpThirdEditText: EditText,
        otpFourthEditText: EditText,
        remainingTextView: TextView,
        resendTextView: TextView,
        errorTextView: TextView,
        resendCallBack: (entity: Boolean?) -> Unit,
        validOtpCallBack: (entity: String) -> Unit
    ) {
        otpDataModule.initMobile(
            changeNumberTextView,
            otpFirstEditText,
            otpSecondEditText,
            otpThirdEditText,
            otpFourthEditText,
            remainingTextView,
            resendTextView,
            errorTextView,
            resendCallBack,
            validOtpCallBack,
        )
    }

    fun checkMobile(mobile: String) {
        this.apiMediatorLiveData = checkMobileRemote.verifyMobile(mobile)
    }

    fun handleCheckMobileApi(): LiveData<ApiStatus<String>> = apiMediatorLiveData

    fun verifyMobileOtp(mobile: String, otp: String) {
        this.verifyMobileOtpMediator = verifyMobileOtpForgotPasswordRemote.verifyOtp(mobile, otp)
    }

    fun handleVerifyMobileOtp(): LiveData<ApiStatus<String>> = verifyMobileOtpMediator

    fun showErrorOnOtp() {
        otpDataModule.animateErrorEditText()
    }
}