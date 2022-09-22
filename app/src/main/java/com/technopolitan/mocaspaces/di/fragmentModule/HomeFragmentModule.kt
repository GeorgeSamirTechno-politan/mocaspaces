package com.technopolitan.mocaspaces.di.fragmentModule

import android.content.Context
import androidx.fragment.app.Fragment
import com.technopolitan.mocaspaces.data.home.*
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
    fun provideWorkSpaceRemote(dateTimeModule: DateTimeModule, context: Context): WorkSpaceRemote =
        WorkSpaceRemote(networkModule, dateTimeModule, context)

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
    fun providePriceAdapter(): PriceAdapter =
        PriceAdapter()

    @Singleton
    @Provides
    fun provideWorkSpaceAdapter(
        glideModule: GlideModule,
        context: Context,
        amenityAdapter: AmenityAdapter,
        spannableStringModule: SpannableStringModule,
        priceAdapter: PriceAdapter
    )
            : WorkSpaceAdapter = WorkSpaceAdapter(
        glideModule,
        context, amenityAdapter, spannableStringModule, priceAdapter
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