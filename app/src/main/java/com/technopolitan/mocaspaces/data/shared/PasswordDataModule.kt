package com.technopolitan.mocaspaces.data.shared

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.modules.RXModule
import com.technopolitan.mocaspaces.modules.ValidationModule
import com.technopolitan.mocaspaces.transitionButton.TransitionButton
import com.technopolitan.mocaspaces.utilities.Constants
import dagger.Module
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.regex.Pattern
import javax.inject.Inject

@Module
class PasswordDataModule @Inject constructor(
    private var context: Context,
    private var rxModule: RXModule,
    private var validationModule: ValidationModule
) {

    private lateinit var passwordTextInputLayout: TextInputLayout
    private lateinit var confirmPasswordTextInputLayout: TextInputLayout
    private lateinit var validationLayout: LinearLayout
    private lateinit var atLeastEightCharTextView: MaterialTextView
    private lateinit var upperAndLowerCaseTextView: MaterialTextView
    private lateinit var atLeastOneNumberTextView: MaterialTextView
    private lateinit var atLeastSpecialCharTextView: MaterialTextView
    private lateinit var button: TransitionButton
    private lateinit var callback: () -> Unit
    private lateinit var passwordObservable: Observable<String>
    private lateinit var confirmObservable: Observable<String>

    fun init(
        passwordTextInputLayout: TextInputLayout,
        confirmPasswordTextInputLayout: TextInputLayout,
        validationLayout: LinearLayout,
        atLeastEightCharTextView: MaterialTextView,
        upperAndLowerCaseTextView: MaterialTextView,
        atLeastOneNumberTextView: MaterialTextView,
        atLeastSpecialCharTextView: MaterialTextView,
        button: TransitionButton,
        callback: () -> Unit
    ) {
        this.passwordTextInputLayout = passwordTextInputLayout
        this.confirmPasswordTextInputLayout = confirmPasswordTextInputLayout
        this.validationLayout = validationLayout
        this.atLeastEightCharTextView = atLeastEightCharTextView
        this.upperAndLowerCaseTextView = upperAndLowerCaseTextView
        this.atLeastOneNumberTextView = atLeastOneNumberTextView
        this.atLeastSpecialCharTextView = atLeastSpecialCharTextView
        this.button = button
        this.callback = callback
        buttonChange(false)
        listenForPasswordFocus()
        initObservable()
    }

    private fun buttonChange(enable: Boolean) {
        if (enable) {
            button.isEnabled = true
            button.alpha = 1.toFloat()
        } else {
            button.isEnabled = false
            button.alpha = 0.5.toFloat()
        }
    }

    private fun listenForPasswordFocus() {
        passwordTextInputLayout.editText!!.setOnFocusChangeListener { view: View, focus: Boolean ->
            if (focus && validationLayout.visibility != View.VISIBLE)
                validationLayout.visibility = View.VISIBLE
            else if (!focus && validationLayout.visibility == View.VISIBLE)
                validationLayout.visibility = View.GONE
        }
    }

    private fun initObservable() {
        initPasswordPublisher()
        initConfirmPasswordPublisher()
        initCombineLatest()
    }

    private fun initPasswordPublisher() {
        passwordObservable = rxModule.getTextWatcherObservable(passwordTextInputLayout.editText!!)
            .subscribeOn(Schedulers.io()).observeOn(
                AndroidSchedulers.mainThread()
            )
        passwordObservable.subscribe {
            initValidationLayout(it)
        }
    }

    private fun initValidationLayout(text: String) {
        validateLengthLayout(text)
        validateUpperAndLowerLayout(text)
        validateOneNumberLayout(text)
        validateSpecialCharLayout(text)
    }

    private fun makeChecked(textView: MaterialTextView) {
        textView.setTextColor(context.getColor(R.color.green_color))
        textView.setCompoundDrawablesWithIntrinsicBounds(
            R.drawable.ic_two_tone_check_green,
            0,
            0,
            0
        )
    }

    private fun makeUnChecked(textView: MaterialTextView) {
        textView.setTextColor(context.getColor(R.color.accent_color))
        textView.setCompoundDrawablesWithIntrinsicBounds(
            R.drawable.ic_two_tone_close_purble,
            0,
            0,
            0
        )
    }

    private fun validateLengthLayout(text: String) {
        if (validateLength(text))
            makeChecked(atLeastEightCharTextView)
        else makeUnChecked(atLeastEightCharTextView)
    }

    private fun validateUpperAndLowerLayout(text: String) {
        if (validateUpperAndLowerCase(text))
            makeChecked(upperAndLowerCaseTextView)
        else makeUnChecked(upperAndLowerCaseTextView)
    }

    private fun validateOneNumberLayout(text: String) {
        if (validateAtLeastOneNumber(text))
            makeChecked(atLeastOneNumberTextView)
        else makeUnChecked(atLeastOneNumberTextView)
    }

    private fun validateSpecialCharLayout(text: String) {
        if (validateAtLeastOneSpecialChar(text))
            makeChecked(atLeastEightCharTextView)
        else makeUnChecked(atLeastEightCharTextView)
    }

    private fun validateLength(text: String): Boolean = text.length >= Constants.passwordLength
    private fun validateUpperAndLowerCase(text: String): Boolean = Pattern.matches(
        Constants.atLeastLowerCaseRegex,
        text
    ) && Pattern.matches(Constants.atLeastUpperCaseRegex, text)

    private fun validateAtLeastOneNumber(text: String): Boolean =
        Pattern.matches(Constants.atLeastNumberCaseRegex, text)

    private fun validateAtLeastOneSpecialChar(text: String): Boolean =
        Pattern.matches(Constants.atLeastSpecialCharacter, text)


    private fun initConfirmPasswordPublisher() {
        confirmObservable =
            rxModule.getTextWatcherObservable(confirmPasswordTextInputLayout.editText!!)
                .subscribeOn(Schedulers.io()).observeOn(
                    AndroidSchedulers.mainThread()
                )
        confirmObservable.subscribe {
            validateConfirmPassword() && validatePasswordSame()
        }
    }

    private fun validateConfirmPassword(): Boolean = validationModule.validateWithRegex(
        confirmPasswordTextInputLayout,
        Constants.passwordRegex,
        "",
        false
    )

    private fun validatePasswordSame(): Boolean = validationModule.validateMatches(
        passwordTextInputLayout,
        confirmPasswordTextInputLayout,
        context.getString(R.string.password_not_matach),
        false
    )

    private fun validatePassword(): Boolean = validationModule.validateWithRegex(
        passwordTextInputLayout,
        Constants.passwordRegex,
        "",
        false
    )


    private fun initCombineLatest() {
        Observable.combineLatest(passwordObservable, confirmObservable) { _: String, _: String ->
            return@combineLatest validatePasswordAndConfirm()
        }.subscribe {
            buttonChange(it)
            button.setOnClickListener {
                callback()
            }
        }

    }

    private fun validatePasswordAndConfirm(): Boolean {
        return validatePassword() && validateConfirmPassword() && validatePasswordSame()
    }


}