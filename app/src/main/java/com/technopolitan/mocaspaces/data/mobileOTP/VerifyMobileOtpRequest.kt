package com.technopolitan.mocaspaces.data.mobileOTP


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class VerifyMobileOtpRequest(
    @SerializedName("check")
    @Expose
    val mobile: String,
    @SerializedName("otp")
    @Expose
    val otp: String
)