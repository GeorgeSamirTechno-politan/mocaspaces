package com.technopolitan.mocaspaces.di.fragmentModule

import android.content.Context
import androidx.fragment.app.Fragment
import com.technopolitan.mocaspaces.data.remote.WorkSpaceDetailsRemote
import com.technopolitan.mocaspaces.modules.*
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

@Module
class LocationDetailsFragmentModule @Inject constructor(private var networkModule: NetworkModule) {

    @Singleton
    @Provides
    fun provideGoogleMapModule(
        context: Context,
        utilityModule: UtilityModule,
        fragment: Fragment?,
        permissionModule: PermissionModule
    ): GoogleMapModule = GoogleMapModule(
        context,
        utilityModule, fragment,
        permissionModule
    )

    @Singleton
    @Provides
    fun provideWorkSpaceDetailsRemote(
        context: Context,
        spannableStringModule: SpannableStringModule,
        dateTimeModule: DateTimeModule
    ): WorkSpaceDetailsRemote =
        WorkSpaceDetailsRemote(networkModule, context, spannableStringModule, dateTimeModule)

}