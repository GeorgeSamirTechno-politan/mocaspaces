package com.technopolitan.mocaspaces.ui.workspacePlans

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.data.workSpacePlans.WorkSpacePlansAdapter
import com.technopolitan.mocaspaces.databinding.FragmentWorkSpacePlansBinding
import com.technopolitan.mocaspaces.di.DaggerApplicationComponent
import com.technopolitan.mocaspaces.di.viewModel.ViewModelFactory
import com.technopolitan.mocaspaces.models.workSpace.WorkSpacePlanMapper
import com.technopolitan.mocaspaces.modules.ApiResponseModule
import com.technopolitan.mocaspaces.ui.locationDetails.LocationDetailsViewModel
import javax.inject.Inject

class WorkSpacePlansFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var apiHandler: ApiResponseModule<List<WorkSpacePlanMapper>>

    private lateinit var workSpacePlansAdapter: WorkSpacePlansAdapter

    private lateinit var locationDetailsViewModel: LocationDetailsViewModel
    private lateinit var viewModel: WorkSpacePlansViewModel
    private lateinit var binding: FragmentWorkSpacePlansBinding


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
    }

    private fun initView() {
        binding.planAppBar.appToolBar.setNavigationIconTint(requireContext().getColor(R.color.workspace_color))
        binding.planAppBar.appBarLayout.setBackgroundColor(requireContext().getColor(R.color.accent_color))
        binding.planAppBar.appToolBar.subtitle = requireContext().getString(R.string.back)
        binding.planAppBar.appToolBar.setSubtitleTextColor(requireContext().getColor(R.color.workspace_color))
    }

    private fun callApi() {
        viewModel.setPlanRequest(
            locationDetailsViewModel.getPriceResponse(),
            locationDetailsViewModel.getCurrency()
        )
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
            /// TODO missing call back
        }
        workSpacePlansAdapter.setData(list.toMutableList())
        binding.workSpacePlanRecycler.adapter = workSpacePlansAdapter
    }

}