package com.technopolitan.mocaspaces.data.remote

import androidx.lifecycle.MediatorLiveData
import com.technopolitan.mocaspaces.bases.BaseRemote
import com.technopolitan.mocaspaces.data.ApiStatus
import com.technopolitan.mocaspaces.data.FailedStatus
import com.technopolitan.mocaspaces.data.HeaderResponse
import com.technopolitan.mocaspaces.data.SuccessStatus
import com.technopolitan.mocaspaces.modules.NetworkModule
import com.technopolitan.mocaspaces.network.BaseUrl
import io.reactivex.Flowable
import javax.inject.Inject

class VerifyMobileOtpForgotPasswordRemote @Inject constructor(
    private val networkModel: NetworkModule
) : BaseRemote<String, String>() {

    private val verifyOtpMediator: MediatorLiveData<ApiStatus<String>> = MediatorLiveData()
    private lateinit var mobile: String
    private lateinit var otp: String

    fun verifyOtp(mobile: String, otp: String): MediatorLiveData<ApiStatus<String>> {
        this.mobile = mobile
        this.otp = otp
        return handleApi()
    }

    override fun handleResponse(it: HeaderResponse<String>): ApiStatus<String> {
        return if (it.succeeded)
            SuccessStatus(data = it.data!!, message = "")
        else FailedStatus(it.message)
    }

    override fun flowable(): Flowable<HeaderResponse<String>> {
        return networkModel.provideService(BaseUrl.sso)
            .verifyMobileOtpForForgot(mobile = mobile, otp = otp)
    }
}