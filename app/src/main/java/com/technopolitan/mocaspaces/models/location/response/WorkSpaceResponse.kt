package com.technopolitan.mocaspaces.models.location.response


import com.google.gson.annotations.SerializedName
import com.technopolitan.mocaspaces.models.shared.ImagesResponse
import com.technopolitan.mocaspaces.models.shared.NameResponse
import com.technopolitan.mocaspaces.models.shared.PriceResponse
import com.technopolitan.mocaspaces.models.shared.WorkingHourResponse

data class WorkSpaceResponse(
    @SerializedName("Address")
    val address: String,
    @SerializedName("City")
    val cityResponse: NameResponse?,
    @SerializedName("Country")
    val country: NameResponse?,
    @SerializedName("Currency")
    val currencyResponse: NameResponse?,
    @SerializedName("District")
    val districtResponse: NameResponse?,
    @SerializedName("Id")
    val id: Int,
    @SerializedName("IsFavourite")
    val isFavourite: Boolean,
    @SerializedName("Latitude")
    val latitude: String,
    @SerializedName("LocationAmenities")
    val locationAmenities: List<AmenityResponse>,
    @SerializedName("LocationImages")
    val imagesResponse: ImagesResponse?,
    @SerializedName("LocationWorkingHours")
    val workingHourResponse: List<WorkingHourResponse>,
    @SerializedName("Longitude")
    val longitude: String,
    @SerializedName("MapAddress")
    val mapAddress: String,
    @SerializedName("Name")
    val name: String,
    @SerializedName("Prices")
    val priceResponse: PriceResponse
)