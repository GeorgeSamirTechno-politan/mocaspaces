package com.technopolitan.mocaspaces.models.location.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.technopolitan.mocaspaces.models.shared.ImagesResponse
import com.technopolitan.mocaspaces.models.shared.NameResponse
import com.technopolitan.mocaspaces.models.shared.PriceResponse
import com.technopolitan.mocaspaces.models.shared.WorkingHourResponse

data class LocationDetailsResponse(
    @SerializedName("Id")
    @Expose
    val id: Int = 0,

    @SerializedName("LocationName")
    @Expose
    val locationName: String = "",

    @SerializedName("District")
    @Expose
    val district: NameResponse,

    @SerializedName("City")
    @Expose
    val city: NameResponse,

    @SerializedName("Country")
    @Expose
    val country: NameResponse,

    @SerializedName("About")
    @Expose
    val about: String = "",

    @SerializedName("TermsOfUse")
    @Expose
    val termsOfUse: String = "",

    @SerializedName("Address")
    @Expose
    val address: String = "",

    @SerializedName("MapAddress")
    @Expose
    val mapAddress: String = "",

    @SerializedName("Longitude")
    @Expose
    val longitude: String = "",

    @SerializedName("Latitude")
    @Expose
    val latitude: String = "",

    @SerializedName("Currency")
    @Expose
    val currency: NameResponse,

    @SerializedName("LocationImage")
    @Expose
    val image: ImagesResponse,

    @SerializedName("TakeACloserLook")
    @Expose
    val marketingList: List<TakeACloserLookResponse>,

    @SerializedName("LocationWorkingHours")
    @Expose
    val workingHourList: List<WorkingHourResponse>,

    @SerializedName("LocationAmenities")
    @Expose
    val amenityList: List<AmenityResponse>,

    @SerializedName("Prices")
    @Expose
    val price: PriceResponse,

    @SerializedName("IsFavourite")
    @Expose
    val isFavourite: Boolean = false
)