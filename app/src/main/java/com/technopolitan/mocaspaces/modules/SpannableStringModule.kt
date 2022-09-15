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

    private var spannableString: SpannableString = SpannableString("")
    private var addedString: String = ""
    private var start: Int = 0
    private var end: Int = 0

    fun newString() : SpannableStringModule{
        spannableString = SpannableString("")
        return this
    }

    fun addString(vararg strings: String): SpannableStringModule {
        addedString = ""
        for (s in strings) {
            spannableString = SpannableString(spannableString.toString() + s)
            addedString += s
        }

        start = if (spannableString.length == addedString.length) 0
        else spannableString.length - addedString.length
        end = spannableString.length -1
        return this
    }

    fun init(
        colorResourceId: Int? = null,
        dimen: Int? = null,
        fontResourceId: Int? = null
    ): SpannableStringModule {
        if (colorResourceId != null)
            setColor(colorResourceId, start, end)
        if (dimen != null)
            setSize(dimen, start, end)
        if (fontResourceId != null)
            setTypeFace(fontResourceId, start, end)
        return this
    }

    private fun setColor(
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

    private fun setSize(dimen: Int, start: Int, end: Int): SpannableStringModule {
        spannableString.setSpan(
            AbsoluteSizeSpan(getDimensToFloat(context, dimen)),
            start,
            end,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )
        return this
    }

    private fun setTypeFace(
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