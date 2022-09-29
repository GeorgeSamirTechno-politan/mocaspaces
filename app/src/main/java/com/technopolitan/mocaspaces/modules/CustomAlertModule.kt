package com.technopolitan.mocaspaces.modules

import android.content.Context
import android.view.ViewGroup
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.utilities.customSnakeBar.CustomSnakeBar
import dagger.Module
import javax.inject.Inject


@Module
class CustomAlertModule @Inject constructor(private var context: Context, private var dialogModule: DialogModule) {


    fun showSnakeBar(viewGroup: ViewGroup, message: String) {
        CustomSnakeBar.make(viewGroup, message)
    }

    fun showMessageDialog(message: String, callback: (entity: Boolean)-> Unit){
        dialogModule.showMessageDialog(message, context.getString(R.string.ok),callback)
    }


}