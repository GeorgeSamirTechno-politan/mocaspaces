package com.technopolitan.mocaspaces.data.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MediatorLiveData
import com.technopolitan.mocaspaces.bases.BaseRemote
import com.technopolitan.mocaspaces.data.*
import com.technopolitan.mocaspaces.data.mobileOTP.OtpMobileRequest
import com.technopolitan.mocaspaces.modules.NetworkModule
import com.technopolitan.mocaspaces.network.BaseUrl
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CheckMobileRemote @Inject constructor(
    private val networkModel: NetworkModule
) : BaseRemote<String, String>() {

    private lateinit var mobile: String

    fun verifyMobile(mobile: String): MediatorLiveData<ApiStatus<String>> {
        this.mobile = mobile
        return handleApi()
    }

    override fun handleResponse(it: HeaderResponse<String>): ApiStatus<String> {
        return if (it.succeeded)
            SuccessStatus(data = it.data!!, message = "")
        else FailedStatus(it.message)
    }

    override fun flowable(): Flowable<HeaderResponse<String>> {
        return  networkModel.provideServiceInterfaceWithoutAuth(BaseUrl.sso)
            .otpMobile(otpMobileRequest = OtpMobileRequest(mobile))
    }


}