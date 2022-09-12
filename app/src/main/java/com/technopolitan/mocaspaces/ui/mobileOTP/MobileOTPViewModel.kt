package com.technopolitan.mocaspaces.ui.mobileOTP

import android.widget.EditText
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.google.android.gms.common.api.Api
import com.technopolitan.mocaspaces.bases.BaseViewModel
import com.technopolitan.mocaspaces.data.ApiStatus
import com.technopolitan.mocaspaces.data.remote.CheckMobileRemote
import com.technopolitan.mocaspaces.data.remote.VerifyMobileOtpRemote
import com.technopolitan.mocaspaces.data.shared.OTPDataModule
import javax.inject.Inject

class MobileOTPViewModel @Inject constructor(
    private var otpDataModule: OTPDataModule,
    private var checkMobileRemote: CheckMobileRemote,
    private var verifyMobileOtpRemote: VerifyMobileOtpRemote
) : BaseViewModel<String>() {

    private var verifyMobileOtpMediator: MediatorLiveData<ApiStatus<String>> = MediatorLiveData()

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
        resendCallBack: (entity: Boolean?) -> Unit,
        validOtpCallBack: (entity: String) -> Unit
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
            resendCallBack,
            validOtpCallBack,
            1
        )
    }

    fun checkMobile(mobile: String) {
        this.apiMutableLiveData = checkMobileRemote.verifyMobile(mobile)
    }

    fun handleCheckMobileApi(): LiveData<ApiStatus<String>> = apiMutableLiveData

    fun verifyMobileOtp(mobile: String, otp: String) {
        this.verifyMobileOtpMediator = verifyMobileOtpRemote.verifyOtp(mobile, otp)
    }

    fun handleVerifyMobileOtp(): LiveData<ApiStatus<String>> = verifyMobileOtpMediator

    fun showErrorOnOtp() {
        otpDataModule.animateErrorEditText()
    }

//    fun updateOtp(otp: String) {
//        otpDataModule.updateOtp(otp)
//    }

//    fun updatePermissionResult(it: Boolean?) {
//        otpDataModule.updatePermissionResult(it)
//    }
}