package com.technopolitan.mocaspaces.data.remote

import androidx.lifecycle.MediatorLiveData
import com.technopolitan.mocaspaces.bases.BaseRemote
import com.technopolitan.mocaspaces.data.ApiStatus
import com.technopolitan.mocaspaces.data.FailedStatus
import com.technopolitan.mocaspaces.data.HeaderResponse
import com.technopolitan.mocaspaces.data.SuccessStatus
import com.technopolitan.mocaspaces.models.location.mappers.LocationPaxMapper
import com.technopolitan.mocaspaces.models.location.response.LocationFilterPaxResponse
import com.technopolitan.mocaspaces.modules.NetworkModule
import com.technopolitan.mocaspaces.network.BaseUrl
import com.technopolitan.mocaspaces.utilities.SingleLiveEvent
import io.reactivex.Flowable
import javax.inject.Inject

class PaxFilterRemote @Inject constructor(private var networkModule: NetworkModule) :
    BaseRemote<List<LocationPaxMapper>, List<LocationFilterPaxResponse>>() {

    private var featureId: Int = 2

    fun getPaxFilter(featureId: Int): SingleLiveEvent<ApiStatus<List<LocationPaxMapper>>> {
        this.featureId = featureId
        return handleApi()
    }

    override fun flowable(): Flowable<HeaderResponse<List<LocationFilterPaxResponse>>> {
        return networkModule.provideService(BaseUrl.locationApi).getPaxFilter(featureId = featureId)
    }

    override fun handleResponse(it: HeaderResponse<List<LocationFilterPaxResponse>>): ApiStatus<List<LocationPaxMapper>> {
        return if (it.succeeded) {
            val list: MutableList<LocationPaxMapper> = mutableListOf()
            it.data?.let { data ->
                data.forEach { item ->
                    list.add(LocationPaxMapper(item))
                }
            }
            SuccessStatus(it.message, list)
        } else FailedStatus(it.message)
    }
}