package com.technopolitan.mocaspaces.data.remote

import androidx.lifecycle.MediatorLiveData
import com.technopolitan.mocaspaces.bases.BaseRemote
import com.technopolitan.mocaspaces.data.ApiStatus
import com.technopolitan.mocaspaces.data.FailedStatus
import com.technopolitan.mocaspaces.data.HeaderResponse
import com.technopolitan.mocaspaces.data.SuccessStatus
import com.technopolitan.mocaspaces.data.checkEmail.CheckEmailRequest
import com.technopolitan.mocaspaces.modules.NetworkModule
import com.technopolitan.mocaspaces.network.BaseUrl
import com.technopolitan.mocaspaces.utilities.SingleLiveEvent
import io.reactivex.Flowable
import javax.inject.Inject

class SendOtpEmailRemote @Inject constructor(private var networkModule: NetworkModule) :
    BaseRemote<String, String>() {

    private lateinit var email: String

    fun otpEmail(email: String): SingleLiveEvent<ApiStatus<String>> {
        this.email = email
        return handleApi()
    }

    override fun flowable(): Flowable<HeaderResponse<String>> {
        return networkModule.provideService(BaseUrl.sso)
            .otpEmail(request = CheckEmailRequest(email))
    }

    override fun handleResponse(it: HeaderResponse<String>): ApiStatus<String> {
        return if (it.succeeded)
            SuccessStatus("", it.data)
        else FailedStatus(it.message)
    }
}