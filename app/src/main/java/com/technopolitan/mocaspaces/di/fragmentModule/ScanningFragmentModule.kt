package com.technopolitan.mocaspaces.di.fragmentModule

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import com.technopolitan.mocaspaces.modules.CompressModule
import com.technopolitan.mocaspaces.modules.UtilityModule
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ScanningFragmentModule {

    @Singleton
    @Provides
    fun provideCompressModule(
        context: Context,
        fragment: Fragment?,
        utilityModule: UtilityModule,
        activity: Activity
    ): CompressModule = CompressModule(context, fragment, utilityModule, activity)
}