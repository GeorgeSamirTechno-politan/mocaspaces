package com.technopolitan.mocaspaces.ui.hourlyBooking

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.databinding.FragmentHourlyBookingBinding
import com.technopolitan.mocaspaces.di.DaggerApplicationComponent
import com.technopolitan.mocaspaces.di.viewModel.ViewModelFactory
import com.technopolitan.mocaspaces.modules.NavigationModule
import com.technopolitan.mocaspaces.ui.bookingSharedFragment.CalenderFragment
import com.technopolitan.mocaspaces.ui.bookingSharedFragment.CalenderViewModel
import com.technopolitan.mocaspaces.ui.locationDetails.LocationDetailsViewModel
import javax.inject.Inject

class HourlyBookingFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var navigationModule: NavigationModule

    private lateinit var viewModel: HourlyBookingViewModel
    private lateinit var calenderViewModel: CalenderViewModel
    private lateinit var binding: FragmentHourlyBookingBinding
    private lateinit var detailsViewModel: LocationDetailsViewModel

    override fun onAttach(context: Context) {
        DaggerApplicationComponent.factory().buildDi(requireContext(), requireActivity(), this)
            .inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHourlyBookingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        viewModel = ViewModelProvider(this, viewModelFactory)[HourlyBookingViewModel::class.java]
        calenderViewModel =
            ViewModelProvider(requireActivity(), viewModelFactory)[CalenderViewModel::class.java]
        detailsViewModel = ViewModelProvider(
            requireActivity(),
            viewModelFactory
        )[LocationDetailsViewModel::class.java]
        calenderViewModel.setLocationId(detailsViewModel.getLocationId())
        initToolBar()
        initCalenderFragment()
    }

    private fun initToolBar() {
        binding.bookingHourlyToolBar.appToolBar.setNavigationIconTint(requireContext().getColor(R.color.workspace_color))
        binding.bookingHourlyToolBar.appToolBar.setBackgroundColor(requireContext().getColor(R.color.accent_color))
        binding.bookingHourlyToolBar.appToolBar.subtitle =
            requireContext().getString(R.string.choose_a_plan)
        binding.bookingHourlyToolBar.appToolBar.setSubtitleTextColor(requireContext().getColor(R.color.workspace_color))
        binding.bookingHourlyToolBar.appToolBar.setNavigationOnClickListener {
            navigationModule.popBack()
        }
    }

    private fun initCalenderFragment() {
        childFragmentManager.beginTransaction()
            .replace(binding.calenderFragmentHost.id, CalenderFragment()).commit()
    }


}