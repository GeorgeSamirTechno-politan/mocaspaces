package com.technopolitan.mocaspaces.data.remote

import androidx.lifecycle.MediatorLiveData
import com.technopolitan.mocaspaces.bases.BaseRemote
import com.technopolitan.mocaspaces.data.ApiStatus
import com.technopolitan.mocaspaces.data.FailedStatus
import com.technopolitan.mocaspaces.data.HeaderResponse
import com.technopolitan.mocaspaces.data.SuccessStatus
import com.technopolitan.mocaspaces.models.location.mappers.SearchHintMapper
import com.technopolitan.mocaspaces.models.location.response.SearchHintResponse
import com.technopolitan.mocaspaces.modules.NetworkModule
import com.technopolitan.mocaspaces.network.BaseUrl
import io.reactivex.Flowable
import javax.inject.Inject

class SearchHintRemote @Inject constructor(private var networkModule: NetworkModule) :
    BaseRemote<List<SearchHintMapper>, List<SearchHintResponse>>() {

    fun getAllSearchHint(): MediatorLiveData<ApiStatus<List<SearchHintMapper>>> = handleApi()

    override fun flowable(): Flowable<HeaderResponse<List<SearchHintResponse>>> {
        return networkModule.provideService(BaseUrl.locationApi).getAllSearchHint()
    }

    override fun handleResponse(it: HeaderResponse<List<SearchHintResponse>>): ApiStatus<List<SearchHintMapper>> {
        return if (it.succeeded) {
            val list = mutableListOf<SearchHintMapper>()
            it.data?.let {
                it.forEach { item ->
                    list.add(SearchHintMapper().init(item))
                }
            }
            SuccessStatus(it.message, list)
        } else FailedStatus(it.message)
    }
}