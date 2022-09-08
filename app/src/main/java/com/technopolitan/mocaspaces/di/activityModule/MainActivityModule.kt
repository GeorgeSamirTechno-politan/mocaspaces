package com.technopolitan.mocaspaces.di.activityModule


import com.technopolitan.mocaspaces.data.remote.MainRemote
import com.technopolitan.mocaspaces.modules.NetworkModule
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
class MainActivityModule @Inject constructor(private val networkModule: NetworkModule) {

    @Singleton
    @Provides
    fun provideMainRemote(): MainRemote = MainRemote(networkModule)
}