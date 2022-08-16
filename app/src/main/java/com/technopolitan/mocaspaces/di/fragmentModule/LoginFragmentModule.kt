package com.technopolitan.mocaspaces.di.fragmentModule

import android.content.Context
import androidx.fragment.app.Fragment
import com.technopolitan.mocaspaces.data.login.LoginDataModule
import com.technopolitan.mocaspaces.data.remote.LoginRemote
import com.technopolitan.mocaspaces.modules.NetworkModule
import com.technopolitan.mocaspaces.modules.RXModule
import com.technopolitan.mocaspaces.modules.SharedPrefModule
import com.technopolitan.mocaspaces.modules.ValidationModule
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
class LoginFragmentModule @Inject constructor(private val networkModule: NetworkModule) {
    @Singleton
    @Provides
    fun provideLoginRemote(sharedPrefModule: SharedPrefModule): LoginRemote =
        LoginRemote(networkModule, sharedPrefModule)

    @Singleton
    @Provides
    fun provideLoginDataModule(
        context: Context,
        validationModule: ValidationModule,
        rxModule: RXModule,
        sharedPrefModule: SharedPrefModule,
        fragment: Fragment?
    ): LoginDataModule = LoginDataModule(
        context,
        validationModule, rxModule, sharedPrefModule, fragment
    )
}