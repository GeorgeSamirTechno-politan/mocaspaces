package com.technopolitan.mocaspaces.models.location.request


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AddFavouriteWorkSpaceRequest(
    @SerializedName("basicUserId")
    @Expose
    val basicUserId: Int,
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("locationId")
    @Expose
    val locationId: Int
)