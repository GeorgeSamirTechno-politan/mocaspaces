package com.technopolitan.mocaspaces.models.meeting

import android.content.Context
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.models.location.mappers.PricePagerMapper
import com.technopolitan.mocaspaces.models.location.mappers.WorkTimeMapper
import com.technopolitan.mocaspaces.modules.DateTimeModule
import com.technopolitan.mocaspaces.network.BaseUrl

class MeetingRoomMapper constructor(private var dateTimeModule: DateTimeModule){

    var id : Int = 0
    var image: String= ""
    var venueName: String = ""
    var locationName: String = ""
    var address: String = ""
    var capacity: Int = 0
    var priceList: MutableList<PricePagerMapper> = mutableListOf()
    val workTimeMapper: WorkTimeMapper = WorkTimeMapper(dateTimeModule)

    fun init(meetingRoomResponse: MeetingRoomResponse, context: Context): MeetingRoomMapper{
        id = meetingRoomResponse.id
        venueName = "@${meetingRoomResponse.venueName}"
        address = "${meetingRoomResponse.districtResponse.name}, ${meetingRoomResponse.nameResponse.name}"
        locationName = meetingRoomResponse.location.name
        capacity = meetingRoomResponse.capacity
        priceList.add(PricePagerMapper(context.getString(R.string.starting_at), meetingRoomResponse.nonMemberPricePerHour.toInt().toString(),
        "/${context.getString(R.string.hour)}", meetingRoomResponse.currencyResponse.name))
        workTimeMapper.init(meetingRoomResponse.locationWorkingHourResponses)
        if( meetingRoomResponse.images != null)
        image = BaseUrl.baseForImage(BaseUrl.locationApi) + meetingRoomResponse.images.locationImageFilePath
        return this
    }

}