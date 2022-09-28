package com.technopolitan.mocaspaces.utilities

import android.widget.TextView
import androidx.core.text.HtmlCompat

fun TextView.loadHtml(html: String) {
    this.text = HtmlCompat.fromHtml(html, 0)
}