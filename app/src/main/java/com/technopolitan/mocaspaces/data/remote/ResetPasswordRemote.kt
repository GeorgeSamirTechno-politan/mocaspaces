package com.technopolitan.mocaspaces.data.remote

import androidx.lifecycle.MediatorLiveData
import com.technopolitan.mocaspaces.bases.BaseRemote
import com.technopolitan.mocaspaces.data.ApiStatus
import com.technopolitan.mocaspaces.data.FailedStatus
import com.technopolitan.mocaspaces.data.HeaderResponse
import com.technopolitan.mocaspaces.data.SuccessStatus
import com.technopolitan.mocaspaces.models.ResetPasswordRequest
import com.technopolitan.mocaspaces.modules.NetworkModule
import com.technopolitan.mocaspaces.network.BaseUrl
import com.technopolitan.mocaspaces.utilities.SingleLiveEvent
import io.reactivex.Flowable
import javax.inject.Inject

class ResetPasswordRemote @Inject constructor(private var networkModule: NetworkModule) : BaseRemote<String, String>() {

    private lateinit var mobile:String
    private lateinit var newPassword: String

    fun getResetPassword(mobile:String, newPassword: String): SingleLiveEvent<ApiStatus<String>> {
        this.mobile = mobile
        this.newPassword = newPassword
        return handleApi()
    }
    override fun flowable(): Flowable<HeaderResponse<String>> {
        return networkModule.provideService(BaseUrl.sso).restPassword(request = ResetPasswordRequest(mobile, newPassword))
    }

    override fun handleResponse(it: HeaderResponse<String>): ApiStatus<String> {
        return if(it.succeeded){
            SuccessStatus(it.message, it.data)
        }else FailedStatus(it.message)
    }
}