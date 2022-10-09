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
import com.technopolitan.mocaspaces.models.RefreshTokenRequest
import com.technopolitan.mocaspaces.models.ResetPasswordRequest
import com.technopolitan.mocaspaces.models.location.bizLounge.BizLoungeResponse
import com.technopolitan.mocaspaces.models.location.request.AddFavouriteWorkSpaceRequest
import com.technopolitan.mocaspaces.models.location.request.LocationRequest
import com.technopolitan.mocaspaces.models.location.response.LocationDetailsResponse
import com.technopolitan.mocaspaces.models.location.response.LocationFilterPaxResponse
import com.technopolitan.mocaspaces.models.location.response.SearchHintResponse
import com.technopolitan.mocaspaces.models.location.response.WorkSpaceResponse
import com.technopolitan.mocaspaces.models.meeting.MeetingRoomResponse
import com.technopolitan.mocaspaces.models.workSpace.PlanTypeResponse
import com.technopolitan.mocaspaces.utilities.Constants
import io.reactivex.Flowable
import retrofit2.http.*

interface ServiceInterface {

    @POST("v{version}/UserLogin/Login")
    fun login(
        @Path("version") version: Int = Constants.apiVersion,
        @Body loginRequest: LoginRequest
    ): Flowable<HeaderResponse<LoginResponse>>

    @POST("v{version}/Notification/RefreshNotificationToken")
    fun refreshFCMToken(
        @Path("version") version: Int = Constants.apiVersion,
        @Body refreshTokenRequest: RefreshTokenRequest
    ): Flowable<HeaderResponse<String>>

    @GET("v{version}/Country/GetAllCountriesWithoutPagination")
    fun countries(
        @Path("version") version: Int = Constants.apiVersion,
    ): Flowable<HeaderResponse<List<CountryResponse>>>

    @POST("v{version}/UserRegister/OtpMobile")
    fun otpMobile(
        @Path("version") version: Int = Constants.apiVersion,
        @Body otpMobileRequest: OtpMobileRequest
    ): Flowable<HeaderResponse<String>>

    @POST("v{version}/UserRegister/GenerateOtpForResetPassword")
    fun otpForgotPasswordMobile(
        @Path("version") version: Int = Constants.apiVersion,
        @Body otpMobileRequest: OtpMobileRequest
    ): Flowable<HeaderResponse<String>>

    @GET("v{version}/UserRegister/CheckOTPAndMobile")
    fun verifyMobileOtpForForgot(
        @Path("version") version: Int = Constants.apiVersion,
        @Query("Mobile") mobile: String,
        @Query("OTP") otp: String
    ): Flowable<HeaderResponse<String>>

    @POST("v{version}/UserRegister/ResetPassword")
    fun restPassword(
        @Path("version") version: Int = Constants.apiVersion,
        @Body request: ResetPasswordRequest
    ): Flowable<HeaderResponse<String>>

    @POST("v{version}/UserRegister/CheckOTPMobile")
    fun verifyMobileOtp(
        @Path("version") version: Int = Constants.apiVersion,
        @Body verifyMobileOtpRequest: VerifyMobileOtpRequest
    ): Flowable<HeaderResponse<String>>

    @GET("v{version}/MemberType/GetAllMemberTypesWithoutPagination")
    fun memberTypes(
        @Path("version") version: Int = Constants.apiVersion,
    ): Flowable<HeaderResponse<List<MemberTypeResponse>>>


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

    @POST("v{version}/Location/GetFilterLocationWorkspaceMobile")
    fun getAllWorkSpacePagination(
        @Path("version") version: Int = Constants.apiVersion,
        @Body request: LocationRequest
    ): Flowable<HeaderResponse<List<WorkSpaceResponse>>>

    @POST("v{version}/Meetingspace/GetFilterLocationMeetingSpacesMobile")
    fun getAllMeetingPagination(
        @Path("version") version: Int = Constants.apiVersion,
        @Body request: LocationRequest
    ): Flowable<HeaderResponse<List<MeetingRoomResponse>>>

    @POST("v{version}/EventSpace/GetFilterLocationEventSpacesMobile")
    fun getAllEventPagination(
        @Path("version") version: Int = Constants.apiVersion,
        @Body request: LocationRequest
    ): Flowable<HeaderResponse<List<MeetingRoomResponse>>>

    @POST("v{version}/Location/GetFilterLocationBizLoungeMobile")
    fun getAllBizLounge(
        @Path("version") version: Int = Constants.apiVersion,
        @Body request: LocationRequest
    ): Flowable<HeaderResponse<List<BizLoungeResponse>>>

    @GET("v{version}/Location/GetLocationDistrictMobile")
    fun getAllSearchHint(
        @Path("version") version: Int = Constants.apiVersion
    ): Flowable<HeaderResponse<List<SearchHintResponse>>>

    @POST("v{version}/Location/AddFavouriteLocation")
    fun addWorkspaceFavourite(
        @Path("version") version: Int = Constants.apiVersion,
        @Body addFavouriteWorkSpaceRequest: AddFavouriteWorkSpaceRequest
    )
            : Flowable<HeaderResponse<String>>

    @DELETE("v{version}/Location/DeleteFavouriteLocation")
    fun deleteWorkSpaceFavourite(
        @Path("version") version: Int = Constants.apiVersion,
        @Query("LocationId") locationId: Int
    ): Flowable<HeaderResponse<String>>

    @GET("v{version}/SpacePax/GetAllSpacePaxWithoutPagination")
    fun getPaxFilter(
        @Path("version") version: Int = Constants.apiVersion,
        @Query("FeatureId") featureId: Int
    ): Flowable<HeaderResponse<List<LocationFilterPaxResponse>>>

    @GET("v{version}/Workspace/GetCoWorkingWorkspaceByIdMobile")
    fun getWorkSpaceDetails(
        @Path("version") version: Int = Constants.apiVersion,
        @Query("LocationId") locationId: Int
    ): Flowable<HeaderResponse<LocationDetailsResponse>>

    @GET("v{version}/PlanTypes/GetAllPlanTypesWithRelatedData/{lobSpaceTypeId }")
    fun getPlanTypesWithRelatedData(
        @Path("version") version: Int = Constants.apiVersion,
        @Path("lobSpaceTypeId ") lopSpaceType: Int = 1
    ): Flowable<HeaderResponse<List<PlanTypeResponse>>>


}