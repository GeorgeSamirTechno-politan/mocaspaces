package com.technopolitan.mocaspaces.ui.resetPassword

import androidx.lifecycle.MutableLiveData
import com.technopolitan.mocaspaces.bases.BaseViewModel
import com.technopolitan.mocaspaces.data.ApiStatus
import com.technopolitan.mocaspaces.data.remote.ResetPasswordRemote
import com.technopolitan.mocaspaces.data.shared.PasswordDataModule
import com.technopolitan.mocaspaces.databinding.FragmentResetPasswordBinding
import javax.inject.Inject

class ResetPasswordViewModel@Inject constructor(
    private var passwordDataModule: PasswordDataModule,
    private var resetPasswordRemote: ResetPasswordRemote
) : BaseViewModel<String>() {

    fun initDataModule(binding: FragmentResetPasswordBinding, callback: () -> Unit) {
        passwordDataModule.init(
            binding.resetPasswordLayout.newPasswordTextInputLayout,
            binding.resetPasswordLayout.rePasswordTextInputLayout,
            binding.resetPasswordLayout.passwordValidationLayout,
            binding.resetPasswordLayout.eightCharAtLeastTextView,
            binding.resetPasswordLayout.upperLowerCaseTextView,
            binding.resetPasswordLayout.oneNumberAtLeastTextView,
            binding.resetPasswordLayout.specialCharAtLeastTextView,
            binding.resetPasswordBtn,
            callback
        )
    }

    fun setResetPassword(mobile: String, newPassword: String) {
        apiMediatorLiveData = resetPasswordRemote.getResetPassword(mobile, newPassword)
    }

    fun getResetPassword(): MutableLiveData<ApiStatus<String>> {
        return apiMediatorLiveData
    }
}