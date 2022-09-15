package com.technopolitan.mocaspaces.data.remote

import android.content.Context
import android.location.Location
import androidx.lifecycle.MediatorLiveData
import com.technopolitan.mocaspaces.bases.BaseRemote
import com.technopolitan.mocaspaces.data.ApiStatus
import com.technopolitan.mocaspaces.data.FailedStatus
import com.technopolitan.mocaspaces.data.HeaderResponse
import com.technopolitan.mocaspaces.data.SuccessStatus
import com.technopolitan.mocaspaces.models.location.request.LocationRequest
import com.technopolitan.mocaspaces.models.meeting.MeetingRoomMapper
import com.technopolitan.mocaspaces.models.meeting.MeetingRoomResponse
import com.technopolitan.mocaspaces.modules.DateTimeModule
import com.technopolitan.mocaspaces.modules.NetworkModule
import com.technopolitan.mocaspaces.network.BaseUrl
import io.reactivex.Flowable
import javax.inject.Inject

class MeetingRoomRemote @Inject constructor(
    private var networkModule: NetworkModule,
    private var context: Context,
    private var dateTimeModule: DateTimeModule
) : BaseRemote<List<MeetingRoomMapper>, List<MeetingRoomResponse>>() {

    private var pageNumber: Int = 1
    private var pageSize: Int = 10
    private var location: Location? = null
    fun getMeetingRoom(
        pageNumber: Int = 1,
        pageSize: Int = 10,
        location: Location? = null
    ): MediatorLiveData<ApiStatus<List<MeetingRoomMapper>>> {
        this.pageNumber = pageNumber
        this.pageSize = pageSize
        return handleApi()
    }


    override fun flowable(): Flowable<HeaderResponse<List<MeetingRoomResponse>>> {
        return networkModule.provideService(BaseUrl.locationApi).getAllMeetingPagination(request = LocationRequest(
            pageNumber = pageNumber, pageSize = pageSize
        ))
    }

    override fun handleResponse(it: HeaderResponse<List<MeetingRoomResponse>>): ApiStatus<List<MeetingRoomMapper>> {
        val list = mutableListOf<MeetingRoomMapper>()
        return if (it.succeeded) {
            it.data!!.forEach {
                list.add(MeetingRoomMapper(dateTimeModule).init(it, context))
            }
            val remaining: Int = it.pageTotal!! - it.pageNumber!!
            SuccessStatus(message = "", list, remaining)
        } else FailedStatus(it.message)
    }
}