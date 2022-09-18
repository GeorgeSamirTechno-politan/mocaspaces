package com.technopolitan.mocaspaces.data.main

import android.app.Activity
import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.SharedPrefKey
import com.technopolitan.mocaspaces.databinding.CustomBottomNavigationLayoutBinding
import com.technopolitan.mocaspaces.modules.GlideModule
import com.technopolitan.mocaspaces.modules.NavigationModule
import com.technopolitan.mocaspaces.modules.SharedPrefModule
import com.technopolitan.mocaspaces.modules.UtilityModule
import com.technopolitan.mocaspaces.ui.main.MainActivity
import dagger.Module
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

@Module
class CustomBottomNavigationModule @Inject constructor(
    private var context: Context,
    private var activity: Activity,
    private var sharedPrefModule: SharedPrefModule,
    private var navigationModule: NavigationModule,
    private var glideModule: GlideModule,
    private var utilityModule: UtilityModule
) {

    private lateinit var binding: CustomBottomNavigationLayoutBinding
    private val loggedInPublisherSubject: PublishSubject<Boolean> = PublishSubject.create()
    private val myProfilePathPublisherSubject: PublishSubject<String> = PublishSubject.create()
    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment

    fun init(binding: CustomBottomNavigationLayoutBinding) {
        this.binding = binding
        loggedIn()
        setUpNavController()
        listenForLoggedInPublisher()
    }

    private fun setUpNavController() {
        navHostFragment =
            (activity as MainActivity).supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        navController.addOnDestinationChangedListener(listener)
    }

    private fun loggedIn() {
        if (isLoggedIn()) {
            loggedInPublisherSubject.onNext(true)
        } else loggedInPublisherSubject.onNext(false)
    }

    private fun isLoggedIn(): Boolean {
        return sharedPrefModule.contain(SharedPrefKey.BearerToken.name)
            .and(sharedPrefModule.getStringFromShared(SharedPrefKey.BearerToken.name).isNotEmpty())
    }

    private fun listenForLoggedInPublisher() {
        loggedInPublisherSubject.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it) setLogInView() else setStartView()
            }
    }

    private fun setStartView() {
        binding.bottomNavLayout.visibility = View.GONE
        navController.setGraph(R.navigation.start_nav)

    }

    private fun setLogInView() {
        navController.setGraph(R.navigation.logged_in_nav)
        binding.bottomNavLayout.visibility = View.VISIBLE
        setUpCustomBottomNav()
        listenForMyProfilePathPublisherSubject()
        myProfilePathPublisherSubject.onNext(
            sharedPrefModule.getStringFromShared(
                SharedPrefKey.ProfileUrl.name
            )
        )
    }


    private fun listenForMyProfilePathPublisherSubject() {
        myProfilePathPublisherSubject.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                glideModule.loadImage(it, binding.myProfileTab)
            }
    }

    private fun setUpCustomBottomNav() {
        clickOnHome()
        clickOnMyBookings()
        clickOnMyPass()
        clickOnNotification()
        clickOnMyProfile()
    }

    private fun clickOnHome() {
        binding.homeTab.setOnClickListener {
            if (binding.homeTab.solidColor == R.color.grey_color)
                initHome()
        }
    }

    private fun initHome() {
        disableAllAndEnableSelected(binding.homeTab.id)
        navigationModule.navigateTo(R.id.home_fragment)
    }

    private fun clickOnMyBookings() {
        binding.myBookingTab.setOnClickListener {
            if (binding.myBookingTab.solidColor == R.color.grey_color)
                initMyBookings()
        }
    }

    private fun initMyBookings() {
        disableAllAndEnableSelected(binding.myBookingTab.id)
        /// TODO missing click on my bookings
    }

    private fun clickOnMyPass() {
        binding.myPassTab.setOnClickListener {
            if (binding.myPassTab.solidColor == R.color.grey_color)
                initMyPass()
        }
    }

    private fun initMyPass() {
        disableAllAndEnableSelected(binding.myPassTab.id)
        /// TODO missing click on my pass
    }

    private fun clickOnNotification() {
        binding.notificationTab.setOnClickListener {
            if (binding.notificationTab.solidColor == R.color.grey_color)
                initMyNotification()
        }
    }

    private fun initMyNotification() {
        disableAllAndEnableSelected(binding.notificationTab.id)
        /// TODO missing click on notification
    }

    private fun clickOnMyProfile() {
        binding.myProfileTab.setOnClickListener {
            if (binding.myProfileTab.solidColor == R.color.grey_color)
                initMyProfile()
        }
    }

    private fun initMyProfile() {
        disableAllAndEnableSelected(binding.myProfileTab.id)
        /// TODO missing click on my profile
    }

    private fun disableAllAndEnableSelected(itemId: Int) {
        disableAllUnselected(itemId)
        enableSelected(itemId)
    }

    private fun disableAllUnselected(itemId: Int) {
        if (itemId != binding.homeTab.id) {
            binding.homeTab.setBackgroundColor(context.getColor(R.color.grey_color))
        }
        if (itemId != binding.myBookingTab.id) {
            binding.myBookingTab.setBackgroundColor(context.getColor(R.color.grey_color))
        }
        if (itemId != binding.notificationTab.id) {
            binding.notificationTab.setBackgroundColor(context.getColor(R.color.grey_color))
        }
    }

    private fun enableSelected(itemId: Int) {
        when (itemId) {
            binding.homeTab.id -> {
                binding.homeTab.setBackgroundColor(context.getColor(R.color.accent_color))
            }
            binding.myBookingTab.id -> {
                binding.myBookingTab.setBackgroundColor(context.getColor(R.color.accent_color))
            }
            binding.notificationTab.id -> {
                binding.notificationTab.setBackgroundColor(context.getColor(R.color.accent_color))
            }
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
}