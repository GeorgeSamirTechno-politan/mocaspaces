package com.technopolitan.mocaspaces.di.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.ViewModelFactoryDsl
import com.google.android.datatransport.runtime.dagger.multibindings.IntoMap
import com.technopolitan.mocaspaces.ui.checkMobile.CheckMobileViewModel
import com.technopolitan.mocaspaces.ui.login.LoginViewModel
import com.technopolitan.mocaspaces.ui.main.MainViewModel
import com.technopolitan.mocaspaces.ui.mobileOTP.MobileOTPViewModel
import com.technopolitan.mocaspaces.ui.personalInfo.PersonalInfoViewModel
import com.technopolitan.mocaspaces.ui.register.RegisterViewModel
import com.technopolitan.mocaspaces.ui.sharedViewModel.CountryViewModel
import com.technopolitan.mocaspaces.ui.splash.SplashViewModel
import com.technopolitan.mocaspaces.ui.studentVerify.StudentVerifyViewModel
import dagger.Binds
import dagger.Module

@Module()
abstract class ViewModelModule {

    @com.google.android.datatransport.runtime.dagger.Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewViewModelProviderFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(RegisterViewModel::class)
    abstract fun bindsRegisterViewModel(registerViewModel: RegisterViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindsLoginViewModel(loginViewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PersonalInfoViewModel::class)
    abstract fun bindsPersonalInfoViewModel(personalInfoViewModel: PersonalInfoViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CheckMobileViewModel::class)
    abstract fun bindsCheckMobileViewModel(checkMobileViewModel: CheckMobileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindsMainViewModel(mainViewModel: CheckMobileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MobileOTPViewModel::class)
    abstract fun bindsMobileOTPViewModel(mobileOTPViewModel: MobileOTPViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CountryViewModel::class)
    abstract fun bindsCountryViewModel(countryViewModel: CountryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun bindsSplashViewModel(splashViewModel: SplashViewModel): ViewModel

//    @Binds
//    @IntoMap
//    @ViewModelKey(CountryViewModel::class)
//    abstract fun bindsCountryViewModel(countryViewModel: CountryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(StudentVerifyViewModel::class)
    abstract fun bindsStudentVerifyViewModel(studentVerifyViewModel: StudentVerifyViewModel): ViewModel
}