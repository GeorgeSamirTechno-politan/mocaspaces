package com.technopolitan.mocaspaces.ui.fragmentUtilities

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.technopolitan.mocaspaces.databinding.FragmentNoInternetBinding
import com.technopolitan.mocaspaces.di.DaggerApplicationComponent
import com.technopolitan.mocaspaces.modules.ConnectionLiveDataModule
import com.technopolitan.mocaspaces.modules.NavigationModule
import com.technopolitan.mocaspaces.modules.NetworkModule
import com.technopolitan.mocaspaces.modules.UtilityModule
import javax.inject.Inject


class NoInternetFragment : Fragment() {

    @Inject
    lateinit var networkModule: NetworkModule

    @Inject
    lateinit var connectionLiveDataModule: ConnectionLiveDataModule

    @Inject
    lateinit var navigationModule: NavigationModule

    @Inject
    lateinit var utilityModule: UtilityModule
    private lateinit var fragmentNoInternetBinding: FragmentNoInternetBinding

    override fun onAttach(context: Context) {
        DaggerApplicationComponent.factory().buildDi(context, requireActivity(), this).inject(this)
        super.onAttach(context)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentNoInternetBinding =
            FragmentNoInternetBinding.inflate(layoutInflater, container, false)
//        updateStatusBar()
        return fragmentNoInternetBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerNetworkChangeListener()
        fragmentNoInternetBinding.noInternetSwipeRefresh.setOnRefreshListener(this::listenForOnRefresh)
    }

    private fun listenForOnRefresh() {
        if (networkModule.isInternetAvailable()) {
//            updateStatusBar()
            navigationModule.popBack()
        }
        fragmentNoInternetBinding.noInternetSwipeRefresh.isRefreshing = false
    }

//    private fun updateStatusBar(colorId: Int = R.color.white) {
//        utilityModule.setStatusBar(
//            requireContext().getColor(colorId)
//        )
//    }

    private fun registerNetworkChangeListener() {
        connectionLiveDataModule.observe(viewLifecycleOwner) {
//            updateStatusBar(R.color.accent_color)
            if (it) navigationModule.popBack()

        }
    }


}