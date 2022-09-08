package com.technopolitan.mocaspaces.ui.login

import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.LiveData
import com.google.android.material.textfield.TextInputLayout
import com.technopolitan.mocaspaces.bases.BaseViewModel
import com.technopolitan.mocaspaces.data.ApiStatus
import com.technopolitan.mocaspaces.data.login.LoginDataModule
import com.technopolitan.mocaspaces.data.login.LoginMapper
import com.technopolitan.mocaspaces.data.remote.LoginRemote
import com.technopolitan.mocaspaces.transitionButton.TransitionButton
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val loginRemote: LoginRemote,
    private val loginDataModule: LoginDataModule
) :
    BaseViewModel<LoginMapper>() {

    fun configLoginData(
        emailTextInputLayout: TextInputLayout,
        passwordTextInputLayout: TextInputLayout,
        loginButton: TransitionButton,
        biometricLoginLayout: LinearLayout,
        signUpText: TextView,
        orSignInTextView: TextView,
        callBack: (Entity: Any?) -> Unit
    ) {
        loginDataModule.init(
            emailTextInputLayout,
            passwordTextInputLayout,
            loginButton,
            biometricLoginLayout,
            signUpText,
            orSignInTextView,
            callBack
        )
    }

    fun login(email: String, password: String) {
        this.apiMutableLiveData = loginRemote.login(email, password)
    }

    fun handleLoginApiCall(): LiveData<ApiStatus<LoginMapper>> = apiMutableLiveData
}