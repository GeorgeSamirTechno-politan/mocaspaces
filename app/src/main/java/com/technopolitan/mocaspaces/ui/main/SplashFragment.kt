package com.technopolitan.mocaspaces.ui.main

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.databinding.FragmentSplashBinding
import com.technopolitan.mocaspaces.di.DaggerApplicationComponent
import com.technopolitan.mocaspaces.di.viewModel.ViewModelFactory
import com.technopolitan.mocaspaces.modules.*
import com.technopolitan.mocaspaces.utilities.Constants
import javax.inject.Inject

class SplashFragment : Fragment() {


    @Inject
    lateinit var navigationModule: NavigationModule

    @Inject
    lateinit var dialogModule: DialogModule

    @Inject
    lateinit var appDefaultModel: AppDefaultModule

    @Inject
    lateinit var viewModelFactory: ViewModelFactory


    @Inject
    lateinit var refreshFCMApiHandler: ApiResponseModule<String>

    private lateinit var binding: FragmentSplashBinding
    private lateinit var mainViewModel: MainViewModel


    override fun onAttach(context: Context) {
        DaggerApplicationComponent.factory()
            .buildDi(context, fragment = this, activity = requireActivity()).inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel =
            ViewModelProvider(requireActivity(), viewModelFactory)[MainViewModel::class.java]
        handleApp()
    }

    private fun isRootedRooted(): Boolean {
//        val rootBeer = RootBeer(requireContext())
//        return rootBeer.isRooted || rootBeer.isRootedWithBusyBoxCheck || rootBeer.checkSuExists() || rootBeer.checkForMagiskBinary()
        return false
    }

    private fun handleApp() {
        if (isRootedRooted()) {
            dialogModule.showMessageDialog(
                getString(R.string.rooted_device_message),
                getString(R.string.close_app),
                callBack = {
                    requireActivity().finishAffinity()
                })
        } else {
            appDefaultModel.init {
//                refreshFCMToken()
                handleNavigation()
            }
        }
    }


    private fun refreshFCMToken() {
        mainViewModel.refreshFCMToken(Constants.notificationToken)
        mainViewModel.getRefreshFCMToken().observe(viewLifecycleOwner) {
            refreshFCMApiHandler.handleResponse(it, useProgressDialog = false) {
                handleNavigation()
            }
        }
    }


    private fun handleNavigation() {
        navigationModule.navigateTo(R.id.action_splash_to_start)
    }


}