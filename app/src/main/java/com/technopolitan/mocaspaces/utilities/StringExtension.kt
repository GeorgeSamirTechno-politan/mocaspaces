package com.technopolitan.mocaspaces.utilities

import java.text.DecimalFormat

fun formatPrice(double: Double): String {
    val decimalFormat = DecimalFormat("#,###,##0.0")
    return decimalFormat.format(double)
}
