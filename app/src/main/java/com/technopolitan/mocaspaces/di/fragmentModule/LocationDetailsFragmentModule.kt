package com.technopolitan.mocaspaces.di.fragmentModule

import android.content.Context
import androidx.fragment.app.Fragment
import com.technopolitan.mocaspaces.modules.GoogleMapModule
import com.technopolitan.mocaspaces.modules.NetworkModule
import com.technopolitan.mocaspaces.modules.UtilityModule
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

@Module
class LocationDetailsFragmentModule @Inject constructor(private var networkModule: NetworkModule) {

    @Singleton
    @Provides
    fun provideGoogleMapModule(
        context: Context,
        utilityModule: UtilityModule,
        fragment: Fragment?
    ): GoogleMapModule = GoogleMapModule(
        context,
        utilityModule, fragment
    )
}