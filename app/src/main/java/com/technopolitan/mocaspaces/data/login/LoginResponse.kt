package com.technopolitan.mocaspaces.data.login


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("Email")
    @Expose
    val email: String,
    @SerializedName("FirstName")
    @Expose
    val firstName: String,
    @SerializedName("Gender")
    @Expose
    val gender: String,
    @SerializedName("Id")
    @Expose
    val id: Int,
    @SerializedName("JWToken")
    @Expose
    val jWToken: String,
    @SerializedName("LastName")
    @Expose
    val lastName: String,
    @SerializedName("MobileNumber")
    @Expose
    val mobileNumber: String,
    @SerializedName("ProfilePhoto")
    @Expose
    val profilePhoto: String
)