package com.technopolitan.mocaspaces.di.fragmentModule

import android.content.Context
import com.technopolitan.mocaspaces.data.country.CountryDataModule
import com.technopolitan.mocaspaces.data.remote.CountryRemote
import com.technopolitan.mocaspaces.modules.GlideModule
import com.technopolitan.mocaspaces.modules.NetworkModule
import com.technopolitan.mocaspaces.modules.PowerMenuModule
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

@Module
class ForgetPasswordMobileFragmentModule @Inject constructor(private var networkModule: NetworkModule) {

    @Singleton
    @Provides
    fun provideCountryDataModule(
        context: Context,
        powerMenuModule: PowerMenuModule,
        glideModule: GlideModule,
    ): CountryDataModule =
        CountryDataModule(context, powerMenuModule, glideModule)

    @Singleton
    @Provides
    fun provideCountryRemote(): CountryRemote = CountryRemote(networkModule)
}