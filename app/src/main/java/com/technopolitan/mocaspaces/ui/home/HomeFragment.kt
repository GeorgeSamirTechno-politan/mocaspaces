package com.technopolitan.mocaspaces.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.technopolitan.mocaspaces.data.home.HomeSearchAdapter
import com.technopolitan.mocaspaces.data.home.HomeViewPagerAdapter
import com.technopolitan.mocaspaces.databinding.HomeFragmentBinding
import com.technopolitan.mocaspaces.di.DaggerApplicationComponent
import com.technopolitan.mocaspaces.di.viewModel.ViewModelFactory
import com.technopolitan.mocaspaces.models.location.mappers.SearchHintMapper
import com.technopolitan.mocaspaces.modules.ApiResponseModule
import com.technopolitan.mocaspaces.modules.LocationModule
import com.technopolitan.mocaspaces.modules.PermissionModule
import com.technopolitan.mocaspaces.modules.UtilityModule
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
    lateinit var homeViewPagerAdapter: HomeViewPagerAdapter

    @Inject
    lateinit var locationModule: LocationModule

    @Inject
    lateinit var searchHintApiHandler: ApiResponseModule<List<SearchHintMapper>>

    @Inject
    lateinit var homeSearchAdapter: HomeSearchAdapter

    @Inject
    lateinit var utilityModule: UtilityModule

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    //    @Inject
    lateinit var viewModel: HomeViewModel


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
        viewModel =
            ViewModelProvider(requireActivity(), viewModelFactory)[HomeViewModel::class.java]
        requestLocationPermission()
    }

    private fun requestLocationPermission() {
        val permissionList: MutableList<String> = mutableListOf()
        permissionList.add(android.Manifest.permission.ACCESS_FINE_LOCATION)
        permissionList.add(android.Manifest.permission.ACCESS_COARSE_LOCATION)
        permissionModule.init(permissionList, requiredPermission = false) {
            if (it) {
                locationModule.init({ location ->
                    viewModel.setLocation(location)
                    handleHomeFragment()
                })
            } else {
                viewModel.setLocation(null)
                handleHomeFragment()
            }


        }
    }

    private fun handleHomeFragment() {
        handleSearchHintApi()
        initHomeSearchMapperList()
        listenForSearchHintListChange()
        handleDownList()
    }

    private fun listenForSearchHintListChange() {
        viewModel.getSearchHintListLiveData().observe(viewLifecycleOwner) {
            homeSearchAdapter.setSearchHintList(it)
        }
    }

    private fun handleSearchHintApi() {
        viewModel.getSearchHintApi().observe(viewLifecycleOwner) {
            searchHintApiHandler.handleResponse(
                it,
                binding.searchProgress.progressView,
                binding.homeSearchViewPager
            ) { data ->
                viewModel.updateSearchHintList()

            }
        }
    }

    private fun handleDownList() {
        viewModel.initAllHomeRequest()
        binding.homeFragmentViewPager.adapter = homeViewPagerAdapter
        binding.homeFragmentViewPager.registerOnPageChangeCallback(pageChangeCallBack)
    }


    private fun initHomeSearchMapperList() {
        homeSearchAdapter.setList(viewModel.getHomeSearchMapperList())
        binding.homeSearchViewPager.adapter = homeSearchAdapter
        homeSearchAdapter.setSearchCallBack {
            viewModel.setSearchHint(it)
        }
        binding.homeSearchViewPager.offscreenPageLimit = 1
        binding.homeSearchViewPager.setPageTransformer(utilityModule.getPageTransformationForViewPager2())
        binding.homeSearchViewPager.registerOnPageChangeCallback(searchPageChangeCallBack)
    }

    private val pageChangeCallBack: ViewPager2.OnPageChangeCallback = object :
        ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            updateViewType(position)
        }
    }

    private val searchPageChangeCallBack: ViewPager2.OnPageChangeCallback = object :
        ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            updateViewType(position)
        }
    }

    private fun updateViewType(position: Int) {
        viewModel.setViewType(position + 1)
        binding.homeFragmentViewPager.setCurrentItem(position, true)
        binding.homeSearchViewPager.setCurrentItem(position, true)
        viewModel.updateSearchHintList()
    }

}