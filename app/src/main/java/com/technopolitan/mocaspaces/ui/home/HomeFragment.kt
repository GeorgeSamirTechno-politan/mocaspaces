package com.technopolitan.mocaspaces.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.data.home.HomeSearchAdapter
import com.technopolitan.mocaspaces.data.home.HomeSearchMapper
import com.technopolitan.mocaspaces.data.home.HomeViewPagerAdapter
import com.technopolitan.mocaspaces.databinding.HomeFragmentBinding
import com.technopolitan.mocaspaces.di.DaggerApplicationComponent
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
    lateinit var viewModel: HomeViewModel

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

    private val homeSearchMapperList: MutableList<HomeSearchMapper> = mutableListOf()

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
            if (it) {
                locationModule.init({ location ->
                    viewModel.setLocation(location)
                })
                handleHomeFragment()
            } else
                handleHomeFragment()

        }
    }


    private fun handleHomeFragment() {
        binding.homeFragmentViewPager.adapter = homeViewPagerAdapter
        binding.homeFragmentViewPager.registerOnPageChangeCallback(pageChangeCallBack)
        handleSearchHintApi()
    }

    private fun handleSearchHintApi() {
        viewModel.getSearchHintApi().observe(viewLifecycleOwner) {
            searchHintApiHandler.handleResponse(
                it,
                binding.searchProgress.progressView,
                binding.homeSearchViewPager
            ) { data ->
                homeSearchAdapter.setSearchHintList(data)
                initHomeSearchMapperList()
            }
        }

    }

    private fun searchMapperList() {
        homeSearchMapperList.add(
            HomeSearchMapper(
                R.color.workspace_color,
                requireActivity().getString(R.string.workspace),
                0,
                R.color.text_input_box_work_space_color,
                R.drawable.work_space_bottom_rounded_corner
            )
        )
        homeSearchMapperList.add(
            HomeSearchMapper(
                R.color.meeting_space_color,
                requireActivity().getString(R.string.meeting_space),
                1,
                R.color.text_input_box_meeting_space_color,
                R.drawable.meeting_space_bottom_rounded_corner
            )
        )
        homeSearchMapperList.add(
            HomeSearchMapper(
                R.color.event_space_color,
                requireActivity().getString(R.string.event_space),
                2,
                R.color.text_input_box_event_space_color,
                R.drawable.event_space_bottom_rounded_corner
            )
        )
        homeSearchMapperList.add(
            HomeSearchMapper(
                R.color.biz_lounge_color,
                requireActivity().getString(R.string.biz_lounge),
                3,
                R.color.text_input_box_biz_lounge_color,
                R.drawable.biz_lounge_bottom_rounded_corner
            )
        )
    }

    private fun initHomeSearchMapperList() {
        searchMapperList()
        homeSearchAdapter.setList(homeSearchMapperList)
        binding.homeSearchViewPager.adapter = homeSearchAdapter
        binding.homeSearchViewPager.offscreenPageLimit = 1
        binding.homeSearchViewPager.setPageTransformer(utilityModule.getPageTransformationForViewPager2())
        binding.homeSearchViewPager.registerOnPageChangeCallback(searchPageChangeCallBack)
    }

    private val pageChangeCallBack: ViewPager2.OnPageChangeCallback = object :
        ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            viewModel.setViewType(position + 1)
//            binding.homeSearchRecycler.smoothScrollToPosition(position)
            binding.homeSearchViewPager.setCurrentItem(position, true)
        }
    }

    private val searchPageChangeCallBack: ViewPager2.OnPageChangeCallback = object :
        ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            viewModel.setViewType(position + 1)
            binding.homeFragmentViewPager.setCurrentItem(position, true)

        }
    }

}