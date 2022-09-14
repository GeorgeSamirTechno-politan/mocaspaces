package com.technopolitan.mocaspaces.di.fragmentModule

import android.content.Context
import androidx.fragment.app.Fragment
import com.technopolitan.mocaspaces.data.home.HomeDateModule
import com.technopolitan.mocaspaces.data.home.HomeViewPagerAdapter
import com.technopolitan.mocaspaces.data.remote.WorkSpaceRemote
import com.technopolitan.mocaspaces.modules.DateTimeModule
import com.technopolitan.mocaspaces.modules.NetworkModule
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

@Module
class HomeFragmentModule @Inject constructor(private var networkModule: NetworkModule) {

    @Singleton
    @Provides
    fun provideHomeViewPagerAdapter(context: Context, fragment: Fragment?): HomeViewPagerAdapter =
        HomeViewPagerAdapter(context, fragment)

    @Singleton
    @Provides
    fun provideHomeDataModule(
        context: Context,
        homeViewPagerAdapter: HomeViewPagerAdapter
    ): HomeDateModule =
        HomeDateModule(context, homeViewPagerAdapter)

    @Singleton
    @Provides
    fun provideWorkSpaceRemote(dateTimeModule: DateTimeModule): WorkSpaceRemote =
        WorkSpaceRemote(networkModule, dateTimeModule)
}