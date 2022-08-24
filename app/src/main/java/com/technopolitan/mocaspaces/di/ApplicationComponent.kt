package com.technopolitan.mocaspaces.di

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import com.technopolitan.mocaspaces.modules.AppModule
import com.technopolitan.mocaspaces.ui.checkMobile.CheckMobileFragment
import com.technopolitan.mocaspaces.ui.fragmentUtilities.NoInternetFragment
import com.technopolitan.mocaspaces.ui.fragmentUtilities.TwoChooseDialogFragment
import com.technopolitan.mocaspaces.ui.login.LoginFragment
import com.technopolitan.mocaspaces.ui.main.MainActivity
import com.technopolitan.mocaspaces.ui.mobileOTP.MobileOTPFragment
import com.technopolitan.mocaspaces.ui.register.RegisterFragment
import com.technopolitan.mocaspaces.ui.splash.SplashFragment
import com.technopolitan.mocaspaces.ui.start.StartFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
    ]
)
interface ApplicationComponent {

    @Component.Factory
    interface AppComponentFactory {
        fun buildDi(
            @BindsInstance context: Context, @BindsInstance activity: Activity,
            @BindsInstance fragment: Fragment? = null
        ): ApplicationComponent
    }

    fun inject(mainActivity: MainActivity)
    fun inject(startFragment: StartFragment)
    fun inject(splashFragment: SplashFragment)
    fun inject(loginFragment: LoginFragment)
    fun inject(twoChooseDialogFragment: TwoChooseDialogFragment)
    fun inject(noInternetFragment: NoInternetFragment)
    fun inject(registerFragment: RegisterFragment)
    fun inject(checkMobileFragment: CheckMobileFragment)
    fun inject(mobileOTPFragment: MobileOTPFragment)
}