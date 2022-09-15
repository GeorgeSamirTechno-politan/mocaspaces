package com.technopolitan.mocaspaces.models


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ResetPasswordRequest(
    @SerializedName("mobile")
    @Expose
    val mobile: String,
    @SerializedName("newpassword")
    @Expose
    val newpassword: String
)