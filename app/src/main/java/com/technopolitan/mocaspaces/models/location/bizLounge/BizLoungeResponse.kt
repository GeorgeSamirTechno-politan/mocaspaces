package com.technopolitan.mocaspaces.models.location.bizLounge

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.technopolitan.mocaspaces.models.shared.ImagesResponse
import com.technopolitan.mocaspaces.models.shared.NameResponse
import com.technopolitan.mocaspaces.models.shared.WorkingHourResponse

data class BizLoungeResponse(
    @SerializedName("Id")
    @Expose
    val id: Int = 0,

    @SerializedName("Name")
    @Expose
    val name: String = "",

    @SerializedName("District")
    @Expose
    val locationDistrict: NameResponse?,

    @SerializedName("City")
    @Expose
    val locationCity: NameResponse?,

    @SerializedName("Country")
    @Expose
    val locationCountry: NameResponse?,

    @SerializedName("LocationImages")
    @Expose
    val imagesResponse: ImagesResponse?,

    @SerializedName("LocationWorkingHours")
    @Expose
    val workingHourResponse: List<WorkingHourResponse>
)
