package com.technopolitan.mocaspaces.data.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MediatorLiveData
import com.technopolitan.mocaspaces.SharedPrefKey
import com.technopolitan.mocaspaces.data.*
import com.technopolitan.mocaspaces.data.login.LoginMapper
import com.technopolitan.mocaspaces.data.login.LoginResponse
import com.technopolitan.mocaspaces.modules.NetworkModule
import com.technopolitan.mocaspaces.modules.SharedPrefModule
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class LoginRemote @Inject constructor(
    private val networkModel: NetworkModule,
    private var sharedPrefModule: SharedPrefModule
) {

    private val loginMediator: MediatorLiveData<ApiStatus<LoginMapper>> = MediatorLiveData()

    fun login(email: String, password: String): MediatorLiveData<ApiStatus<LoginMapper>> {
        loginMediator.value = LoadingStatus()
        val source: LiveData<ApiStatus<LoginMapper>> = LiveDataReactiveStreams.fromPublisher(
            networkModel.provideServiceInterfaceWithoutAuth()
                .login(email = email, password = password)
                .map { handleResponse(it) }.onErrorReturn {
                    handleError(it)
                }.subscribeOn(Schedulers.io())
        )
        loginMediator.addSource(source) {
            loginMediator.value = it
            loginMediator.removeSource(source)
        }
        return loginMediator
    }

    private fun handleError(it: Throwable): ApiStatus<LoginMapper> = ErrorStatus(it.message)

    private fun handleResponse(it: HeaderResponse<LoginResponse>): ApiStatus<LoginMapper> {
        return if (it.succeeded)
            SuccessStatus(it.message, LoginMapper().init(it.data!!, firstTimeLogin()))
        else FailedStatus(it.message)
    }

    private fun firstTimeLogin(): Boolean {
        return if (sharedPrefModule.contain(SharedPrefKey.FirstTimeLogin.name))
            sharedPrefModule.getBooleanFromShared(SharedPrefKey.FirstTimeLogin.name)
        else true
    }
}