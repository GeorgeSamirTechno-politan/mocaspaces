package com.technopolitan.mocaspaces.ui.checkEmail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.technopolitan.mocaspaces.bases.BaseViewModel
import com.technopolitan.mocaspaces.data.ApiStatus
import com.technopolitan.mocaspaces.data.remote.CheckOtpEmailRemote
import com.technopolitan.mocaspaces.data.remote.SendOtpEmailRemote
import com.technopolitan.mocaspaces.data.shared.OTPDataModule
import com.technopolitan.mocaspaces.databinding.FragmentCheckEmailBinding
import javax.inject.Inject

class CheckEmailViewModel @Inject constructor(
    private var otpDataModule: OTPDataModule,
    private var sendOtpEmailRemote: SendOtpEmailRemote,
    private var checkOtpEmailRemote: CheckOtpEmailRemote,
) :
    BaseViewModel<String>() {

    private var verifyEmailOtpMediator: MediatorLiveData<ApiStatus<String>> = MediatorLiveData()


    fun init(
        binding: FragmentCheckEmailBinding, resendCallBack: (entity: Boolean) -> Unit,
        validOtpCallBack: (entity: String) -> Unit,
    ) {
        otpDataModule.initEmail(
            binding.sendOtpEmailLayout,
            binding.changeEmailTextView,
            binding.otpEmailLayout.otpWhiteFirstEditText,
            binding.otpEmailLayout.otpWhiteSecondEditText,
            binding.otpEmailLayout.otpWhiteThirdEditText,
            binding.otpEmailLayout.otpWhiteFourthEditText,
            binding.remainingEmailTextView,
            binding.resendEmailTextView,
            binding.errorEmailTextView,
            resendCallBack, validOtpCallBack
        )
    }

    fun setSendEmailOtp(email: String) {
        this.apiMediatorLiveData = sendOtpEmailRemote.otpEmail(email)
    }

    fun handleSentOtpEmail(): LiveData<ApiStatus<String>> = apiMediatorLiveData

    fun verifyEmailOtp(email: String, otp: String) {
        this.verifyEmailOtpMediator = checkOtpEmailRemote.verifyEmailOtp(otp, email)
    }

    fun handleVerifyMobileOtp(): LiveData<ApiStatus<String>> = verifyEmailOtpMediator

    fun showErrorOnOtp() {
        otpDataModule.animateErrorEditText()
    }


}