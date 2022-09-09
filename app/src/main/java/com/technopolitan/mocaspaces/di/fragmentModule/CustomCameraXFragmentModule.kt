package com.technopolitan.mocaspaces.di.fragmentModule

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import com.technopolitan.mocaspaces.modules.CameraXModule
import com.technopolitan.mocaspaces.modules.CustomAlertModule
import com.technopolitan.mocaspaces.modules.ImagePickerModule
import com.technopolitan.mocaspaces.modules.PikItModule
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CustomCameraXFragmentModule {

    @Singleton
    @Provides
    fun provideCameraXModule(
        context: Context,
        alertModule: CustomAlertModule,
        activity: Activity,
        pickItModule: PikItModule
    ): CameraXModule = CameraXModule(
        context,
        alertModule,
        activity,
        pickItModule
    )

    @Singleton
    @Provides
    fun provideImagePickerModule(fragment: Fragment?): ImagePickerModule =
        ImagePickerModule(fragment)
}