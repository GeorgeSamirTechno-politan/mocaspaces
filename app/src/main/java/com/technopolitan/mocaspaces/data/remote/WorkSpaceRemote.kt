package com.technopolitan.mocaspaces.data.remote

import android.content.Context
import android.location.Location
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
import com.technopolitan.mocaspaces.modules.SpannableStringModule
import com.technopolitan.mocaspaces.network.BaseUrl
import com.technopolitan.mocaspaces.utilities.SingleLiveEvent
import io.reactivex.Flowable
import javax.inject.Inject

class WorkSpaceRemote @Inject constructor(
    private var networkModule: NetworkModule, private var dateTimeModule: DateTimeModule,
    private var context: Context,
    private var spannableStringModule: SpannableStringModule
) : BaseRemote<List<WorkSpaceMapper?>, List<WorkSpaceResponse>>() {

    private var pageNumber: Int = 1
    private var pageSize: Int = 10
    private var type: Int? = null
    private var id: Int? = null
    private var location: Location? = null
    fun getWorkSpace(
        pageNumber: Int = 1,
        pageSize: Int = 10,
        type: Int? = null,
        id: Int? = null,
        location: Location? = null
    ): SingleLiveEvent<ApiStatus<List<WorkSpaceMapper?>>> {
        this.pageNumber = pageNumber
        this.pageSize = pageSize
        this.id = id
        this.type = type
        this.location = location
        return handleApi()
    }


    override fun flowable(): Flowable<HeaderResponse<List<WorkSpaceResponse>>> {
        return networkModule.provideService(BaseUrl.locationApi).getAllWorkSpacePagination(
            request = LocationRequest(
                pageNumber = pageNumber,
                pageSize = pageSize,
                type = type,
                id = id
            )
        )
    }

    override fun handleResponse(it: HeaderResponse<List<WorkSpaceResponse>>): ApiStatus<List<WorkSpaceMapper?>> {
        val list = mutableListOf<WorkSpaceMapper?>()
        return if (it.succeeded) {
            if (it.data == null)
                list.add(null)
            else
                it.data.let {
                    it.forEach { item ->
                        list.add(
                            WorkSpaceMapper(
                                dateTimeModule,
                                context,
                                spannableStringModule
                            ).init(item, location, context)
                        )
                    }
                }

            SuccessStatus(message = "", list, getRemaining(it.pageTotal!!, pageSize, pageSize))
        } else FailedStatus(it.message)
    }
}