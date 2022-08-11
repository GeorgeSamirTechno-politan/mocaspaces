package com.technopolitan.mocaspaces.ui.main

import android.os.Bundle
import android.window.OnBackInvokedDispatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.databinding.ActivityMainBinding
import com.technopolitan.mocaspaces.di.DaggerApplicationComponent
import com.technopolitan.mocaspaces.modules.*
import javax.inject.Inject


class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var appDefaultModel: AppDefaultModule

    @Inject
    lateinit var mainViewModel: MainViewModel

    @Inject
    lateinit var dialogModule: DialogModule

    @Inject
    lateinit var navigationModule: NavigationModule

    @Inject
    lateinit var permissionModule: PermissionModule
    private lateinit var splashScreen: SplashScreen

    //
    @Inject
    lateinit var utilityModule: UtilityModule
    private lateinit var activityMainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerApplicationComponent.factory()
            .buildDi(applicationContext, activity = this, fragment = null).inject(this)
        utilityModule.setStatusBar()
        splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        showNoInternetConnection()

//        listenForSplashEnd()
//        roundedCornersForBottomAppBar()

    }

    private fun showNoInternetConnection() {
        if (permissionModule.checkCustomPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)) {
            mainViewModel.updateNetworkChangeMediator()
            listenForConnectionChange()
        }
    }

    private fun listenForConnectionChange() {
        mainViewModel.connectionChangeLiveData().observe(this) {
            if (it == false) {
                navigationModule.navigateTo(
                    R.id.no_internet_fragment,
                    navHostId = R.id.nav_host_fragment
                )
            }
        }
    }

//    private fun roundedCornersForBottomAppBar() {
////        activityMainBinding.bottomNav.background = null
//        val shapeAppearanceModel =
//            ShapeAppearanceModel().toBuilder().setTopRightCorner(CornerFamily.ROUNDED, 50f)
//                .setTopLeftCorner(CornerFamily.ROUNDED, 50f)
//                .build()
//        ViewCompat.setBackground(
//            activityMainBinding.bottomAppBar,
//            MaterialShapeDrawable(shapeAppearanceModel)
//        )
//        ViewCompat.setBackground(
//            activityMainBinding.bottomNav,
//            MaterialShapeDrawable(shapeAppearanceModel)
//        )
//    }


//    private fun listenForSplashEnd() {
//
//    }

    override fun getOnBackInvokedDispatcher(): OnBackInvokedDispatcher {
        if (navigationModule.hasBackStack(R.id.nav_host_fragment))
        else {
            dialogModule.showCloseAppDialog()
//            return   dialogModule.showCloseAppDialog()
        }
        return super.getOnBackInvokedDispatcher()
    }

//    override fun onBackPressed() {
//        if (navigationModule.hasBackStack(R.id.nav_host_fragment))
//            super.onBackPressed()
//        else   dialogModule.showCloseAppDialog()
//
//    }


}



