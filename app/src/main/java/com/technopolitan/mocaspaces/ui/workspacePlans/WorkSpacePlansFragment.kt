package com.technopolitan.mocaspaces.ui.workspacePlans

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.data.workSpacePlans.WorkSpacePlansAdapter
import com.technopolitan.mocaspaces.databinding.FragmentWorkSpacePlansBinding
import com.technopolitan.mocaspaces.di.DaggerApplicationComponent
import com.technopolitan.mocaspaces.di.viewModel.ViewModelFactory
import com.technopolitan.mocaspaces.models.workSpace.WorkSpacePlanMapper
import com.technopolitan.mocaspaces.modules.ApiResponseModule
import com.technopolitan.mocaspaces.modules.NavigationModule
import com.technopolitan.mocaspaces.ui.locationDetails.LocationDetailsViewModel
import com.technopolitan.mocaspaces.utilities.BookingType
import com.technopolitan.mocaspaces.utilities.Constants
import com.technopolitan.mocaspaces.utilities.cardStackRecycleView.*
import javax.inject.Inject

class WorkSpacePlansFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var apiHandler: ApiResponseModule<List<WorkSpacePlanMapper>>

    @Inject
    lateinit var navigationModule: NavigationModule

    private lateinit var workSpacePlansAdapter: WorkSpacePlansAdapter

    private lateinit var locationDetailsViewModel: LocationDetailsViewModel
    private lateinit var viewModel: WorkSpacePlansViewModel
    private lateinit var binding: FragmentWorkSpacePlansBinding
    private lateinit var cardStackLayoutManager: CardStackLayoutManager


    override fun onAttach(context: Context) {
        DaggerApplicationComponent.factory().buildDi(requireContext(), requireActivity(), this)
            .inject(this)
        super.onAttach(context)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWorkSpacePlansBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        locationDetailsViewModel = ViewModelProvider(
            requireActivity(),
            viewModelFactory
        )[LocationDetailsViewModel::class.java]
        viewModel = ViewModelProvider(this, viewModelFactory)[WorkSpacePlansViewModel::class.java]
        initView()
        callApi()
        listenForApi()
    }

    private fun initView() {
        binding.planAppBar.appToolBar.setNavigationIconTint(requireContext().getColor(R.color.workspace_color))
        binding.planAppBar.appToolBar.setBackgroundColor(requireContext().getColor(R.color.accent_color))
        binding.planAppBar.appToolBar.subtitle = requireContext().getString(R.string.back)
        binding.planAppBar.appToolBar.setSubtitleTextColor(requireContext().getColor(R.color.workspace_color))
        binding.planAppBar.appToolBar.setNavigationOnClickListener {
            navigationModule.popBack()
        }
    }

    private fun callApi() {
        viewModel.setPlanRequest(
            locationDetailsViewModel.getPriceResponse(),
            locationDetailsViewModel.getCurrency()
        )
    }

    private fun listenForApi() {
        viewModel.getWorkSpacePlan().observe(viewLifecycleOwner) {
            apiHandler.handleResponse(
                it,
                binding.workSpacePlanProgress.progressView,
                binding.workSpacePlanRecycler
            ) { data ->
                setAdapter(data)
            }
        }
    }

    private fun setAdapter(list: List<WorkSpacePlanMapper>) {
        workSpacePlansAdapter = WorkSpacePlansAdapter(requireContext()) {
            when (it.planId) {
                BookingType.hourlyTypeId -> navigationModule.navigateTo(R.id.action_work_space_plans_fragment_to_hourly_booking)
            }
        }
        list.forEach { _ ->
            binding.dotsIndicator.addTab(binding.dotsIndicator.newTab())
        }
        workSpacePlansAdapter.setData(list.toMutableList())
        binding.workSpacePlanRecycler.layoutManager = getDefaultLayoutManager(list)
        binding.workSpacePlanRecycler.adapter = workSpacePlansAdapter
    }

    private fun getDefaultLayoutManager(list: List<WorkSpacePlanMapper>): CardStackLayoutManager {
        cardStackLayoutManager = CardStackLayoutManager(requireContext()).apply {
            setOverlayInterpolator(LinearInterpolator())
            if (Constants.appLanguage == "en")
                setStackFrom(StackFrom.Right)
            else setStackFrom(StackFrom.Left)
            setVisibleCount(4)
//            setDirections(Direction.HORIZONTAL)
            setTranslationInterval(12.0f)
            setScaleInterval(0.95f)
            setMaxDegree(-180.0f)
            setSwipeThreshold(0.1f)
            setCanScrollHorizontal(true)
            setCanScrollVertical(false)
            listenForTopPositionChange { updateViewPagerIndicator(it, list) }
            setSwipeAnimationSetting(getSwipeAnimationSetting())
            setRewindAnimationSetting(getRewindAnimationSetting())
            setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
        }
        return cardStackLayoutManager
    }

    private fun getSwipeAnimationSetting(): SwipeAnimationSetting =
        SwipeAnimationSetting.Builder()
            .setDirection(rewindDirection())
            .setDuration(Duration.Slow.duration)
            .setInterpolator(AccelerateInterpolator())
            .build()


    private fun getRewindAnimationSetting(): RewindAnimationSetting =
        RewindAnimationSetting.Builder()
            .setDirection(swipeDirection())
            .setDuration(Duration.Slow.duration)
            .setInterpolator(DecelerateInterpolator())
            .build()

    private fun swipeDirection() =
        if (Constants.appLanguage == "en") Direction.Bottom else Direction.Left

    private fun rewindDirection() =
        if (Constants.appLanguage == "en") Direction.Top else Direction.Right

    private fun updateViewPagerIndicator(position: Int, list: List<WorkSpacePlanMapper>) {
        Log.d(javaClass.name, "updateViewPagerIndicator: $position")
        binding.dotsIndicator.setScrollPosition(position % list.size, 0f, true)
    }

}