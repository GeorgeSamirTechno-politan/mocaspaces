package com.technopolitan.mocaspaces.data.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MediatorLiveData
import com.technopolitan.mocaspaces.data.*
import com.technopolitan.mocaspaces.data.memberType.MemberTypeResponse
import com.technopolitan.mocaspaces.modules.NetworkModule
import com.technopolitan.mocaspaces.network.BaseUrl
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MemberTypeRemote @Inject constructor(private var networkModule: NetworkModule) {

    private val apiMediatorLiveData: MediatorLiveData<ApiStatus<List<DropDownMapper>>> =
        MediatorLiveData()

    fun getMemberType(): MediatorLiveData<ApiStatus<List<DropDownMapper>>> {
        apiMediatorLiveData.value = LoadingStatus()
        val source: LiveData<ApiStatus<List<DropDownMapper>>> =
            LiveDataReactiveStreams.fromPublisher(
                networkModule.provideServiceInterfaceWithoutAuth(BaseUrl.locationApi).memberTypes()
                    .map { handleResponse(it) }
                    .onErrorReturn { handlerError(it) }
                    .subscribeOn(Schedulers.io())
            )
        apiMediatorLiveData.addSource(source) {
            apiMediatorLiveData.value = it
            apiMediatorLiveData.removeSource(source)
        }
        return apiMediatorLiveData
    }

    private fun handlerError(it: Throwable): ApiStatus<List<DropDownMapper>> =
        ErrorStatus(it.message)

    private fun handleResponse(it: HeaderResponse<List<MemberTypeResponse>>): ApiStatus<List<DropDownMapper>> {
        return if (it.succeeded) {
            val memberTypeList = mutableListOf<DropDownMapper>()
            it.data?.forEach {
                val item = DropDownMapper(
                    it.id!!,
                    it.name!!,
                    it.icon!!,
                    selected = false,
                    description = ""
                )
                memberTypeList.add(item)
            }
            memberTypeList[0].selected = true
            SuccessStatus(data = memberTypeList.asIterable().toList(), message = "")
        } else FailedStatus(it.message)
    }
}