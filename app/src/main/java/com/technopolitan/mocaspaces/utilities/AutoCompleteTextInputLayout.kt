package com.technopolitan.mocaspaces.utilities

import android.content.Context
import android.util.AttributeSet
import android.view.ContextThemeWrapper
import com.google.android.material.textfield.TextInputLayout
import com.technopolitan.mocaspaces.R

class AutoCompleteTextInputLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = com.google.android.material.R.attr.textInputStyle
) : TextInputLayout(
    ContextThemeWrapper(context, R.style.text_input_layout_style),
    attrs, defStyleAttr
)