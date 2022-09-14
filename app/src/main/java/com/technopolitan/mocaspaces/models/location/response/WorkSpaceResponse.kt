package com.technopolitan.mocaspaces.models.location.response


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.technopolitan.mocaspaces.models.shared.*

data class WorkSpaceResponse(
    @SerializedName("Address")
    @Expose
    val address: String,
    @SerializedName("City")
    @Expose
    val cityResponse: CityResponse,
    @SerializedName("Country")
    @Expose
    val country: CountryResponse,
    @SerializedName("Currency")
    @Expose
    val currencyResponse: CurrencyResponse,
    @SerializedName("District")
    @Expose
    val districtResponse: DistrictResponse,
    @SerializedName("Id")
    @Expose
    val id: Int,
    @SerializedName("IsFavourite")
    @Expose
    val isFavourite: Boolean,
    @SerializedName("Latitude")
    @Expose
    val latitude: String,
    @SerializedName("LocationAmenities")
    @Expose
    val locationAmenities: List<LocationAmenityResponse>,
    @SerializedName("LocationImages")
    @Expose
    val locationImagesResponse: LocationImagesResponse,
    @SerializedName("LocationWorkingHours")
    @Expose
    val locationWorkingHourResponses: List<LocationWorkingHourResponse>,
    @SerializedName("Longitude")
    @Expose
    val longitude: String,
    @SerializedName("MapAddress")
    @Expose
    val mapAddress: String,
    @SerializedName("Name")
    @Expose
    val name: String,
    @SerializedName("Prices")
    @Expose
    val pricesResponse: PricesResponse
)