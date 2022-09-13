package com.technopolitan.mocaspaces.data.register


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("company")
    @Expose
    val company: String,
    @SerializedName("countryId")
    @Expose
    val countryId: Int,
    @SerializedName("dateOfBirth")
    @Expose
    val dateOfBirth: String,
    @SerializedName("email")
    @Expose
    val email: String,
    @SerializedName("firstName")
    @Expose
    val firstName: String,
    @SerializedName("genderId")
    @Expose
    val genderId: Int,
    @SerializedName("jobTitle")
    @Expose
    val jobTitle: String,
    @SerializedName("lastName")
    @Expose
    val lastName: String,
    @SerializedName("memberTypeId")
    @Expose
    val memberTypeId: Int,
    @SerializedName("mobileNumber")
    @Expose
    val mobileNumber: String,
    @SerializedName("password")
    @Expose
    val password: String,
    @SerializedName("plateForm")
    @Expose
    val plateForm: String,
    @SerializedName("profilePhoto")
    @Expose
    val profilePhoto: String,
    @SerializedName("userDocumentsDto")
    @Expose
    var userDocumentsDtoRequest: UserDocumentsDtoRequest? = null
)