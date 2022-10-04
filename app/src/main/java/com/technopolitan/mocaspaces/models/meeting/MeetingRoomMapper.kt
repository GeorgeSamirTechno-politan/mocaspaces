package com.technopolitan.mocaspaces.models.meeting

import android.content.Context
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.models.location.mappers.PriceMapper
import com.technopolitan.mocaspaces.models.location.mappers.WorkTimeMapper
import com.technopolitan.mocaspaces.modules.DateTimeModule
import com.technopolitan.mocaspaces.modules.SpannableStringModule
import com.technopolitan.mocaspaces.network.BaseUrl

class MeetingRoomMapper constructor(
    private var dateTimeModule: DateTimeModule,
    private var context: Context,
    private var spannableStringModule: SpannableStringModule
) {

    var id: Int = 0
    var image: String = ""
    var venueName: String = ""
    var locationName: String = ""
    var address: String = ""
    var capacity: Int = 0
    var priceList: MutableList<PriceMapper> = mutableListOf()
    val workTimeMapper: WorkTimeMapper =
        WorkTimeMapper(dateTimeModule, spannableStringModule, context)

    fun init(meetingRoomResponse: MeetingRoomResponse, context: Context): MeetingRoomMapper {
        id = meetingRoomResponse.id
        venueName = "@${meetingRoomResponse.venueName}"
        if (meetingRoomResponse.districtResponse != null && meetingRoomResponse.cityResponse != null)
            address =
                "${meetingRoomResponse.districtResponse.name}, ${meetingRoomResponse.cityResponse.name}"
        if (meetingRoomResponse.location != null)
            locationName = meetingRoomResponse.location.name
        capacity = meetingRoomResponse.capacity
        if (meetingRoomResponse.currencyResponse != null)
            priceList.add(
                PriceMapper().initPriceMapper(
                    context.getString(R.string.starting_at),
                    meetingRoomResponse.nonMemberPricePerHour.toInt().toString(),
                    "/${context.getString(R.string.hour)}",
                    meetingRoomResponse.currencyResponse.name
                )
            )
        workTimeMapper.init(meetingRoomResponse.workingHourRespons)
        if (meetingRoomResponse.images != null)
            image =
                BaseUrl.baseForImage(BaseUrl.locationApi) + meetingRoomResponse.images.locationImageFilePath
        return this
    }

}