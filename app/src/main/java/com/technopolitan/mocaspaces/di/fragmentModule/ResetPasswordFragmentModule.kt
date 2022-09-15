package com.technopolitan.mocaspaces.di.fragmentModule

import com.technopolitan.mocaspaces.data.remote.ResetPasswordRemote
import com.technopolitan.mocaspaces.modules.NetworkModule
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

@Module
class ResetPasswordFragmentModule @Inject constructor(private var networkModule: NetworkModule){

    @Singleton
    @Provides
    fun provideResetPasswordRemote(): ResetPasswordRemote= ResetPasswordRemote(networkModule)
}