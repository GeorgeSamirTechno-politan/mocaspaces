package com.technopolitan.mocaspaces.network


import com.technopolitan.mocaspaces.data.HeaderResponse
import com.technopolitan.mocaspaces.data.login.LoginResponse
import com.technopolitan.mocaspaces.utilities.Constants
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ServiceInterface {

    @GET("v{version}/Lounge_Client/Login")
    fun login(
        @Path("version") version: Int = Constants.apiVersion,
        @Query("Email") email: String,
        @Query("Password") password: String,
        @Query("Model") model: String = Constants.model,
        @Query("Uniquly_Identifier") uniquelyIdentifier: String = Constants.firebaseInstallationId,
        @Query("OS") os: String = Constants.OS,
        @Query("DeviceType") deviceTypes: String = "Android",
        @Query("Brand") brand: String = Constants.brand,
        @Query("NotificationToken") notificationToken: String = Constants.notificationToken
    ): Flowable<HeaderResponse<LoginResponse>>


    @GET("v{version}/Lounge_Client/VerifySendSMS")
    fun verifyMobile(
        @Path("version") version: Int = Constants.apiVersion,
        @Query("Mobile") mobile: String
    ): Flowable<HeaderResponse<String>>
}