package com.technopolitan.mocaspaces.data.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MediatorLiveData
import com.technopolitan.mocaspaces.bases.BaseRemote
import com.technopolitan.mocaspaces.data.*
import com.technopolitan.mocaspaces.data.gender.GenderMapper
import com.technopolitan.mocaspaces.data.gender.GenderResponse
import com.technopolitan.mocaspaces.modules.NetworkModule
import com.technopolitan.mocaspaces.network.BaseUrl
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GenderRemote @Inject constructor(private var networkModule: NetworkModule) :
    BaseRemote<List<GenderMapper>, List<GenderResponse>>() {

    fun getAllGender(): MediatorLiveData<ApiStatus<List<GenderMapper>>> = handleApi()

    override fun handleResponse(it: HeaderResponse<List<GenderResponse>>): ApiStatus<List<GenderMapper>> {
        return if (it.succeeded) {
            val genderList = mutableListOf<GenderMapper>()
            it.data?.forEach {
                genderList.add(GenderMapper().init(it))
            }

            SuccessStatus(it.message, genderList)
        } else FailedStatus(it.message)
    }

    override fun flowable(): Flowable<HeaderResponse<List<GenderResponse>>> =
        networkModule.provideServiceInterfaceWithoutAuth(BaseUrl.sso)
            .getAllGender()
}