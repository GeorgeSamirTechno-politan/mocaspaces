package com.technopolitan.mocaspaces.ui.registerPassword

import androidx.lifecycle.MutableLiveData
import com.technopolitan.mocaspaces.bases.BaseViewModel
import com.technopolitan.mocaspaces.data.ApiStatus
import com.technopolitan.mocaspaces.data.register.RegisterRequestMapper
import com.technopolitan.mocaspaces.data.remote.RegisterRemote
import com.technopolitan.mocaspaces.data.shared.PasswordDataModule
import com.technopolitan.mocaspaces.databinding.FragmentRegisterPasswordBinding
import javax.inject.Inject

class RegisterPasswordViewModel @Inject constructor(
    private var passwordDataModule: PasswordDataModule,
    private var registerRemote: RegisterRemote
) : BaseViewModel<String>() {

    fun initDataModule(binding: FragmentRegisterPasswordBinding, callback: () -> Unit) {
        passwordDataModule.init(
            binding.registerPasswordLayout.newPasswordTextInputLayout,
            binding.registerPasswordLayout.rePasswordTextInputLayout,
            binding.registerPasswordLayout.passwordValidationLayout,
            binding.registerPasswordLayout.eightCharAtLeastTextView,
            binding.registerPasswordLayout.upperLowerCaseTextView,
            binding.registerPasswordLayout.oneNumberAtLeastTextView,
            binding.registerPasswordLayout.specialCharAtLeastTextView,
            binding.createAccountBtn,
            callback
        )
    }

    fun setRegisterRequest(registerRequestMapper: RegisterRequestMapper) {
        apiMutableLiveData = registerRemote.register(registerRequestMapper)
    }

    fun getRegisterHandler(): MutableLiveData<ApiStatus<String>> {
        return apiMutableLiveData
    }
}