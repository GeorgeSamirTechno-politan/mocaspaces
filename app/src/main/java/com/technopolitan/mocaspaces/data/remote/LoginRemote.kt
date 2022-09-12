package com.technopolitan.mocaspaces.data.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MediatorLiveData
import com.technopolitan.mocaspaces.SharedPrefKey
import com.technopolitan.mocaspaces.bases.BaseRemote
import com.technopolitan.mocaspaces.data.*
import com.technopolitan.mocaspaces.data.login.LoginMapper
import com.technopolitan.mocaspaces.data.login.LoginResponse
import com.technopolitan.mocaspaces.modules.NetworkModule
import com.technopolitan.mocaspaces.modules.SharedPrefModule
import com.technopolitan.mocaspaces.network.BaseUrl
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class LoginRemote @Inject constructor(
    private val networkModel: NetworkModule,
    private var sharedPrefModule: SharedPrefModule
) : BaseRemote<LoginMapper, LoginResponse>(){

    private val loginMediator: MediatorLiveData<ApiStatus<LoginMapper>> = MediatorLiveData()
    private lateinit var email: String
    private lateinit var password: String

    fun login(email: String, password: String): MediatorLiveData<ApiStatus<LoginMapper>> {
        this.email = email
        this.password = password
        return handleApi()
    }

    override fun handleResponse(it: HeaderResponse<LoginResponse>): ApiStatus<LoginMapper> {
        return if (it.succeeded)
            SuccessStatus(it.message, LoginMapper().init(it.data!!, firstTimeLogin()))
        else FailedStatus(it.message)
    }

    private fun firstTimeLogin(): Boolean {
        return if (sharedPrefModule.contain(SharedPrefKey.FirstTimeLogin.name))
            sharedPrefModule.getBooleanFromShared(SharedPrefKey.FirstTimeLogin.name)
        else true
    }

    override fun flowable(): Flowable<HeaderResponse<LoginResponse>> {
        return networkModel.provideServiceInterfaceWithoutAuth(BaseUrl.emptyApi)
            .login(email = email, password = password)
    }
}