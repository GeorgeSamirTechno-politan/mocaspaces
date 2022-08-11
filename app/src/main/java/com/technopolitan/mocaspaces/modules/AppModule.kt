package com.technopolitan.mocaspaces.modules

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient()

    @Singleton
    @Provides
    fun provideNetworkModel(context: Context): NetworkModule =
        NetworkModule(provideOkHttpClient(), context)

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
        fragment: Fragment?,
        activity: Activity,
        dialogModule: DialogModule
    ): PermissionModule = PermissionModule(context, fragment, activity, dialogModule)

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
    fun provideSharedPrefModule(context: Context): SharedPrefModule = SharedPrefModule(context)

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
        context: Context
    ): ApiResponseModule<Any> = ApiResponseModule<Any>(
        dialogModule, context
    )

    @Singleton
    @Provides
    fun provideConnectionStateLiveDataModule(
        context: Context,
        networkModule: NetworkModule
    ): ConnectionLiveDataModule = ConnectionLiveDataModule(
        context, networkModule
    )
}