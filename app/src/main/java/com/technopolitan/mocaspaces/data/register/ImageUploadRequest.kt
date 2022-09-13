package com.technopolitan.mocaspaces.data.register


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ImageUploadRequest(
    @SerializedName("albumName")
    @Expose
    val albumName: String,
    @SerializedName("image")
    @Expose
    val image: String
)