package com.technopolitan.mocaspaces.di.fragmentModule

import android.content.Context
import androidx.fragment.app.Fragment
import com.technopolitan.mocaspaces.data.login.LoginDataModule
import com.technopolitan.mocaspaces.data.remote.LoginRemote
import com.technopolitan.mocaspaces.data.shared.OtpBlockUserModule
import com.technopolitan.mocaspaces.modules.*
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
        fragment: Fragment?,
        navigationModule: NavigationModule,
        otpBlockUserModule: OtpBlockUserModule
    ): LoginDataModule = LoginDataModule(
        context,
        validationModule, rxModule, sharedPrefModule, fragment, navigationModule,
        otpBlockUserModule
    )
}