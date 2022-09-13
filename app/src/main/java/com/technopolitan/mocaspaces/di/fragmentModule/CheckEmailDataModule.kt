package com.technopolitan.mocaspaces.di.fragmentModule

import android.content.Context
import com.technopolitan.mocaspaces.data.remote.CheckOtpEmailRemote
import com.technopolitan.mocaspaces.data.remote.SendOtpEmailRemote
import com.technopolitan.mocaspaces.data.shared.OTPDataModule
import com.technopolitan.mocaspaces.data.shared.OtpBlockUserModule
import com.technopolitan.mocaspaces.modules.NavigationModule
import com.technopolitan.mocaspaces.modules.NetworkModule
import com.technopolitan.mocaspaces.modules.RXModule
import com.technopolitan.mocaspaces.modules.SmsIdentifierModule
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

@Module
class CheckEmailDataModule @Inject constructor(private var networkModule: NetworkModule) {

    @Singleton
    @Provides
    fun provideOtpDataModule(
        context: Context,
        rxModule: RXModule,
        otpBlockUserModule: OtpBlockUserModule,
        navigationModule: NavigationModule,
        smsIdentifierModule: SmsIdentifierModule
    ): OTPDataModule = OTPDataModule(
        context, rxModule, otpBlockUserModule, navigationModule, smsIdentifierModule
    )

    @Singleton
    @Provides
    fun provideCheckEmailOtpRemote(): CheckOtpEmailRemote = CheckOtpEmailRemote(networkModule)

    @Singleton
    @Provides
    fun provideSendOtpEmailRemote() = SendOtpEmailRemote(networkModule)


}