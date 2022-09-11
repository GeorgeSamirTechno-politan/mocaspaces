package com.technopolitan.mocaspaces.data.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MediatorLiveData
import com.technopolitan.mocaspaces.data.*
import com.technopolitan.mocaspaces.data.gender.GenderMapper
import com.technopolitan.mocaspaces.data.gender.GenderResponse
import com.technopolitan.mocaspaces.modules.NetworkModule
import com.technopolitan.mocaspaces.network.BaseUrl
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GenderRemote @Inject constructor(private var networkModule: NetworkModule) {

    private val genderApiMediator: MediatorLiveData<ApiStatus<List<GenderMapper>>> = MediatorLiveData()

    fun getAllGender(): MediatorLiveData<ApiStatus<List<GenderMapper>>>{
        genderApiMediator.value = LoadingStatus()
        val source: LiveData<ApiStatus<List<GenderMapper>>> = LiveDataReactiveStreams.fromPublisher(
            networkModule.provideServiceInterfaceWithoutAuth(BaseUrl.emptyApi)
                .getAllGender()
                .map { handleResponse(it) }.onErrorReturn {
                    handleError(it)
                }.subscribeOn(Schedulers.io())
        )
        genderApiMediator.addSource(source) {
            genderApiMediator.value = it
            genderApiMediator.removeSource(source)
        }
        return genderApiMediator
    }

    private fun handleError(it: Throwable): ApiStatus<List<GenderMapper>> = ErrorStatus(it.message)

    private fun handleResponse(it: HeaderResponse<List<GenderResponse>>): ApiStatus<List<GenderMapper>> {
        return if (it.succeeded){
            val genderList = mutableListOf<GenderMapper>()
            it.data?.forEach{
                genderList.add(GenderMapper().init(it))
            }

            SuccessStatus(it.message, genderList)
        }

        else FailedStatus(it.message)
    }
}