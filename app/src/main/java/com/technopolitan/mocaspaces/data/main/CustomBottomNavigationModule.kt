package com.technopolitan.mocaspaces.data.main

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.imageview.ShapeableImageView
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
    private val myProfilePathPublisherSubject: PublishSubject<String> = PublishSubject.create()
    private var selectedItemId = 0
    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment
    private var selectedColor: Int = 0
    private var unSelectedColor: Int = 0
    private lateinit var selectedAnimator: ValueAnimator
    private lateinit var unSelectedAnimator: ValueAnimator

    fun init(binding: CustomBottomNavigationLayoutBinding) {
        this.binding = binding
        setUpColorWithAnimator()
        setUpNavController()
        listenForLoggedInPublisher()
    }

    private fun setUpColorWithAnimator() {
        selectedColor = context.getColor(R.color.accent_color)
        unSelectedColor = context.getColor(R.color.grey_color)
        selectedAnimator = ValueAnimator.ofObject(ArgbEvaluator(), unSelectedColor, selectedColor)
        selectedAnimator.duration = 500
        unSelectedAnimator =
            ValueAnimator.ofObject(ArgbEvaluator(), unSelectedColor, unSelectedColor)
        unSelectedAnimator.duration = 500
    }

    private fun setUpNavController() {
        navHostFragment =
            (activity as MainActivity).supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        navController.addOnDestinationChangedListener(listener)
    }



    private fun isLoggedIn(): Boolean {
        return sharedPrefModule.contain(SharedPrefKey.BearerToken.name)
            .and(sharedPrefModule.getStringFromShared(SharedPrefKey.BearerToken.name).isNotEmpty())
    }

    private fun listenForLoggedInPublisher() {
        if (isLoggedIn())
            setLogInView()
        else setStartView()
    }

    private fun setStartView() {
        binding.root.visibility = View.GONE
        navController.setGraph(R.navigation.start_nav)

    }

    private fun setLogInView() {
        navController.setGraph(R.navigation.logged_in_nav)
        binding.bottomNavLayout.visibility = View.VISIBLE
        initHome()
        setUpCustomBottomNav()
        listenForMyProfilePathPublisherSubject()
        updateMyProfileImageView()
    }

    fun updateMyProfileImageView() {
        val imagePath = sharedPrefModule.getStringFromShared(
            SharedPrefKey.ProfileUrl.name
        )
        myProfilePathPublisherSubject.onNext(
            imagePath
        )
    }


    private fun listenForMyProfilePathPublisherSubject() {
        myProfilePathPublisherSubject.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                glideModule.loadImage(it, binding.myProfileTab, R.drawable.ic_person_grey)
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
            if (binding.homeTab.id != selectedItemId)
                initHome()
        }
    }

    private fun initHome() {
        disableAllAndEnableSelected(binding.homeTab.id)
        navigateHome()
    }

    private fun navigateHome() {
        navigationModule.navigateTo(R.id.home_fragment, R.id.nav_host_fragment)
    }

    private fun clickOnMyBookings() {
        binding.myBookingTab.setOnClickListener {
            if (binding.myBookingTab.id != selectedItemId)
                initMyBookings()
        }
    }

    private fun initMyBookings() {
        disableAllAndEnableSelected(binding.myBookingTab.id)
        navigateToMyBooking()
    }

    private fun navigateToMyBooking() {
        navigationModule.navigateTo(R.id.my_bookings_fragment, R.id.nav_host_fragment)
    }

    private fun clickOnMyPass() {
        binding.myPassTab.setOnClickListener {
            if (binding.myPassTab.id != selectedItemId)
                initMyPass()
        }
    }

    private fun initMyPass() {
        disableAllAndEnableSelected(binding.myPassTab.id)
        /// TODO missing click on my pass
    }

    private fun clickOnNotification() {
        binding.notificationTab.setOnClickListener {
            if (binding.notificationTab.id != selectedItemId)
                initMyNotification()
        }
    }

    private fun initMyNotification() {
        disableAllAndEnableSelected(binding.notificationTab.id)
        /// TODO missing click on notification
    }

    private fun clickOnMyProfile() {
        binding.myProfileTab.setOnClickListener {
            if (binding.myProfileTab.id != selectedItemId)
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
            setUnSelected(binding.homeTab)
        }
        if (itemId != binding.myBookingTab.id) {
            setUnSelected(binding.myBookingTab)
        }
        if (itemId != binding.notificationTab.id) {
            setUnSelected(binding.notificationTab)
        }
    }

    private fun setUnSelected(image: ShapeableImageView) {
        selectedAnimator.addUpdateListener { image.setColorFilter(unSelectedAnimator.animatedValue as Int) }
        unSelectedAnimator.start()
    }

    private fun enableSelected(itemId: Int) {
        when (itemId) {
            binding.homeTab.id -> {
                setSelectedItem(binding.homeTab)
            }
            binding.myBookingTab.id -> {
                setSelectedItem(binding.myBookingTab)
            }
            binding.notificationTab.id -> {
                setSelectedItem(binding.notificationTab)
            }
        }
    }

    private fun setSelectedItem(image: ShapeableImageView) {
        selectedItemId = image.id
        selectedAnimator.addUpdateListener { image.setColorFilter(selectedAnimator.animatedValue as Int) }
        selectedAnimator.start()
    }


    private val listener: NavController.OnDestinationChangedListener =
        NavController.OnDestinationChangedListener { _, destination, _ ->
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