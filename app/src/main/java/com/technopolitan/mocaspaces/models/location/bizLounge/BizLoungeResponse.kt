package com.technopolitan.mocaspaces.models.location.bizLounge

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.technopolitan.mocaspaces.models.location.response.LocationImagesResponse
import com.technopolitan.mocaspaces.models.location.response.LocationWorkingHourResponse
import com.technopolitan.mocaspaces.models.shared.NameResponse

data class BizLoungeResponse(
    @SerializedName("Id")
    @Expose
    val id: Int = 0,

    @SerializedName("Name")
    @Expose
    val name: String = "",

    @SerializedName("District")
    @Expose
    val locationDistrict: NameResponse,

    @SerializedName("City")
    @Expose
    val locationCity: NameResponse,

    @SerializedName("Country")
    @Expose
    val locationCountry: NameResponse,

    @SerializedName("LocationImages")
    @Expose
    val locationImagesResponse: LocationImagesResponse?,

    @SerializedName("LocationWorkingHours")
    @Expose
    val locationWorkingHourResponse: List<LocationWorkingHourResponse>
)
