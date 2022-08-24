package com.technopolitan.mocaspaces.data.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MediatorLiveData
import com.technopolitan.mocaspaces.data.*
import com.technopolitan.mocaspaces.modules.NetworkModule
import com.technopolitan.mocaspaces.network.BaseUrl
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CheckMobileRemote @Inject constructor(
    private val networkModel: NetworkModule
) {

    private val verifyMobileMediator: MediatorLiveData<ApiStatus<String>> = MediatorLiveData()

    fun verifyMobile(mobile: String): MediatorLiveData<ApiStatus<String>> {
        verifyMobileMediator.value = LoadingStatus()
        val source: LiveData<ApiStatus<String>> = LiveDataReactiveStreams.fromPublisher(
            networkModel.provideServiceInterfaceWithoutAuth(BaseUrl.emptyApi).verifyMobile(mobile = mobile)
                .map { handleResponse(it) }
                .onErrorReturn { handlerError(it) }
                .subscribeOn(Schedulers.io())
        )
        verifyMobileMediator.addSource(source) {
            verifyMobileMediator.value = it
            verifyMobileMediator.removeSource(source)
        }
        return verifyMobileMediator
    }

    private fun handlerError(it: Throwable): ApiStatus<String> = ErrorStatus(it.message)

    private fun handleResponse(it: HeaderResponse<String>): ApiStatus<String> {
        return if (it.succeeded)
            SuccessStatus(data = it.data!!, message = "")
        else FailedStatus(it.message)
    }

}