package com.technopolitan.mocaspaces.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.technopolitan.mocaspaces.data.home.HomeViewPagerAdapter
import com.technopolitan.mocaspaces.databinding.HomeFragmentBinding
import com.technopolitan.mocaspaces.di.DaggerApplicationComponent
import com.technopolitan.mocaspaces.modules.LocationModule
import com.technopolitan.mocaspaces.modules.PermissionModule
import javax.inject.Inject

class HomeFragment : Fragment() {

    override fun onAttach(context: Context) {
        DaggerApplicationComponent.factory().buildDi(requireContext(), requireActivity(), this)
            .inject(this)
        super.onAttach(context)
    }

    @Inject
    lateinit var permissionModule: PermissionModule

    @Inject
    lateinit var viewModel: HomeViewModel

    @Inject
    lateinit var homeViewPagerAdapter: HomeViewPagerAdapter

    @Inject
    lateinit var locationModule: LocationModule

    private lateinit var binding: HomeFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestLocationPermission()
    }

    private fun requestLocationPermission() {
        val permissionList: MutableList<String> = mutableListOf()
        permissionList.add(android.Manifest.permission.ACCESS_FINE_LOCATION)
        permissionList.add(android.Manifest.permission.ACCESS_COARSE_LOCATION)
        permissionModule.init(permissionList, requiredPermission = false) {
            if(it){
                locationModule.init({location->
                    viewModel.setLocation(location)
                })
                handleHomeFragment()
            }else
                handleHomeFragment()

        }
    }

    private fun handleHomeFragment() {
        binding.homeFragmentViewPager.adapter = homeViewPagerAdapter
        binding.homeFragmentViewPager.registerOnPageChangeCallback(pageChangeCallBack)
    }

    private val pageChangeCallBack: ViewPager2.OnPageChangeCallback = object :
        ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            viewModel.setViewType(position+ 1)
        }
    }

}