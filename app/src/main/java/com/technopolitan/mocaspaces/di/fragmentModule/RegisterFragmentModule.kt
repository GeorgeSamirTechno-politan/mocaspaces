package com.technopolitan.mocaspaces.di.fragmentModule

import android.content.Context
import androidx.fragment.app.Fragment
import com.technopolitan.mocaspaces.data.register.RegisterDataModule
import com.technopolitan.mocaspaces.modules.NetworkModule
import com.technopolitan.mocaspaces.modules.RXModule
import com.technopolitan.mocaspaces.modules.SharedPrefModule
import com.technopolitan.mocaspaces.modules.ValidationModule
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

@Module
class RegisterFragmentModule @Inject constructor(private var networkModule: NetworkModule){

    @Singleton
    @Provides
    fun provideRegisterDataModule(context: Context,
                                  validationModule: ValidationModule,
                                  rxModule: RXModule,
                                  sharedPrefModule: SharedPrefModule,
                                  fragment: Fragment?
    ): RegisterDataModule = RegisterDataModule(
        context,
        validationModule, rxModule, sharedPrefModule, fragment
    )
}