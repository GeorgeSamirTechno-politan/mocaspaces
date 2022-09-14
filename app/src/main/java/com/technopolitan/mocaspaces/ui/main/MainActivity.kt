package com.technopolitan.mocaspaces.ui.main

import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.navigation.NavigationBarView
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.SharedPrefKey
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
    lateinit var pixModule: PixModule

    @Inject
    lateinit var permissionModule: PermissionModule

    @Inject
    lateinit var glideModule: GlideModule

    @Inject
    lateinit var sharedPrefModule: SharedPrefModule
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
//            activityResultLauncher,
            android.Manifest.permission.ACCESS_NETWORK_STATE,
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

    private fun setUpNavController() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        if (loggedIn()) {
            setLogInView()
        } else {
            setStartView()
        }
        navController.addOnDestinationChangedListener(listener)
    }

    private fun setStartView() {
        navController.setGraph(R.navigation.start_nav)
        binding.myPassFab.visibility = View.GONE
        binding.bottomAppBar.visibility = View.GONE
        binding.mainCoordinatorLayout.visibility = View.GONE
    }

    private fun setLogInView() {
        navController.setGraph(R.navigation.logged_in_nav)
        binding.myPassFab.visibility = View.VISIBLE
        binding.bottomAppBar.visibility = View.VISIBLE
        binding.mainCoordinatorLayout.visibility = View.VISIBLE
        roundedCornersForBottomAppBar()
        glideModule.loadImageBitmap(
            sharedPrefModule.getStringFromShared(SharedPrefKey.ProfileUrl.name),
            callback = {
                binding.bottomNav.menu.findItem(R.id.profile).icon = BitmapDrawable(resources, it)
            },
            res = resources
        )
        binding.bottomNav.setOnItemSelectedListener(bottomNavListener)
    }


    private fun loggedIn(): Boolean {
        return sharedPrefModule.contain(SharedPrefKey.BearerToken.name)
            .and(sharedPrefModule.getStringFromShared(SharedPrefKey.BearerToken.name).isNotEmpty())
    }

    private val bottomNavListener: NavigationBarView.OnItemSelectedListener =
        object : NavigationBarView.OnItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.home -> setHome(item)
                    R.id.my_bookings -> {
                        return true
                    }
                    R.id.notification -> {
                        return true
                    }
                    R.id.profile -> setProfile(item)
                }
                return false
            }

        }

    private fun setHome(item: MenuItem): Boolean {
        return if (item.isChecked) {
            changeBottomItemColor(R.color.grey_color, item)
            false
        } else {
            changeBottomItemColor(R.color.accent_color, item)
            navigationModule.navigateTo(R.id.home_fragment)
            item.isChecked = true
            true
        }
    }

    private fun setProfile(item: MenuItem) : Boolean{
        return if (item.isChecked) {
            changeBottomItemColor(android.R.color.transparent, item)
            false
        } else {
            changeBottomItemColor(android.R.color.transparent, item)
//            navigationModule.navigateTo(R.id.home_fragment)
            item.isChecked = true
            true
        }
    }

    private fun changeBottomItemColor(color: Int, item: MenuItem){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            item.icon.colorFilter = BlendModeColorFilter(getColor(color), BlendMode.SRC_OUT)
        }else{
            item.icon.colorFilter =
                PorterDuffColorFilter(getColor(color), PorterDuff.Mode.SRC_OUT)
        }
    }

    private val listener: NavController.OnDestinationChangedListener =
        NavController.OnDestinationChangedListener { controller, destination, arguments ->
            run {
                when (destination.id) {
                    R.id.register_fragment -> utilityModule.setStatusBar(R.color.white)
                    R.id.login_fragment -> utilityModule.setStatusBar(R.color.accent_color)
                    R.id.start_fragment -> utilityModule.setStatusBar(R.color.accent_color)
                    R.id.no_internet_fragment -> utilityModule.setStatusBar(R.color.accent_color)
                    R.id.splash_fragment -> utilityModule.setStatusBar(R.color.accent_color)
                }
            }
        }

    private fun roundedCornersForBottomAppBar() {
//        activityMainBinding.bottomNav.background = null
//        val shapeAppearanceModel =
//            ShapeAppearanceModel().toBuilder().setTopRightCorner(CornerFamily.ROUNDED, 50f)
//                .setTopLeftCorner(CornerFamily.ROUNDED, 50f)
//                .build()
//        ViewCompat.setBackground(
//            binding.bottomAppBar,
//            MaterialShapeDrawable(shapeAppearanceModel)
//        )
//        ViewCompat.setBackground(
//            binding.bottomNav,
//            MaterialShapeDrawable(shapeAppearanceModel)
//        )
    }
}



