package com.technopolitan.mocaspaces.data.remote

import androidx.lifecycle.MediatorLiveData
import com.technopolitan.mocaspaces.bases.BaseRemote
import com.technopolitan.mocaspaces.data.ApiStatus
import com.technopolitan.mocaspaces.data.FailedStatus
import com.technopolitan.mocaspaces.data.HeaderResponse
import com.technopolitan.mocaspaces.data.SuccessStatus
import com.technopolitan.mocaspaces.data.mobileOTP.OtpMobileRequest
import com.technopolitan.mocaspaces.modules.NetworkModule
import com.technopolitan.mocaspaces.network.BaseUrl
import com.technopolitan.mocaspaces.utilities.SingleLiveEvent
import io.reactivex.Flowable
import javax.inject.Inject

class SendOtpForgotPasswordMobile@Inject constructor(
    private val networkModel: NetworkModule
) : BaseRemote<String, String>() {

    private lateinit var mobile: String

    fun verifyMobile(mobile: String): SingleLiveEvent<ApiStatus<String>> {
        this.mobile = mobile
        return handleApi()
    }

    override fun handleResponse(it: HeaderResponse<String>): ApiStatus<String> {
        return if (it.succeeded)
            SuccessStatus(data = it.data!!, message = "")
        else FailedStatus(it.message)
    }

    override fun flowable(): Flowable<HeaderResponse<String>> {
        return  networkModel.provideService(BaseUrl.sso)
            .otpForgotPasswordMobile(otpMobileRequest = OtpMobileRequest(mobile))
    }


}