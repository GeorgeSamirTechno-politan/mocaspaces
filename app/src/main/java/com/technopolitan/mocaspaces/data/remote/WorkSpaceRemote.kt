package com.technopolitan.mocaspaces.data.remote

import androidx.lifecycle.MediatorLiveData
import com.technopolitan.mocaspaces.bases.BaseRemote
import com.technopolitan.mocaspaces.data.ApiStatus
import com.technopolitan.mocaspaces.data.FailedStatus
import com.technopolitan.mocaspaces.data.HeaderResponse
import com.technopolitan.mocaspaces.data.SuccessStatus
import com.technopolitan.mocaspaces.models.location.mappers.WorkSpaceMapper
import com.technopolitan.mocaspaces.models.location.request.LocationRequest
import com.technopolitan.mocaspaces.models.location.response.WorkSpaceResponse
import com.technopolitan.mocaspaces.modules.DateTimeModule
import com.technopolitan.mocaspaces.modules.NetworkModule
import com.technopolitan.mocaspaces.network.BaseUrl
import io.reactivex.Flowable
import javax.inject.Inject

class WorkSpaceRemote @Inject constructor(
    private var networkModule: NetworkModule, private var dateTimeModule: DateTimeModule
) : BaseRemote<List<WorkSpaceMapper>, List<WorkSpaceResponse>>() {

    private var pageNumber: Int = 1
    private var pageSize: Int = 10
    fun getWorkSpace(
        pageNumber: Int = 1,
        pageSize: Int = 10
    ): MediatorLiveData<ApiStatus<List<WorkSpaceMapper>>> {
        this.pageNumber = pageNumber
        this.pageSize = pageSize
        return handleApi()
    }

    override fun flowable(): Flowable<HeaderResponse<List<WorkSpaceResponse>>> {
        return networkModule.provideServiceInterfaceWithAuth(BaseUrl.locationApi).getAllWorkSpace(
            request = LocationRequest(
                pageNumber = pageNumber,
                pageSize = 10
            )
        )
    }

    override fun handleResponse(it: HeaderResponse<List<WorkSpaceResponse>>): ApiStatus<List<WorkSpaceMapper>> {
        val list = mutableListOf<WorkSpaceMapper>()
        return if (it.succeeded) {
            it.data!!.forEach {
                list.add(WorkSpaceMapper(dateTimeModule).init(it))
            }
            SuccessStatus(message = "", list)
        } else FailedStatus(it.message)
    }
}