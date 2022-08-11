package com.technopolitan.mocaspaces.modules

import android.content.Context
import android.widget.Toast
import dagger.Module
import javax.inject.Inject


@Module
class ToastModule @Inject constructor(private var context: Context?) {


    fun showToast(text: String?, isLongDuration: Boolean = false) {
        try {
            Toast.makeText(
                context,
                text,
                if (isLongDuration) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
            ).show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun showToast(resourceId: Int, context: Context?, isLongDuration: Boolean = false) {
        try {
            Toast.makeText(
                context,
                resourceId,
                if (isLongDuration) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
            ).show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}