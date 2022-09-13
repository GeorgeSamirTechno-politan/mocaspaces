package com.technopolitan.mocaspaces.data.remote

import androidx.lifecycle.MediatorLiveData
import com.technopolitan.mocaspaces.bases.BaseRemote
import com.technopolitan.mocaspaces.data.ApiStatus
import com.technopolitan.mocaspaces.data.FailedStatus
import com.technopolitan.mocaspaces.data.HeaderResponse
import com.technopolitan.mocaspaces.data.SuccessStatus
import com.technopolitan.mocaspaces.data.mobileOTP.VerifyMobileOtpRequest
import com.technopolitan.mocaspaces.modules.NetworkModule
import com.technopolitan.mocaspaces.network.BaseUrl
import io.reactivex.Flowable
import javax.inject.Inject

class CheckOtpEmailRemote @Inject constructor(private var networkModule: NetworkModule) :
    BaseRemote<String, String>() {

    private lateinit var otp: String
    private lateinit var email: String

    fun verifyEmailOtp(otp: String, email: String): MediatorLiveData<ApiStatus<String>> {
        this.otp = otp
        this.email = email
        return handleApi()
    }

    override fun flowable(): Flowable<HeaderResponse<String>> {
        return networkModule.provideServiceInterfaceWithoutAuth(BaseUrl.sso).verifyEmailOtp(
            verifyMobileOtpRequest = VerifyMobileOtpRequest(
                check = email,
                otp = otp
            )
        )
    }

    override fun handleResponse(it: HeaderResponse<String>): ApiStatus<String> {
        return if (it.succeeded)
            return SuccessStatus("", it.data)
        else FailedStatus(it.message)
    }
}