package com.technopolitan.mocaspaces.ui.register

import androidx.lifecycle.ViewModel
import com.technopolitan.mocaspaces.bases.BaseViewModel
import com.technopolitan.mocaspaces.data.register.RegisterDataModule
import com.technopolitan.mocaspaces.data.register.RegisterRequestMapper
import javax.inject.Inject

class RegisterViewModel @Inject constructor(private var registerDataModule: RegisterDataModule) :
    ViewModel() {

    private var registerRequestMapper: RegisterRequestMapper = RegisterRequestMapper()

    fun getRegisterRequestMapper(): RegisterRequestMapper = registerRequestMapper

}