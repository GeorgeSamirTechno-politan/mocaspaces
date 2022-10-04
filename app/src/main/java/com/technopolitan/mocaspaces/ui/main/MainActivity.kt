package com.technopolitan.mocaspaces.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import com.technopolitan.mocaspaces.databinding.ActivityMainBinding
import com.technopolitan.mocaspaces.di.DaggerApplicationComponent
import com.technopolitan.mocaspaces.di.viewModel.ViewModelFactory
import com.technopolitan.mocaspaces.modules.*
import javax.inject.Inject


class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var appDefaultModel: AppDefaultModule

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var mainViewModel: MainViewModel

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

    @Inject
    lateinit var dialogModule: DialogModule

    private lateinit var splashScreen: SplashScreen

    @Inject
    lateinit var utilityModule: UtilityModule
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerApplicationComponent.factory()
            .buildDi(applicationContext, activity = this, fragment = null).inject(this)
        setStatusBar()
        initSplashScreen()
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBottomNavigation()
    }

    private fun setStatusBar() {
        utilityModule.setStatusBar()
    }

    private fun initSplashScreen() {
        splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition { false }
    }


    private fun initBottomNavigation() {
        mainViewModel.initCustomBottomNavigationModule(
            binding.customBottomNavLayout,
            binding.myPassTab,
            this
        )
    }


//    private val bottomNavListener: NavigationBarView.OnItemSelectedListener =
//        object : NavigationBarView.OnItemSelectedListener {
//            override fun onNavigationItemSelected(item: MenuItem): Boolean {
//                when (item.itemId) {
//                    R.id.home -> setHome(item)
//                    R.id.my_bookings -> {
//                        return true
//                    }
//                    R.id.notification -> {
//                        return true
//                    }
//                    R.id.profile -> setProfile(item)
//                }
//                return false
//            }
//
//        }

//    private fun setHome(item: MenuItem): Boolean {
//        return if (item.isChecked) {
//            changeBottomItemColor(R.color.grey_color, item)
//            false
//        } else {
//            changeBottomItemColor(R.color.accent_color, item)
//            navigationModule.navigateTo(R.id.home_fragment)
//            item.isChecked = true
//            true
//        }
//    }
//
//    private fun setProfile(item: MenuItem) : Boolean{
//        return if (item.isChecked) {
//            changeBottomItemColor(android.R.color.transparent, item)
//            false
//        } else {
//            changeBottomItemColor(android.R.color.transparent, item)
////            navigationModule.navigateTo(R.id.home_fragment)
//            item.isChecked = true
//            true
//        }
//    }
//
//    private fun changeBottomItemColor(color: Int, item: MenuItem){
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            item.icon.colorFilter = BlendModeColorFilter(getColor(color), BlendMode.SRC_OUT)
//        }else{
//            item.icon.colorFilter =
//                PorterDuffColorFilter(getColor(color), PorterDuff.Mode.SRC_OUT)
//        }
//    }


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



