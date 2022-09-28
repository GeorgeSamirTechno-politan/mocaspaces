package com.technopolitan.mocaspaces.modules

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import dagger.Module
import javax.inject.Inject


@Module
class NavigationModule @Inject constructor(
    private var fragment: Fragment?,
    private var activity: Activity
) {

    val builder = NavOptions.Builder()
    private var options: NavOptions
    private val fragmentNavController: NavController? =
        fragment?.let { NavHostFragment.findNavController(it) }

    init {
        builder.setEnterAnim(androidx.navigation.ui.R.anim.nav_default_enter_anim)
            .setExitAnim(androidx.navigation.ui.R.anim.nav_default_exit_anim)
            .setPopEnterAnim(androidx.navigation.ui.R.anim.nav_default_pop_enter_anim)
            .setPopExitAnim(androidx.navigation.ui.R.anim.nav_default_pop_exit_anim)
        options = builder.build()

    }

    private fun activityNavController(navHostId: Int?) =
        Navigation.findNavController(activity, navHostId!!)


    fun navigateTo(destination: Int, navHostId: Int? = null, bundle: Bundle? = null) {
        try {
            if (navHostId != null)
                activityNavController(navHostId).navigate(destination, bundle, options)
            else fragment?.let { fragmentNavController?.navigate(destination, bundle, options) }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun popBack(destination: Int? = null, inclusive: Boolean? = null, navHostId: Int? = null) {
        try {
            if (navHostId != null) {
                if (inclusive != null && destination != null)
                    activityNavController(navHostId).popBackStack(destination, inclusive)
                else activityNavController(navHostId).popBackStack()

            } else fragment?.let {
                if (inclusive != null && destination != null)
                    fragmentNavController?.popBackStack(destination, inclusive)
                else fragmentNavController?.popBackStack()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun savedStateHandler(destinationId: Int, navHostId: Int? = null): SavedStateHandle? {
        try {
            if (navHostId == null) {
                if (fragmentNavController != null)
                    if ((fragmentNavController.currentDestination)?.id == destinationId)
                        return fragmentNavController.getBackStackEntry(destinationId).savedStateHandle

            } else {
                if (activityNavController(navHostId).currentDestination.let { it?.id == destinationId }
                ) return activityNavController(navHostId)
                    .getBackStackEntry(destinationId).savedStateHandle
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun navBackStackEntry(navHostId: Int? = null): NavBackStackEntry? {
        return try {
            if (navHostId == null)
                fragmentNavController?.currentBackStackEntry
            else activityNavController(navHostId).currentBackStackEntry
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

    }


    fun hasBackStack(navHostId: Int? = null): Boolean = when (navHostId) {
        null -> fragmentNavController?.backQueue!!.size > 2
        else -> activityNavController(navHostId).backQueue.size > 2
    }

    fun setUpNavigationWithNavigationView(
        navigationView: NavigationView?,
        navController: NavController?
    ) {
        try {
            setupWithNavController(navigationView!!, navController!!)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setUpNavigationWithBottomNavigationView(
        bottomNavigationView: BottomNavigationView?,
        navController: NavController?
    ) {
        try {
            setupWithNavController(bottomNavigationView!!, navController!!)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}