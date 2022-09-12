package com.technopolitan.mocaspaces.di.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.datatransport.runtime.dagger.multibindings.IntoMap
import com.technopolitan.mocaspaces.ui.register.RegisterViewModel
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(impl: ViewModelProviderFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(RegisterViewModel::class)
    abstract fun bindRegisterViewModel(impl: RegisterViewModel): ViewModel
}