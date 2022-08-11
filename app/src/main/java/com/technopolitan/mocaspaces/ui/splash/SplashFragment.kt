package com.technopolitan.mocaspaces.ui.splash

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.databinding.FragmentSplashBinding
import com.technopolitan.mocaspaces.di.DaggerApplicationComponent
import com.technopolitan.mocaspaces.modules.NavigationModule
import com.technopolitan.mocaspaces.modules.PermissionModule
import javax.inject.Inject

class SplashFragment : Fragment() {
    lateinit var fragmentSplashBinding: FragmentSplashBinding

    @Inject
    lateinit var splashViewModel: SplashViewModel

    @Inject
    lateinit var navigationModule: NavigationModule

    @Inject
    lateinit var permissionModule: PermissionModule

    override fun onAttach(context: Context) {
        DaggerApplicationComponent.factory()
            .buildDi(context, fragment = this, activity = requireActivity()).inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentSplashBinding = FragmentSplashBinding.inflate(layoutInflater, container, false)
        return fragmentSplashBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        accessNetworkStatusPermission()
    }

    private fun accessNetworkStatusPermission() {
        permissionModule.requestPermission(permissionName = android.Manifest.permission.ACCESS_NETWORK_STATE,
            requestCode = 1,
            headerPermissionName = getString(R.string.network_status_permission_header),
            detailsMessage = getString(R.string.network_Status_permission_message),
            callBack = {
                navigationModule.navigateTo(R.id.action_splash_to_start)
            })
    }

}