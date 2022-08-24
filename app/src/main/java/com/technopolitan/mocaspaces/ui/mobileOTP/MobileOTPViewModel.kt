package com.technopolitan.mocaspaces.ui.mobileOTP

import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.LiveData
import com.technopolitan.mocaspaces.bases.BaseViewModel
import com.technopolitan.mocaspaces.data.ApiStatus
import com.technopolitan.mocaspaces.data.remote.CheckMobileRemote
import com.technopolitan.mocaspaces.data.shared.OTPDataModule
import javax.inject.Inject

class MobileOTPViewModel @Inject constructor(
    private var otpDataModule: OTPDataModule,
    private var checkMobileRemote: CheckMobileRemote
) : BaseViewModel<String>() {

    fun initOTPDataModule(
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
        otpDataModule.init(
            mobileTextView,
            changeNumberTextView,
            otpFirstEditText,
            otpSecondEditText,
            otpThirdEditText,
            otpFourthEditText,
            remainingTextView,
            resendTextView,
            errorTextView,
            otp,
            resendCallBack,
            validOtpCallBack
        )
    }

    fun checkMobile(mobile: String) {
        this.apiMutableLiveData = checkMobileRemote.verifyMobile(mobile)
    }

    fun handleCheckMobileApi(): LiveData<ApiStatus<String>> = apiMutableLiveData

    fun updateRemaining(otp: String) {
        otpDataModule.updateRemaining(otp)
    }
}