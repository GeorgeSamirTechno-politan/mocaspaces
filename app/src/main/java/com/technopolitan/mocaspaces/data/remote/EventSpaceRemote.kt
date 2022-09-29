package com.technopolitan.mocaspaces.data.remote

import android.content.Context
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
import com.technopolitan.mocaspaces.modules.SpannableStringModule
import com.technopolitan.mocaspaces.network.BaseUrl
import com.technopolitan.mocaspaces.utilities.SingleLiveEvent
import io.reactivex.Flowable
import javax.inject.Inject

class EventSpaceRemote @Inject constructor(
    private var networkModule: NetworkModule,
    private var context: Context,
    private var dateTimeModule: DateTimeModule,
    private var spannableStringModule: SpannableStringModule,
) : BaseRemote<List<MeetingRoomMapper?>, List<MeetingRoomResponse>>() {

    private var pageNumber: Int = 1
    private var pageSize: Int = 10
    private var type: Int? = null
    private var id: Int? = null
    private var fromPax: Int? = null
    private var toPax: Int? = null

    fun getEventSpace(
        pageNumber: Int = 1,
        pageSize: Int = 10,
        type: Int? = null,
        id: Int? = null,
        fromPax: Int?,
        toPax: Int?
    ): SingleLiveEvent<ApiStatus<List<MeetingRoomMapper?>>> {
        this.pageNumber = pageNumber
        this.pageSize = pageSize
        this.type = type
        this.id = id
        this.fromPax = fromPax
        this.toPax = toPax
        return handleApi()
    }


    override fun flowable(): Flowable<HeaderResponse<List<MeetingRoomResponse>>> {
        return networkModule.provideService(BaseUrl.locationApi).getAllEventPagination(
            request = LocationRequest(
                pageNumber = pageNumber,
                pageSize = pageSize,
                type = type,
                id = id,
                fromPax = fromPax,
                toPax = toPax
            )
        )
    }

    override fun handleResponse(it: HeaderResponse<List<MeetingRoomResponse>>): ApiStatus<List<MeetingRoomMapper?>> {
        val list = mutableListOf<MeetingRoomMapper?>()
        return if (it.succeeded) {
            if (it.data == null)
                list.add(null)
            else
                it.data.let {
                    it.forEach { item ->
                        list.add(
                            MeetingRoomMapper(
                                dateTimeModule,
                                context,
                                spannableStringModule
                            ).init(item, context)
                        )
                    }
                }
            SuccessStatus(message = "", list, getRemaining(it.pageTotal!!, pageSize, pageSize))
        } else FailedStatus(it.message)
    }
}