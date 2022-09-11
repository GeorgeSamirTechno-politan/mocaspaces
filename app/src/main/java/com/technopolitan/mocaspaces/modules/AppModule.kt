package com.technopolitan.mocaspaces.modules

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import com.technopolitan.mocaspaces.data.shared.CountDownModule
import com.technopolitan.mocaspaces.data.shared.OtpBlockUserModule
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideSharedPrefModule(context: Context): SharedPrefModule = SharedPrefModule(context)

    @Singleton
    @Provides
    fun provideOkHttpClient(sharedPrefModule: SharedPrefModule): OkHttpClient =
        OkHttpClient(sharedPrefModule)

    @Singleton
    @Provides
    fun provideNetworkModel(context: Context, okHttpClient: OkHttpClient): NetworkModule =
        NetworkModule(okHttpClient, context)

    @Singleton
    @Provides
    fun provideGlideModule(context: Context): GlideModule = GlideModule(context)

    @Singleton
    @Provides
    fun provideLocalModule(context: Context): LocalModule = LocalModule(context)

    @Singleton
    @Provides
    fun providePermissionModule(
        context: Context,
        activity: Activity,
        fragment: Fragment?,
        alertModule: CustomAlertModule,
        dialogModule: DialogModule,
        sharedPrefModule: SharedPrefModule
    ): PermissionModule =
        PermissionModule(context, activity, fragment, alertModule, dialogModule, sharedPrefModule)

    @Singleton
    @Provides
    fun provideToastModule(context: Context): ToastModule = ToastModule(context)

    @Singleton
    @Provides
    fun provideUtilityModule(context: Context, activity: Activity): UtilityModule =
        UtilityModule(context, activity)

    @Singleton
    @Provides
    fun provideAppDefaultModule(context: Context): AppDefaultModule = AppDefaultModule(context)


    @Singleton
    @Provides
    fun provideNavigationModule(fragment: Fragment?, activity: Activity): NavigationModule =
        NavigationModule(activity = activity, fragment = fragment)

    @Singleton
    @Provides
    fun provideDialogModule(
        context: Context,
        activity: Activity,
        navigationModule: NavigationModule
    ): DialogModule = DialogModule(
        context, navigationModule, activity
    )

    @Singleton
    @Provides
    fun provideApiResponseModule(
        dialogModule: DialogModule,
        context: Context,
        customToastModule: CustomAlertModule,
        activity: Activity,
    ): ApiResponseModule<Any> = ApiResponseModule<Any>(
        dialogModule, context, customToastModule, activity
    )

    @Singleton
    @Provides
    fun provideConnectionStateLiveDataModule(
        context: Context,
        networkModule: NetworkModule
    ): ConnectionLiveDataModule = ConnectionLiveDataModule(
        context, networkModule
    )

    @Singleton
    @Provides
    fun provideValidationModule(): ValidationModule = ValidationModule()

    @Singleton
    @Provides
    fun provideRXModule(): RXModule = RXModule()

    @Singleton
    @Provides
    fun provideDateTimeModule(): DateTimeModule = DateTimeModule()

    @Singleton
    @Provides
    fun provideCountDownModule(context: Context): CountDownModule = CountDownModule(context)

    @Singleton
    @Provides
    fun provideOtpBlocUserModule(
        context: Context,
        dialogModule: DialogModule,
        sharedPrefModule: SharedPrefModule,
        dateTimeModule: DateTimeModule,
        countDownModule: CountDownModule
    ): OtpBlockUserModule = OtpBlockUserModule(
        context,
        dialogModule,
        sharedPrefModule,
        dateTimeModule,
        countDownModule
    )

    @Singleton
    @Provides
    fun provideCustomAlertModule(context: Context, dialogModule: DialogModule): CustomAlertModule =
        CustomAlertModule(context, dialogModule)

//    @Singleton
//    @Provides
//    fun provideCropFaceModule(): CropFaceModule = CropFaceModule()

    @Singleton
    @Provides
    fun providePikItModule(context: Context, activity: Activity, fragment: Fragment?) =
        PikItModule(fragment, context, activity)

//    @Singleton
//    @Provides
//    fun provideBitmapModule(): BitmapModule = BitmapModule()


}