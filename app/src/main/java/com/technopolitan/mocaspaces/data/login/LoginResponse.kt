package com.technopolitan.mocaspaces.data.login


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("Accept")
    @Expose
    val accept: Boolean? = false,
    @SerializedName("Country_Code")
    @Expose
    val countryCode: String? = "",
    @SerializedName("CreateAt")
    @Expose
    val createAt: String? = "",
    @SerializedName("Date_Of_Birth")
    @Expose
    val dateOfBirth: String? = "",
    @SerializedName("Email")
    @Expose
    val email: String? = "",
    @SerializedName("First_Name")
    @Expose
    val firstName: String? = "",
    @SerializedName("Id")
    @Expose
    val id: Int? = 0,
    @SerializedName("IsQRVerifiedByAdmin")
    @Expose
    val isQRVerifiedByAdmin: Boolean? = false,
    @SerializedName("IsQRVerifiedByClient")
    @Expose
    val isQRVerifiedByClient: Boolean? = false,
    @SerializedName("isVerified")
    @Expose
    val isVerified: Boolean? = false,
    @SerializedName("JWToken")
    @Expose
    val jWToken: String? = "",
    @SerializedName("Job_Title")
    @Expose
    val jobTitle: String? = "",
    @SerializedName("Last_Name")
    @Expose
    val lastName: String? = "",
    @SerializedName("Loc_Name")
    @Expose
    val locName: Any? = Any(),
    @SerializedName("Location_Id")
    @Expose
    val locationId: Int? = 0,
    @SerializedName("MemberShip_Category_Name")
    @Expose
    val memberShipCategoryName: String? = "",
    @SerializedName("MemberShip_Type_Name")
    @Expose
    val memberShipTypeName: String? = "",
    @SerializedName("Membership_Category_Id")
    @Expose
    val membershipCategoryId: Int? = 0,
    @SerializedName("Membership_Type_Id")
    @Expose
    val membershipTypeId: Int? = 0,
    @SerializedName("Mobile_Number")
    @Expose
    val mobileNumber: String? = "",
    @SerializedName("NotificationToken")
    @Expose
    val notificationToken: Any? = Any(),
    @SerializedName("Password")
    @Expose
    val password: String? = "",
    @SerializedName("Profile_Photo")
    @Expose
    val profilePhoto: String? = "",
    @SerializedName("StatusUser")
    @Expose
    val statusUser: Boolean? = false,
    @SerializedName("WalletBalance")
    @Expose
    val walletBalance: Double? = 0.0,
    @SerializedName("Model")
    @Expose
    val model: String? = null,
    @SerializedName("Uniquly_Identifier")
    @Expose
    val uniquelyIdentifier: String? = null,
    @SerializedName("OS")
    @Expose
    val os: String? = null,
    @SerializedName("DeviceType")
    @Expose
    val deviceType: String? = null,
    @SerializedName("Brand")
    @Expose
    val brand: String? = null,
    @SerializedName("Gender")
    @Expose
    val gender: String? = null,
    @SerializedName("Company")
    @Expose
    val company: String? = null

)