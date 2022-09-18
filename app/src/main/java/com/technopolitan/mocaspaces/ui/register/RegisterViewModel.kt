package com.technopolitan.mocaspaces.ui.register

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.technopolitan.mocaspaces.data.register.RegisterDataModule
import com.technopolitan.mocaspaces.data.register.RegisterRequestMapper
import com.technopolitan.mocaspaces.utilities.Constants
import javax.inject.Inject

class RegisterViewModel @Inject constructor(private var registerDataModule: RegisterDataModule) :
    ViewModel() {

    private val registerRequestMapperMediator: MediatorLiveData<RegisterRequestMapper> = MediatorLiveData()

    init {
        registerRequestMapperMediator.value = RegisterRequestMapper()
    }

    fun getRegisterRequestMapper(): RegisterRequestMapper = Constants.registerRequestMapper



}