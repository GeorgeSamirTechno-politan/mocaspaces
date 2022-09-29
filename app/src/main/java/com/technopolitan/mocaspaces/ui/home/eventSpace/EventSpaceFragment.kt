package com.technopolitan.mocaspaces.ui.home.eventSpace

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.technopolitan.mocaspaces.data.home.MeetingRoomAdapter
import com.technopolitan.mocaspaces.databinding.FragmentEventSpaceBinding
import com.technopolitan.mocaspaces.di.DaggerApplicationComponent
import com.technopolitan.mocaspaces.di.viewModel.ViewModelFactory
import com.technopolitan.mocaspaces.models.location.mappers.LocationPaxMapper
import com.technopolitan.mocaspaces.models.meeting.MeetingRoomMapper
import com.technopolitan.mocaspaces.modules.ApiResponseModule
import com.technopolitan.mocaspaces.ui.home.HomeViewModel
import com.technopolitan.mocaspaces.utilities.Constants
import com.technopolitan.mocaspaces.utilities.loadMore
import javax.inject.Inject

class EventSpaceFragment : Fragment() {
    lateinit var binding: FragmentEventSpaceBinding

    @Inject
    lateinit var eventSpaceAdapter: MeetingRoomAdapter

    @Inject
    lateinit var eventSpaceApiHandler: ApiResponseModule<List<MeetingRoomMapper?>>

    @Inject
    lateinit var paxApiHandler: ApiResponseModule<List<LocationPaxMapper>>

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var eventSpaceViewModel: EventSpaceViewModel

    override fun onAttach(context: Context) {
        DaggerApplicationComponent.factory().buildDi(requireContext(), requireActivity(), this)
            .inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEventSpaceBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel =
            ViewModelProvider(requireActivity(), viewModelFactory)[HomeViewModel::class.java]
        eventSpaceViewModel =
            ViewModelProvider(requireActivity(), viewModelFactory)[EventSpaceViewModel::class.java]
        listenForMediators()
        initView()
    }

    private fun listenForMediators() {
        listenForFilter()
        listForAdapterMediator()
        listenForPaxFilter()
        listenForBackFromDetails()
    }


    private fun listenForFilter() {
        homeViewModel.getEventSpaceFilterLiveData().observe(viewLifecycleOwner) {
            clearAdapter()
            eventSpaceViewModel.setFilter(type = it.type, id = it.id)
            initApiLiveData()
        }
    }


    private fun initView() {
        listenForScrolling()
        listenForSwipeToRefresh()
    }

    private fun listenForSwipeToRefresh() {
        binding.eventRefreshLayout.setOnRefreshListener {
            eventSpaceViewModel.refresh()
            clearAdapter()
//            homeViewModel.setSearchHint(SearchHintMapper())
            binding.eventRefreshLayout.isRefreshing = false
        }
    }

    private fun clearAdapter() {
        eventSpaceAdapter.clearAdapter()
    }

    private fun listenForScrolling() {
        binding.eventRecycler.loadMore {
            if (eventSpaceViewModel.hasLoadMore() && eventSpaceAdapter.itemCount > 0)
                eventSpaceViewModel.loadMore()
        }
    }


    private fun initApiLiveData() {
        eventSpaceViewModel.getEventSpaceApi().observe(viewLifecycleOwner) { response ->
            if (eventSpaceAdapter.itemCount == 0) {
                eventSpaceApiHandler.handleResponse(
                    response,
                    binding.eventProgress.progressView,
                ) {
                    eventSpaceViewModel.updateEventSpaceRemainingPage(response.remainingPage)
                    eventSpaceViewModel.setEventSpaceListMediator(it.toMutableList())
                    eventSpaceViewModel.removeSourceFromEventSpaceApi()
                }
            } else {
                eventSpaceApiHandler.handleResponse(response) {
                    eventSpaceViewModel.updateEventSpaceRemainingPage(response.remainingPage)
                    eventSpaceViewModel.setEventSpaceListMediator(response.data!!.toMutableList())
                    eventSpaceViewModel.removeSourceFromEventSpaceApi()
                }
            }
        }
    }

    private fun listForAdapterMediator() {
        eventSpaceViewModel.getEventSpaceListLiveData().observe(viewLifecycleOwner) {
            eventSpaceAdapter.setData(it)
            binding.eventRecycler.adapter = eventSpaceAdapter
            binding.eventRecycler.setHasFixedSize(true)
        }
    }

    private fun listenForPaxFilter() {
        eventSpaceViewModel.getPaxLiveData().observe(viewLifecycleOwner) {
            paxApiHandler.handleResponse(
                it, binding.eventRoomPaxFilter.paxFilterProgress.progressView,
                binding.eventRoomPaxFilter.filterLayout
            ) { list ->
                initPaxFilter(list)
            }
        }
    }

    private fun initPaxFilter(list: List<LocationPaxMapper>) {
        eventSpaceViewModel.initPaxFilterDataModule(
            binding.eventRoomPaxFilter,
            Constants.eventTypeId, list
        ) {
            clearAdapter()
        }
    }

    private fun listenForBackFromDetails() {
        homeViewModel.getBackFromDetailsLiveData().observe(viewLifecycleOwner) {
            if (it) {
                eventSpaceViewModel.updateDataAgainToView()
                binding.eventProgress.progressView.visibility = View.GONE
            }
        }
    }
}