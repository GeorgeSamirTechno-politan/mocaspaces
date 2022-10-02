package com.technopolitan.mocaspaces.utilities

import android.view.View
import android.view.ViewGroup

fun View.setMargin(top: Int = 0, left: Int = 0, right: Int = 0, bottom: Int = 0) {
    val param = layoutParams as ViewGroup.MarginLayoutParams
    param.setMargins(left, top, right, bottom)
    layoutParams = param
}