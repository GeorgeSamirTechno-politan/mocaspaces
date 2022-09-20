package com.technopolitan.mocaspaces.di.activityModule


import android.app.Activity
import android.content.Context
import com.technopolitan.mocaspaces.data.main.CustomBottomNavigationModule
import com.technopolitan.mocaspaces.data.remote.MainRemote
import com.technopolitan.mocaspaces.modules.*
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
class MainActivityModule @Inject constructor(private val networkModule: NetworkModule) {

    @Singleton
    @Provides
    fun provideMainRemote(): MainRemote = MainRemote(networkModule)

    @Singleton
    @Provides
    fun provideCustomBottomNavigationModule(
        context: Context,
        activity: Activity,
        sharedPrefModule: SharedPrefModule,
        navigationModule: NavigationModule,
        glideModule: GlideModule,
        utilityModule: UtilityModule
    ):
            CustomBottomNavigationModule =
        CustomBottomNavigationModule(
            context,
            activity,
            sharedPrefModule,
            navigationModule,
            glideModule,
            utilityModule
        )

    @Singleton
    @Provides
    fun provideConnectionLiveDataModule(context: Context): ConnectionLiveDataModule =
        ConnectionLiveDataModule(context, networkModule)


}