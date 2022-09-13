package com.technopolitan.mocaspaces.data.mobileOTP


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class VerifyMobileOtpRequest(
    @SerializedName("check")
    @Expose
    val check: String,
    @SerializedName("otp")
    @Expose
    val otp: String
)