package com.technopolitan.mocaspaces.di

//import com.technopolitan.mocaspaces.di.viewModel.ViewModelModule
import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import com.technopolitan.mocaspaces.modules.AppModule
import com.technopolitan.mocaspaces.ui.checkEmail.CheckEmailFragment
import com.technopolitan.mocaspaces.ui.checkMobile.CheckMobileFragment
import com.technopolitan.mocaspaces.ui.forgetPasswordMobile.ForgetPasswordMobileFragment
import com.technopolitan.mocaspaces.ui.fragmentUtilities.*
import com.technopolitan.mocaspaces.ui.login.LoginFragment
import com.technopolitan.mocaspaces.ui.main.MainActivity
import com.technopolitan.mocaspaces.ui.mobileOTP.MobileOTPFragment
import com.technopolitan.mocaspaces.ui.personalInfo.PersonalInfoFragment
import com.technopolitan.mocaspaces.ui.register.RegisterFragment
import com.technopolitan.mocaspaces.ui.registerPassword.RegisterPasswordFragment
import com.technopolitan.mocaspaces.ui.splash.SplashFragment
import com.technopolitan.mocaspaces.ui.start.StartFragment
import com.technopolitan.mocaspaces.ui.studentVerify.StudentVerifyFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
//        ViewModelModule::class
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
    fun inject(personalInfoFragment: PersonalInfoFragment)
    fun inject(datePickerFragment: DatePickerFragment)
    fun inject(singleButtonDialogFragment: SingleButtonDialogFragment)
    fun inject(scanningFragment: ScanningFragment)
    fun inject(customCameraXFragment: CustomCameraXFragment)
    fun inject(studentVerifyFragment: StudentVerifyFragment)
    fun inject(successRegisterFragment: SuccessRegisterFragment)
    fun inject(registerPasswordFragment: RegisterPasswordFragment)
    fun inject(checkEmailFragment: CheckEmailFragment)
    fun inject(forgetPasswordMobileFragment: ForgetPasswordMobileFragment)
}