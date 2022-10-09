package com.technopolitan.mocaspaces.data.remote

import com.technopolitan.mocaspaces.bases.BaseRemote
import com.technopolitan.mocaspaces.data.ApiStatus
import com.technopolitan.mocaspaces.data.FailedStatus
import com.technopolitan.mocaspaces.data.HeaderResponse
import com.technopolitan.mocaspaces.data.SuccessStatus
import com.technopolitan.mocaspaces.models.RefreshTokenRequest
import com.technopolitan.mocaspaces.modules.NetworkModule
import com.technopolitan.mocaspaces.network.BaseUrl
import com.technopolitan.mocaspaces.utilities.SingleLiveEvent
import io.reactivex.Flowable
import javax.inject.Inject

class RefreshFCMTokenRemote @Inject constructor(private var networkModule: NetworkModule) :
    BaseRemote<String, String>() {

    private var newToken: String = ""

    fun handleRefreshNewToken(newToken: String): SingleLiveEvent<ApiStatus<String>> {
        this.newToken = newToken
        return handleApi()
    }

    override fun flowable(): Flowable<HeaderResponse<String>> {
        return networkModule.provideService(BaseUrl.sso)
            .refreshFCMToken(refreshTokenRequest = RefreshTokenRequest(newToken))
    }

    override fun handleResponse(it: HeaderResponse<String>): ApiStatus<String> {
        return if (it.succeeded)
            SuccessStatus(it.data, it.data)
        else FailedStatus(it.message)
    }
}