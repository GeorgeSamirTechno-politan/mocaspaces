package com.technopolitan.mocaspaces.data.register


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserDocumentsDtoRequest(
    @SerializedName("expirationDate")
    @Expose
    val expirationDate: String,
    @SerializedName("imageUpload")
    @Expose
    val imageUploadRequest: List<ImageUploadRequest>,
    @SerializedName("membersRequiredDocumentId")
    @Expose
    val membersRequiredDocumentId: Int
)