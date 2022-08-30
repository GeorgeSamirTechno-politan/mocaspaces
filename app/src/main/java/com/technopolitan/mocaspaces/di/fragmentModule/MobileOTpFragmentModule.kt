package com.technopolitan.mocaspaces.di.fragmentModule

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import com.technopolitan.mocaspaces.data.mobileOTP.MobileOTPRemote
import com.technopolitan.mocaspaces.data.shared.OTPDataModule
import com.technopolitan.mocaspaces.data.shared.OtpBlockUserModule
import com.technopolitan.mocaspaces.modules.*
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

@Module
class MobileOTpFragmentModule @Inject constructor(private var networkModule: NetworkModule) {


    @Singleton
    @Provides
    fun provideSmsIdentifierModule(
        context: Context, permissionModule: PermissionModule,
        smsVerifyCatcherModule: SmsVerifyCatcherModule
    ): SmsIdentifierModule = SmsIdentifierModule(
        context, permissionModule,
        smsVerifyCatcherModule
    )

    @Singleton
    @Provides
    fun provideOTPDataModule(
        context: Context,
        rxModule: RXModule,
        otpBlockUserModule: OtpBlockUserModule,
        navigationModule: NavigationModule,
        smsIdentifierModule: SmsIdentifierModule
    ): OTPDataModule =
        OTPDataModule(context, rxModule, otpBlockUserModule, navigationModule, smsIdentifierModule)

    @Singleton
    @Provides
    fun provideMobileOtpRemote(): MobileOTPRemote = MobileOTPRemote(networkModule)

    @Singleton
    @Provides
    fun provideSmsVerifyCatcherModule(
        activity: Activity,
        context: Context,
        fragment: Fragment?
    ): SmsVerifyCatcherModule = SmsVerifyCatcherModule(
        activity, context, fragment
    )

}