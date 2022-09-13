package com.technopolitan.mocaspaces.data.checkEmail


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CheckEmailRequest(
    @SerializedName("email")
    @Expose
    val email: String
)