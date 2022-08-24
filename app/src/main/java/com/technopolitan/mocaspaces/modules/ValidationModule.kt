package com.technopolitan.mocaspaces.modules

import android.view.View
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import dagger.Module
import java.util.regex.Pattern

@Module
class ValidationModule {
    fun validateEmpty(
        editText: EditText,
        validateMessage: String?,
        isRequiredFocus: Boolean = true
    ): Boolean {
        if (editText.text.toString().isEmpty()) {
            if (isRequiredFocus) editText.requestFocus()
            editText.error = validateMessage
            return false
        }
        editText.error = null
        return true
    }

    fun validateEmpty(
        textInputLayout: TextInputLayout,
        validateMessage: String?,
        isRequiredFocus: Boolean = true
    ): Boolean {
        if (textInputLayout.editText!!.text.toString().isEmpty()) {
            if (isRequiredFocus) textInputLayout.editText!!.requestFocus()
            textInputLayout.error = validateMessage
            return false
        }
        textInputLayout.error = null
        return true
    }

    fun validateMatches(
        firstTextInputLayout: TextInputLayout,
        secondTextInputLayout: TextInputLayout,
        validateMessage: String?,
        isRequiredFocus: Boolean = true
    ): Boolean {
        if (firstTextInputLayout.editText!!.text.toString() != secondTextInputLayout.editText!!
                .text.toString()
        ) {
            if (isRequiredFocus) firstTextInputLayout.editText!!.requestFocus()
            firstTextInputLayout.error = validateMessage
            return false
        }
        firstTextInputLayout.error = null
        return true
    }

    fun validateMatches(
        firstEditText: EditText,
        secondEditText: EditText,
        validateMessage: String?,
        isRequiredFocus: Boolean = true
    ): Boolean {
        if (firstEditText.text.toString() != secondEditText.text.toString()) {
            if (isRequiredFocus) firstEditText.requestFocus()
            firstEditText.error = validateMessage
            return false
        }
        firstEditText.error = null
        return true
    }

    fun validateWithRegex(
        textInputLayout: TextInputLayout,
        regex: String,
        validateMessage: String,
        isRequiredFocus: Boolean = true
    ): Boolean {
        if (!Pattern.matches(regex, textInputLayout.editText!!.text.toString())) {
            if (isRequiredFocus) textInputLayout.editText!!.requestFocus()
            textInputLayout.error = validateMessage
            return false
        }
        textInputLayout.error = null
        return true
    }

    fun validateWithRegex(
        editText: EditText,
        regex: String,
        validateMessage: String,
        isRequiredFocus: Boolean = false
    ): Boolean {
        if (!Pattern.matches(regex, editText.text.toString())) {
            if (isRequiredFocus) editText.requestFocus()
            editText.error = validateMessage
            return false
        }
        editText.error = null
        return true
    }

    fun validateString(
        parentLayout: View,
        validateMessage: String,
        isLongDuration: Boolean,
        stringToValidate: String
    ): Boolean {
        if (stringToValidate.isEmpty()) {
            showSnakeBar(parentLayout, validateMessage, isLongDuration)
            return false
        }
        return true
    }

    fun validateInteger(
        parentLayout: View,
        validateMessage: String,
        isLongDuration: Boolean,
        idToValidate: Int
    ): Boolean {
        if (idToValidate == 0 || idToValidate == -1) {
            showSnakeBar(parentLayout, validateMessage, isLongDuration)
            return false
        }
        return true
    }

    private fun showSnakeBar(view: View, message: String, isLongDuration: Boolean) {
        Snackbar.make(
            view,
            message,
            if (isLongDuration) Snackbar.LENGTH_LONG else Snackbar.LENGTH_SHORT
        ).show()
    }
}