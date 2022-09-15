package com.technopolitan.mocaspaces.models.meeting

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.technopolitan.mocaspaces.models.location.response.LocationImagesResponse
import com.technopolitan.mocaspaces.models.location.response.LocationWorkingHourResponse
import com.technopolitan.mocaspaces.models.shared.NameResponse

data class MeetingRoomResponse(
    @SerializedName("Id")
    @Expose
    val id : Int = 0,

    @SerializedName("VenueName")
    @Expose
    val venueName: String = "",

    @SerializedName("Location")
    @Expose
    val location: NameResponse,

    @SerializedName("Capacity")
    @Expose
    val capacity: Int = 0,

    @SerializedName("MbrPerHour")
    @Expose
    val memberPricePerHour: Double= 0.0,

    @SerializedName("NonMbrPerHour")
    @Expose
    val nonMemberPricePerHour: Double = 0.0,

    @SerializedName("City")
    @Expose
    val nameResponse: NameResponse,

    @SerializedName("Country")
    @Expose
    val country: NameResponse,

    @SerializedName("Currency")
    @Expose
    val currencyResponse: NameResponse,

    @SerializedName("District")
    @Expose
    val districtResponse: NameResponse,

    @SerializedName("LocationImages")
    @Expose
    val images: LocationImagesResponse?,

    @SerializedName("LocationWorkingHours")
    @Expose
    val locationWorkingHourResponses: List<LocationWorkingHourResponse>,
)
