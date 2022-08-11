package com.technopolitan.mocaspaces.modules

import android.content.Context
import android.content.res.Configuration
import com.technopolitan.mocaspaces.utilities.Constants
import dagger.Module
import java.util.*
import javax.inject.Inject


@Module
class LocalModule @Inject constructor(private var context: Context) {


    fun changeAppLanguage(): Context {
        if (Constants.appLanguage.isEmpty()) {
            Constants.appLanguage = "en"
        }
        val locale = Locale(Constants.appLanguage)
        Locale.setDefault(locale)
        val configuration = Configuration(context.resources.configuration)
        configuration.setLocale(locale)
        Locale.setDefault(locale)
        configuration.setLocale(locale)
        context.createConfigurationContext(configuration)
        return context
    }
}