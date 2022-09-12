package com.technopolitan.mocaspaces.data.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MediatorLiveData
import com.technopolitan.mocaspaces.bases.BaseRemote
import com.technopolitan.mocaspaces.data.*
import com.technopolitan.mocaspaces.data.country.CountryMapper
import com.technopolitan.mocaspaces.data.country.CountryResponse
import com.technopolitan.mocaspaces.modules.NetworkModule
import com.technopolitan.mocaspaces.network.BaseUrl
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CountryRemote @Inject constructor(private var networkModule: NetworkModule) : BaseRemote<List<CountryMapper>, List<CountryResponse>>(){


    fun getCountry(): MediatorLiveData<ApiStatus<List<CountryMapper>>> {
       return handleApi()
    }

    override fun handleResponse(it: HeaderResponse<List<CountryResponse>>): ApiStatus<List<CountryMapper>> {
        return if (it.succeeded){
            val countryMapperList = mutableListOf<CountryMapper>()
            it.data?.forEach{
                countryMapperList.add(CountryMapper().init(it))
            }
            SuccessStatus(data = countryMapperList.asIterable().toList(), message = "")
        } else FailedStatus(it.message)
    }

    override fun flowable(): Flowable<HeaderResponse<List<CountryResponse>>> {
        return networkModule.provideServiceInterfaceWithoutAuth(BaseUrl.locationApi).countries()
    }
}