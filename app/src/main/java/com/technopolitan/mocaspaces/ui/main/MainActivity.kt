package com.technopolitan.mocaspaces.ui.main

import android.os.Bundle
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.databinding.ActivityMainBinding
import com.technopolitan.mocaspaces.di.DaggerApplicationComponent
import com.technopolitan.mocaspaces.modules.*
import com.technopolitan.mocaspaces.utilities.Constants
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
    lateinit var pixModule: PixModule

    @Inject
    lateinit var permissionModule: PermissionModule
    private val activityResultLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
//        viewModel.updatePermissionResult(it)
        }
    private lateinit var splashScreen: SplashScreen
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    //
    @Inject
    lateinit var utilityModule: UtilityModule
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerApplicationComponent.factory()
            .buildDi(applicationContext, activity = this, fragment = null).inject(this)
        utilityModule.setStatusBar()
        splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpNavController()
        requestNetworkStatusPermission()
        onBackPressedCallBack()

//        addPixToActivity(R.id.nav_host_fragment, pixModule.options)
    }

    private fun requestNetworkStatusPermission() {
        permissionModule.init(
            activityResultLauncher,
            android.Manifest.permission.ACCESS_NETWORK_STATE,
            Constants.networkStatusPermissionCode,
            getString(R.string.network_status_permission),
            getString(R.string.network_Status_permission_message)
        ) {
            showNoInternetConnection()
        }
    }


    private fun onBackPressedCallBack() {
        onBackPressedDispatcher.addCallback(this, true) {
            try {
                if (supportFragmentManager.backStackEntryCount > 0) {
                    supportFragmentManager.popBackStack()
                } else if (navigationModule.hasBackStack(R.id.nav_host_fragment))
                    navigationModule.popBack(navHostId = R.id.nav_host_fragment)
                else dialogModule.showCloseAppDialog()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun showNoInternetConnection() {
        mainViewModel.updateNetworkChangeMediator()
        listenForConnectionChange()
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

    private fun setUpNavController(){
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        navController.addOnDestinationChangedListener(listener)
    }

    private val listener: NavController.OnDestinationChangedListener =
        NavController.OnDestinationChangedListener { controller, destination, arguments ->
            run {
                when (destination.id) {
                    R.id.register_fragment -> utilityModule.setStatusBar(R.color.white)
                    R.id.login_fragment->utilityModule.setStatusBar(R.color.accent_color)
                    R.id.start_fragment-> utilityModule.setStatusBar(R.color.accent_color)
                    R.id.no_internet_fragment-> utilityModule.setStatusBar(R.color.accent_color)
                    R.id.splash_fragment->utilityModule.setStatusBar(R.color.accent_color)
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
}



