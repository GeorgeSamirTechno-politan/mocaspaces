package com.technopolitan.mocaspaces.customSnakeBar

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.ContentViewCallback
import com.technopolitan.mocaspaces.R

class CustomSnakeBarView @JvmOverloads constructor(
    context: Context, attributeSet: AttributeSet? = null,
    defaultStyle: Int = 0
) : ConstraintLayout(context, attributeSet, defaultStyle), ContentViewCallback {

   lateinit var messageText: TextView

    init {
        View.inflate(context, R.layout.custom_alert_layout, this)
        messageText = findViewById(R.id.alert_text_view)
        findViewById<ImageView>(R.id.alert_close_image_view).setOnClickListener{
            if(isShown)
                this.visibility = GONE
        }
    }

    override fun animateContentIn(delay: Int, duration: Int) {

    }

    override fun animateContentOut(delay: Int, duration: Int) {

    }


}