package com.technopolitan.mocaspaces.di.fragmentModule

import android.content.Context
import androidx.fragment.app.Fragment
import com.technopolitan.mocaspaces.data.home.HomeSearchAdapter
import com.technopolitan.mocaspaces.data.home.HomeViewPagerAdapter
import com.technopolitan.mocaspaces.data.home.SearchHintListAdapter
import com.technopolitan.mocaspaces.data.home.WorkSpaceAdapter
import com.technopolitan.mocaspaces.data.remote.AddFavouriteWorkSpaceRemote
import com.technopolitan.mocaspaces.data.remote.DeleteWorkSpaceFavouriteRemote
import com.technopolitan.mocaspaces.data.remote.WorkSpaceRemote
import com.technopolitan.mocaspaces.modules.*
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
    fun provideWorkSpaceRemote(
        dateTimeModule: DateTimeModule,
        context: Context,
        spannableStringModule: SpannableStringModule
    ): WorkSpaceRemote =
        WorkSpaceRemote(networkModule, dateTimeModule, context, spannableStringModule)

    @Singleton
    @Provides
    fun provideAddWorkspaceFavouriteRemote(sharedPrefModule: SharedPrefModule): AddFavouriteWorkSpaceRemote =
        AddFavouriteWorkSpaceRemote(networkModule, sharedPrefModule)

    @Singleton
    @Provides
    fun provideDeleteWorkSpaceFavouriteRemote(): DeleteWorkSpaceFavouriteRemote =
        DeleteWorkSpaceFavouriteRemote(networkModule)


    @Singleton
    @Provides
    fun provideWorkSpaceAdapter(
        glideModule: GlideModule,
        context: Context,
        spannableStringModule: SpannableStringModule,
    )
            : WorkSpaceAdapter = WorkSpaceAdapter(
        glideModule,
        context, spannableStringModule
    )

//    @Singleton
//    @Provides
//    fun provideSearchHintAdapter(spannableStringModule: SpannableStringModule) : SearchHintAdapter = SearchHintAdapter(spannableStringModule)

    @Singleton
    @Provides
    fun provideSearchHintAdapter(
        context: Context,
        spannableStringModule: SpannableStringModule
    ): SearchHintListAdapter = SearchHintListAdapter(context, spannableStringModule)

    @Singleton
    @Provides
    fun provideHomeSearchAdapter(
        context: Context,
        spannableStringModule: SpannableStringModule,
        searchHintListAdapter: SearchHintListAdapter,
    ): HomeSearchAdapter =
        HomeSearchAdapter(context, spannableStringModule, searchHintListAdapter)
}