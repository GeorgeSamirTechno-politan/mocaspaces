package com.technopolitan.mocaspaces.data.remote

import androidx.lifecycle.MediatorLiveData
import com.technopolitan.mocaspaces.bases.BaseRemote
import com.technopolitan.mocaspaces.data.*
import com.technopolitan.mocaspaces.data.memberType.MemberTypeResponse
import com.technopolitan.mocaspaces.modules.NetworkModule
import com.technopolitan.mocaspaces.network.BaseUrl
import io.reactivex.Flowable
import javax.inject.Inject

class MemberTypeRemote @Inject constructor(private var networkModule: NetworkModule) :
    BaseRemote<List<DropDownMapper>, List<MemberTypeResponse>>() {

    private val apiMediatorLiveData: MediatorLiveData<ApiStatus<List<DropDownMapper>>> =
        MediatorLiveData()

    fun getMemberType(): MediatorLiveData<ApiStatus<List<DropDownMapper>>> = handleApi()

    override fun handleResponse(it: HeaderResponse<List<MemberTypeResponse>>): ApiStatus<List<DropDownMapper>> {
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

    override fun flowable(): Flowable<HeaderResponse<List<MemberTypeResponse>>> =
        networkModule.provideService(BaseUrl.locationApi).memberTypes()
}