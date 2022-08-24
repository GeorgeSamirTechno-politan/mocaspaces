package com.technopolitan.mocaspaces.di.fragmentModule

import android.content.Context
import com.technopolitan.mocaspaces.data.mobileOTP.MobileOTPRemote
import com.technopolitan.mocaspaces.data.shared.OTPDataModule
import com.technopolitan.mocaspaces.modules.*
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

@Module
class MobileOTpFragmentModule @Inject constructor(private var networkModule: NetworkModule) {

    @Singleton
    @Provides
    fun provideOTPDataModule(
        context: Context,
        dialogModule: DialogModule,
        rxModule: RXModule,
        sharedPrefModule: SharedPrefModule,
        dateTimeModule: DateTimeModule
    ): OTPDataModule =
        OTPDataModule(context, dialogModule, rxModule, sharedPrefModule, dateTimeModule)

    @Singleton
    @Provides
    fun provideMobileOtpRemote(): MobileOTPRemote = MobileOTPRemote(networkModule)

}