package com.technopolitan.mocaspaces.transitionButton

import android.graphics.Color


object ColorUtils {
    private fun correct(color: String): String {
        var color = color
        color = color.replace("#([0-9a-fA-F])([0-9a-fA-F])([0-9a-fA-F])".toRegex(), "#$1$1$2$2$3$3")
        return color
    }

    fun parse(color: String): Int {
        var color = color
        when (color.length) {
            4 -> {
                color = color.replace(
                    "#([0-9a-fA-F])([0-9a-fA-F])([0-9a-fA-F])".toRegex(),
                    "#$1$1$2$2$3$3"
                )
                color = color.replace(
                    "#([0-9a-fA-F])([0-9a-fA-F])([0-9a-fA-F])([0-9a-fA-F])".toRegex(),
                    "#$2$2$3$3$4$4"
                )
            }
            5 -> color = color.replace(
                "#([0-9a-fA-F])([0-9a-fA-F])([0-9a-fA-F])([0-9a-fA-F])".toRegex(),
                "#$2$2$3$3$4$4"
            )
        }
        return Color.parseColor(color)
    }
}