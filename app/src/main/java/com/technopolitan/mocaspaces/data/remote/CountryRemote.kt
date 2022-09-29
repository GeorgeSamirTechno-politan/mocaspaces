package com.technopolitan.mocaspaces.data.remote

import androidx.lifecycle.MediatorLiveData
import com.technopolitan.mocaspaces.bases.BaseRemote
import com.technopolitan.mocaspaces.data.ApiStatus
import com.technopolitan.mocaspaces.data.FailedStatus
import com.technopolitan.mocaspaces.data.HeaderResponse
import com.technopolitan.mocaspaces.data.SuccessStatus
import com.technopolitan.mocaspaces.data.country.CountryMapper
import com.technopolitan.mocaspaces.data.country.CountryResponse
import com.technopolitan.mocaspaces.modules.NetworkModule
import com.technopolitan.mocaspaces.network.BaseUrl
import com.technopolitan.mocaspaces.utilities.SingleLiveEvent
import io.reactivex.Flowable
import javax.inject.Inject

class CountryRemote @Inject constructor(private var networkModule: NetworkModule) : BaseRemote<List<CountryMapper>, List<CountryResponse>>(){


    fun getCountry(): SingleLiveEvent<ApiStatus<List<CountryMapper>>> {
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
        return networkModule.provideService(BaseUrl.locationApi).countries()
    }
}