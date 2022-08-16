package com.technopolitan.mocaspaces.di.fragmentModule

import com.technopolitan.mocaspaces.data.remote.CheckMobileRemote
import com.technopolitan.mocaspaces.modules.NetworkModule
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

@Module
class checkMobileFragmentModule @Inject constructor(private var networkModule: NetworkModule) {

    @Singleton
    @Provides
    fun provideCheckMobileRemote(): CheckMobileRemote = CheckMobileRemote(networkModule)

}