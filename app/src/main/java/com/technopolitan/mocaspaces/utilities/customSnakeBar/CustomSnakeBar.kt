package com.technopolitan.mocaspaces.utilities.customSnakeBar

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.technopolitan.mocaspaces.R

class CustomSnakeBar(parent: ViewGroup, content: CustomSnakeBarView) :
    BaseTransientBottomBar<CustomSnakeBar>(parent, content, content) {

    init {
        getView().setBackgroundColor(
            ContextCompat.getColor(view.context, android.R.color.transparent)
        )
        getView().setPadding(
            0,
            0,
            0,
            0
        )
    }

    companion object {

        fun make(
            viewGroup: ViewGroup,
            message: String, long: Boolean = true
        ) {
            val customView = LayoutInflater.from(viewGroup.context).inflate(
             R.layout.custom_snake_bar_layout,viewGroup, false
            ) as CustomSnakeBarView
            customView.messageText.text = message
            CustomSnakeBar(viewGroup, customView).setDuration(if (long) 3000 else 1000).show()
        }
    }
}