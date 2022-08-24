package com.technopolitan.mocaspaces.data.country


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CountryResponse(
    @SerializedName("CountryCode")
    @Expose
    val countryCode: String? = "",
    @SerializedName("CountryCodeString")
    @Expose
    val countryCodeString: String? = "",
    @SerializedName("CountryName")
    @Expose
    val countryName: String? = "",
    @SerializedName("Id")
    @Expose
    val id: Int? = 0,
    @SerializedName("ImageIcon")
    @Expose
    val imageIcon: String? = "",
    @SerializedName("Regex")
    @Expose
    val regex: String? = ""
)