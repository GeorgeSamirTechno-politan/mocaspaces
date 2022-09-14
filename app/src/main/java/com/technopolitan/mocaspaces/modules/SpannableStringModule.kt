package com.technopolitan.mocaspaces.modules

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import androidx.core.content.res.ResourcesCompat
import dagger.Module
import javax.inject.Inject

@Module
class SpannableStringModule @Inject constructor(private var context: Context) {

    private var spannableString:SpannableString = SpannableString("")

    fun addString(vararg strings: String): SpannableStringModule {
        for (s in strings) spannableString = SpannableString(spannableString.toString() + s)
        return this
    }

    fun setColor(
        colorResourceId: Int,
        start: Int,
        end: Int
    ): SpannableStringModule {
        spannableString.setSpan(
            ForegroundColorSpan(context.getColor(colorResourceId)),
            start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return this
    }

    fun setSize(dimen: Int, start: Int, end: Int): SpannableStringModule {
        spannableString.setSpan(
            AbsoluteSizeSpan(getDimensToFloat(context, dimen)),
            start,
            end,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )
        return this
    }

    fun setTypeFace(
        context: Context,
        fontResourceId: Int,
        start: Int,
        end: Int
    ): SpannableStringModule {
        val typeface = ResourcesCompat.getFont(context, fontResourceId)
        spannableString.setSpan(
            CustomTypefaceSpanModule("", typeface),
            start,
            end,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return this
    }

    fun getSpannableString(): SpannableString {
        return spannableString
    }

    private fun getDimensToFloat(context: Context, dimen: Int): Int {
        return context.resources.getDimensionPixelSize(dimen)
    }


}