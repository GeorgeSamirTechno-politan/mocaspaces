package com.technopolitan.mocaspaces.data.remote

import androidx.lifecycle.MediatorLiveData
import com.technopolitan.mocaspaces.bases.BaseRemote
import com.technopolitan.mocaspaces.data.ApiStatus
import com.technopolitan.mocaspaces.data.FailedStatus
import com.technopolitan.mocaspaces.data.HeaderResponse
import com.technopolitan.mocaspaces.data.SuccessStatus
import com.technopolitan.mocaspaces.data.register.ImageUploadRequest
import com.technopolitan.mocaspaces.data.register.RegisterRequest
import com.technopolitan.mocaspaces.data.register.RegisterRequestMapper
import com.technopolitan.mocaspaces.data.register.UserDocumentsDtoRequest
import com.technopolitan.mocaspaces.modules.NetworkModule
import com.technopolitan.mocaspaces.modules.UtilityModule
import com.technopolitan.mocaspaces.network.BaseUrl
import io.reactivex.Flowable
import java.io.File
import javax.inject.Inject

class RegisterRemote @Inject constructor(
    private var networkModule: NetworkModule,
    private var utilityModule: UtilityModule
) :
    BaseRemote<String, String>() {

    private lateinit var registerRequest: RegisterRequest
    private lateinit var registerRequestMapper: RegisterRequestMapper

    fun register(requestMapper: RegisterRequestMapper): MediatorLiveData<ApiStatus<String>> {
        this.registerRequestMapper = requestMapper
        handleRegisterRequest()
        return handleApi()
    }


    override fun flowable(): Flowable<HeaderResponse<String>> =
        networkModule.provideServiceInterfaceWithoutAuth(BaseUrl.sso)
            .register(request = registerRequest)

    override fun handleResponse(it: HeaderResponse<String>): ApiStatus<String> {
        return if (it.succeeded)
            SuccessStatus("", "")
        else FailedStatus(it.message)
    }

    private fun handleRegisterRequest() {
        registerRequest = RegisterRequest(
            firstName = registerRequestMapper.fistName,
            lastName = registerRequestMapper.lastName,
            jobTitle = registerRequestMapper.jobTitle.ifEmpty { null },
            email = registerRequestMapper.email,
            countryId = registerRequestMapper.counterMapper.id,
            mobileNumber = registerRequestMapper.mobile,
            profilePhoto = registerRequestMapper.profileImageBase64,
            dateOfBirth = registerRequestMapper.birthDate!!,
            memberTypeId = registerRequestMapper.memberTypeMapper.id,
            userDocumentsDtoRequest = getUserDocumentsDtoRequest(),
            plateForm = "Android",
            genderId = registerRequestMapper.genderMapper.genderId,
            company = registerRequestMapper.company.ifEmpty { null },
            password = registerRequestMapper.password,
        )
    }

    private fun getUserDocumentsDtoRequest(): UserDocumentsDtoRequest? {
        var userDocumentsDtoRequest: UserDocumentsDtoRequest? = null
        if (registerRequestMapper.memberTypeMapper.id == 3 && registerRequestMapper.studentFrontCardPath != null) {
            val list: MutableList<ImageUploadRequest> = mutableListOf()
            list.add(
                ImageUploadRequest(
                    image = utilityModule.getImageBase64(File(registerRequestMapper.studentFrontCardPath!!)),
                    albumName = "${registerRequestMapper.fistName} front"
                )
            )
            list.add(
                ImageUploadRequest(
                    image = utilityModule.getImageBase64(File(registerRequestMapper.studentBackCardPath!!)),
                    albumName = "${registerRequestMapper.fistName} back"
                )
            )
            userDocumentsDtoRequest = UserDocumentsDtoRequest(
                expirationDate = registerRequestMapper.studentCardExpiryDate!!,
                imageUploadRequest = list,
                membersRequiredDocumentId = 0
            )
        }
        return userDocumentsDtoRequest
    }


}