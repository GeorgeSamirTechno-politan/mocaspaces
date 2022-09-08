package com.technopolitan.mocaspaces.data.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MediatorLiveData
import com.technopolitan.mocaspaces.data.*
import com.technopolitan.mocaspaces.data.country.CountryMapper
import com.technopolitan.mocaspaces.data.country.CountryResponse
import com.technopolitan.mocaspaces.modules.NetworkModule
import com.technopolitan.mocaspaces.network.BaseUrl
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CountryRemote @Inject constructor(private var networkModule: NetworkModule){

    private val verifyMobileMediator: MediatorLiveData<ApiStatus<List<CountryMapper>>> = MediatorLiveData()

    fun getCountry(): MediatorLiveData<ApiStatus<List<CountryMapper>>> {
        verifyMobileMediator.value = LoadingStatus()
        val source: LiveData<ApiStatus<List<CountryMapper>>> = LiveDataReactiveStreams.fromPublisher(
            networkModule.provideServiceInterfaceWithoutAuth(BaseUrl.locationApi).countries()
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

    private fun handlerError(it: Throwable): ApiStatus<List<CountryMapper>> = ErrorStatus(it.message)

    private fun handleResponse(it: HeaderResponse<List<CountryResponse>>): ApiStatus<List<CountryMapper>> {
        return if (it.succeeded){
            val countryMapperList = mutableListOf<CountryMapper>()
            it.data?.forEach{
                countryMapperList.add(CountryMapper().init(it))
            }
            SuccessStatus(data = countryMapperList.asIterable().toList(), message = "")
        } else FailedStatus(it.message)
    }
}