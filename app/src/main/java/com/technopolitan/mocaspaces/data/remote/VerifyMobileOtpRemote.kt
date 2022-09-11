package com.technopolitan.mocaspaces.data.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MediatorLiveData
import com.technopolitan.mocaspaces.data.*
import com.technopolitan.mocaspaces.data.mobileOTP.OtpMobileRequest
import com.technopolitan.mocaspaces.data.mobileOTP.VerifyMobileOtpRequest
import com.technopolitan.mocaspaces.modules.NetworkModule
import com.technopolitan.mocaspaces.network.BaseUrl
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class VerifyMobileOtpRemote @Inject constructor(
    private val networkModel: NetworkModule
) {

    private val verifyOtpMediator: MediatorLiveData<ApiStatus<String>> = MediatorLiveData()

    fun verifyOtp(mobile: String, otp: String): MediatorLiveData<ApiStatus<String>> {
        verifyOtpMediator.value = LoadingStatus()
        val source: LiveData<ApiStatus<String>> = LiveDataReactiveStreams.fromPublisher(
            networkModel.provideServiceInterfaceWithoutAuth(BaseUrl.sso)
                .verifyMobileOtp(verifyMobileOtpRequest = VerifyMobileOtpRequest(mobile, otp))
                .map { handleResponse(it) }
                .onErrorReturn { handlerError(it) }
                .subscribeOn(Schedulers.io())
        )
        verifyOtpMediator.addSource(source) {
            verifyOtpMediator.value = it
            verifyOtpMediator.removeSource(source)
        }
        return verifyOtpMediator
    }

    private fun handlerError(it: Throwable): ApiStatus<String> = ErrorStatus(it.message)

    private fun handleResponse(it: HeaderResponse<String>): ApiStatus<String> {
        return if (it.succeeded)
            SuccessStatus(data = it.data!!, message = "")
        else FailedStatus(it.message)
    }
}