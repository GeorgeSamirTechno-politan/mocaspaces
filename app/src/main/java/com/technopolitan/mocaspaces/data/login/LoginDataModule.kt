package com.technopolitan.mocaspaces.data.login

import android.content.Context
import android.util.Patterns
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.biometric.BiometricManager
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputLayout
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.SharedPrefKey
import com.technopolitan.mocaspaces.data.CryptographyManager
import com.technopolitan.mocaspaces.data.shared.OtpBlockUserModule
import com.technopolitan.mocaspaces.modules.NavigationModule
import com.technopolitan.mocaspaces.modules.RXModule
import com.technopolitan.mocaspaces.modules.SharedPrefModule
import com.technopolitan.mocaspaces.modules.ValidationModule
import com.technopolitan.mocaspaces.utilities.transitionButton.TransitionButton
import dagger.Module
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@Module
class LoginDataModule @Inject constructor(
    private var context: Context,
    private var validationModule: ValidationModule,
    private var rxModule: RXModule,
    private var sharedPrefModule: SharedPrefModule,
    private var fragment: Fragment?,
    private var navigationModule: NavigationModule,
    private var otpBlockUserModule: OtpBlockUserModule,
) {

    private lateinit var emailObserver: Observable<String>
    private lateinit var passwordObserver: Observable<String>
    private lateinit var emailTextInputLayout: TextInputLayout
    private lateinit var passwordTextInputLayout: TextInputLayout
    private lateinit var loginButton: TransitionButton
    private lateinit var biometricLoginLayout: LinearLayout
    private lateinit var signUpText: TextView
    private lateinit var orSignInTextView: TextView
    private lateinit var callApiLogin: (entity: Any?) -> Unit
    private var cryptographyManager = CryptographyManager()
    private val ciphertextWrapper
        get() = cryptographyManager.getCiphertextWrapperFromSharedPrefs(
            SharedPrefKey.BiometricPrefs.name,
            Context.MODE_PRIVATE,
            SharedPrefKey.CiphertextWrapper.name,
            context
        )


    fun init(
        emailTextInputLayout: TextInputLayout,
        passwordTextInputLayout: TextInputLayout,
        loginButton: TransitionButton,
        biometricLoginLayout: LinearLayout,
        signUpText: TextView,
        orSignInTextView: TextView,
        callApiLogin: (entity: Any?) -> Unit
    ) {
        this.loginButton = loginButton
        this.emailTextInputLayout = emailTextInputLayout
        this.passwordTextInputLayout = passwordTextInputLayout
        this.biometricLoginLayout = biometricLoginLayout
        this.signUpText = signUpText
        this.callApiLogin = callApiLogin
        this.orSignInTextView = orSignInTextView
        initLoginDataModule()
    }

    private fun initLoginDataModule() {
        buttonChange(false)
        setEmailObserver()
        setPasswordObserver()
        subscribeObservers()
        configBiometric()
        doAuthenticate()
        clickOnSignUp()
    }

    private fun clickOnSignUp() {
        signUpText.setOnClickListener {
            if (otpBlockUserModule.canAuthenticate(1))
                navigationModule.navigateTo(R.id.action_login_to_register)
            else otpBlockUserModule.showBlockedDialog()
        }
    }

    private fun setEmailObserver() {
        emailObserver =
            rxModule.getTextWatcherObservable(emailTextInputLayout.editText!!)
                .subscribeOn(Schedulers.io()).observeOn(
                    AndroidSchedulers.mainThread()
                )
        emailObserver.subscribe {
            validateEmail()
        }
    }

    private fun setPasswordObserver() {
        passwordObserver =
            rxModule.getTextWatcherObservable(passwordTextInputLayout.editText!!)
                .subscribeOn(Schedulers.io())
                .observeOn(
                    AndroidSchedulers.mainThread()
                )
        passwordObserver.subscribe {
            validatePassword()
        }
    }

    private fun subscribeObservers() {
        Observable.combineLatest(
            emailObserver,
            passwordObserver
        ) { _: String, _: String ->
            return@combineLatest validateEmail()
                    && validatePassword()
        }.subscribe {
            buttonChange(it)
            loginButton.setOnClickListener { callLoginApi() }
        }
    }

    private fun buttonChange(enable: Boolean) {
        if (enable) {
            loginButton.isEnabled = true
            loginButton.alpha = 1.toFloat()
        } else {
            loginButton.isEnabled = false
            loginButton.alpha = 0.5.toFloat()
        }
    }

    private fun validatePassword() = validationModule.validateEmpty(
        passwordTextInputLayout,
        context.getString(R.string.required), false
    )

    private fun validateEmail() = validationModule.validateWithRegex(
        emailTextInputLayout, Patterns.EMAIL_ADDRESS.pattern(), context.getString(
            R.string.email_required
        ), false
    )

    private fun navigateSignUp() {
        navigationModule.navigateTo(R.id.action_login_to_register)
    }

    private fun configBiometric() {
        if (sharedPrefModule.contain(SharedPrefKey.UserEmail.name) && sharedPrefModule.contain(
                SharedPrefKey.UserPassword.name
            ) && canAuthenticate()
        ) {
            biometricLoginLayout.visibility = View.VISIBLE
            orSignInTextView.visibility = View.VISIBLE
        } else {
            biometricLoginLayout.visibility = View.GONE
            orSignInTextView.visibility = View.GONE
        }
    }

    private fun doAuthenticate() {
        biometricLoginLayout.setOnClickListener {
            if (ciphertextWrapper != null) {
                BiometricPromptUtils.createBiometricPrompt(fragment!!, context) {
                    emailTextInputLayout.editText?.setText(
                        sharedPrefModule.getStringFromShared(
                            SharedPrefKey.UserEmail.name
                        )
                    )
                    passwordTextInputLayout.editText?.setText(
                        sharedPrefModule.getStringFromShared(
                            SharedPrefKey.UserPassword.name
                        )
                    )
                    callLoginApi()
                }
            }
        }
    }

    private fun canAuthenticate(): Boolean {
        val canAuthenticate = BiometricManager.from(context)
            .canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK)
        return canAuthenticate == BiometricManager.BIOMETRIC_SUCCESS
    }

    private fun callLoginApi() {
        callApiLogin.invoke(null)

    }
}