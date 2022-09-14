package com.technopolitan.mocaspaces.data.remote

import androidx.lifecycle.MediatorLiveData
import com.technopolitan.mocaspaces.SharedPrefKey
import com.technopolitan.mocaspaces.bases.BaseRemote
import com.technopolitan.mocaspaces.data.ApiStatus
import com.technopolitan.mocaspaces.data.FailedStatus
import com.technopolitan.mocaspaces.data.HeaderResponse
import com.technopolitan.mocaspaces.data.SuccessStatus
import com.technopolitan.mocaspaces.data.login.LoginMapper
import com.technopolitan.mocaspaces.data.login.LoginRequest
import com.technopolitan.mocaspaces.data.login.LoginResponse
import com.technopolitan.mocaspaces.modules.NetworkModule
import com.technopolitan.mocaspaces.modules.SharedPrefModule
import com.technopolitan.mocaspaces.network.BaseUrl
import io.reactivex.Flowable
import javax.inject.Inject


class LoginRemote @Inject constructor(
    private val networkModel: NetworkModule,
    private var sharedPrefModule: SharedPrefModule
) : BaseRemote<LoginMapper, LoginResponse>(){

    private lateinit var email: String
    private lateinit var password: String

    fun login(email: String, password: String): MediatorLiveData<ApiStatus<LoginMapper>> {
        this.email = email
        this.password = password
        return handleApi()
    }

    override fun handleResponse(it: HeaderResponse<LoginResponse>): ApiStatus<LoginMapper> {
        return if (it.succeeded) {
            val loginMapper = LoginMapper().init(it.data!!, firstTimeLogin())
            setDataToShared(loginMapper)
            SuccessStatus(it.message, loginMapper)
        }
        else FailedStatus(it.message)
    }

    private fun setDataToShared(it: LoginMapper){
        sharedPrefModule.setStringToShared(
            SharedPrefKey.UserEmail.name,
            email
        )
        sharedPrefModule.setStringToShared(
            SharedPrefKey.UserPassword.name,
            password
        )
        sharedPrefModule.setStringToShared(
            SharedPrefKey.ProfileUrl.name,
            it.profileUrl
        )
        sharedPrefModule.setStringToShared(
            SharedPrefKey.FirstName.name,
            it.firstName
        )
        sharedPrefModule.setStringToShared(
            SharedPrefKey.LastName.name,
            it.lastName
        )
        sharedPrefModule.setStringToShared(
            SharedPrefKey.UserMobile.name,
            it.mobile
        )
        sharedPrefModule.setStringToShared(
            SharedPrefKey.BearerToken.name,
            it.userToken
        )
    }

    private fun firstTimeLogin(): Boolean {
        return if (sharedPrefModule.contain(SharedPrefKey.FirstTimeLogin.name))
            sharedPrefModule.getBooleanFromShared(SharedPrefKey.FirstTimeLogin.name)
        else true
    }

    override fun flowable(): Flowable<HeaderResponse<LoginResponse>> {
        return networkModel.provideServiceInterfaceWithoutAuth(BaseUrl.sso)
            .login(loginRequest = LoginRequest(email, password))
    }
}