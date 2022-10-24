package com.technopolitan.mocaspaces.ui.bookingSharedFragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.technopolitan.mocaspaces.databinding.FragmentCalenderBinding
import com.technopolitan.mocaspaces.di.DaggerApplicationComponent
import com.technopolitan.mocaspaces.di.viewModel.ViewModelFactory
import com.technopolitan.mocaspaces.models.booking.DayMapper
import com.technopolitan.mocaspaces.modules.ApiResponseModule
import javax.inject.Inject


class CalenderFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var dayApiHandler: ApiResponseModule<List<DayMapper>>

    private lateinit var viewModel: CalenderViewModel

    private lateinit var binding: FragmentCalenderBinding

    override fun onAttach(context: Context) {
        DaggerApplicationComponent.factory().buildDi(requireContext(), requireActivity(), this)
            .inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalenderBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =
            ViewModelProvider(requireActivity(), viewModelFactory)[CalenderViewModel::class.java]
        viewModel.initSingleMultiDateModule(binding.hourlySingleMultiBookingLayout)
        viewModel.initMonthDayDataModule(binding.hourlyMonthDayLayout)
        listenForDayApi()
    }

    private fun listenForDayApi() {
        viewModel.listenForCalenderApi().observe(viewLifecycleOwner) {
            dayApiHandler.handleResponse(
                it,
                binding.hourlyMonthDayLayout.dayProgress.progressView,
                binding.hourlyMonthDayLayout.dayRecycler
            ) { list ->
                viewModel.setDayList(list)
            }
        }
    }

}