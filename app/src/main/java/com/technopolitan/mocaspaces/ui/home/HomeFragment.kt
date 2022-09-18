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

    @Inject
    lateinit var homeSearchAdapter: HomeSearchAdapter

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
            if(it){
                locationModule.init({ location ->
                    viewModel.setLocation(location)
                })
                handleHomeFragment()
            } else
                handleHomeFragment()

        }
    }

    private fun initHomeSearchMapperList() {
        homeSearchMapperList.add(
            HomeSearchMapper(
                R.color.workspace_color,
                requireActivity().getString(R.string.workspace),
                0
            )
        )
        homeSearchMapperList.add(
            HomeSearchMapper(
                R.color.meeting_color,
                requireActivity().getString(R.string.meeting_space),
                1
            )
        )
        homeSearchMapperList.add(
            HomeSearchMapper(
                R.color.event_color,
                requireActivity().getString(R.string.event_space),
                2
            )
        )
        homeSearchMapperList.add(
            HomeSearchMapper(
                R.color.biz_lounge_color,
                requireActivity().getString(R.string.biz_lounge),
                3
            )
        )
        homeSearchAdapter.setList(homeSearchMapperList)
        binding.homeSearchViewPager.adapter = homeSearchAdapter
        binding.homeSearchViewPager.offscreenPageLimit = 1
        val nextItemVisiblePx = resources.getDimension(com.intuit.sdp.R.dimen._30sdp)
        val currentItemHorizontalMarginPx = resources.getDimension(com.intuit.sdp.R.dimen._15sdp)
        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
        val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
            page.translationX = -pageTranslationX * position
            // Next line scales the item's height. You can remove it if you don't want this effect
//            page.scaleY = 1 - (0.25f * abs(position))
            // If you want a fading effect uncomment the next line:
            // page.alpha = 0.25f + (1 - abs(position))
        }
        binding.homeSearchViewPager.setPageTransformer(pageTransformer)
        binding.homeSearchViewPager.registerOnPageChangeCallback(searchPageChangeCallBack)
    }


    private fun handleHomeFragment() {
        binding.homeFragmentViewPager.adapter = homeViewPagerAdapter
        binding.homeFragmentViewPager.registerOnPageChangeCallback(pageChangeCallBack)
        initHomeSearchMapperList()
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