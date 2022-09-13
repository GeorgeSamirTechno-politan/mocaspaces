package com.technopolitan.mocaspaces.network


import com.technopolitan.mocaspaces.data.HeaderResponse
import com.technopolitan.mocaspaces.data.checkEmail.CheckEmailRequest
import com.technopolitan.mocaspaces.data.country.CountryResponse
import com.technopolitan.mocaspaces.data.gender.GenderResponse
import com.technopolitan.mocaspaces.data.login.LoginRequest
import com.technopolitan.mocaspaces.data.login.LoginResponse
import com.technopolitan.mocaspaces.data.memberType.MemberTypeResponse
import com.technopolitan.mocaspaces.data.mobileOTP.OtpMobileRequest
import com.technopolitan.mocaspaces.data.mobileOTP.VerifyMobileOtpRequest
import com.technopolitan.mocaspaces.data.register.RegisterRequest
import com.technopolitan.mocaspaces.utilities.Constants
import io.reactivex.Flowable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ServiceInterface {

    @GET("v{version}/UserLogin/Login")
    fun login(
        @Path("version") version: Int = Constants.apiVersion,
        @Body loginRequest: LoginRequest
    ): Flowable<HeaderResponse<LoginResponse>>


    @GET("v{version}/Country/GetAllCountriesWithoutPagination")
    fun countries(
        @Path("version") version: Int = Constants.apiVersion,
    ): Flowable<HeaderResponse<List<CountryResponse>>>

    @POST("v{version}/UserRegister/OtpMobile")
    fun otpMobile(
        @Path("version") version: Int = Constants.apiVersion,
        @Body otpMobileRequest: OtpMobileRequest
    ): Flowable<HeaderResponse<String>>

    @GET("v{version}/MemberType/GetAllMemberTypesWithoutPagination")
    fun memberTypes(
        @Path("version") version: Int = Constants.apiVersion,
    ): Flowable<HeaderResponse<List<MemberTypeResponse>>>

    @POST("v{version}/UserRegister/CheckOTPMobile")
    fun verifyMobileOtp(
        @Path("version") version: Int = Constants.apiVersion,
        @Body verifyMobileOtpRequest: VerifyMobileOtpRequest
    ): Flowable<HeaderResponse<String>>

    @GET("v{version}/UserRegister/GetAllGenders")
    fun getAllGender(@Path("version") version: Int = Constants.apiVersion)
            : Flowable<HeaderResponse<List<GenderResponse>>>

    @POST("v{version}/UserRegister/UserRegister")
    fun register(
        @Path("version") version: Int = Constants.apiVersion,
        @Body request: RegisterRequest
    ): Flowable<HeaderResponse<String>>

    @POST("v{version}/UserRegister/OtpEmail")
    fun otpEmail(
        @Path("version") version: Int = Constants.apiVersion,
        @Body request: CheckEmailRequest
    ): Flowable<HeaderResponse<String>>

    @POST("v{version}/UserRegister/CheckEmail")
    fun verifyEmailOtp(
        @Path("version") version: Int = Constants.apiVersion,
        @Body verifyMobileOtpRequest: VerifyMobileOtpRequest
    ): Flowable<HeaderResponse<String>>
}