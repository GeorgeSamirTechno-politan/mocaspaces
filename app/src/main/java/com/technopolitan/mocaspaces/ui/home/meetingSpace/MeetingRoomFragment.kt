package com.technopolitan.mocaspaces.ui.home.meetingSpace

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.technopolitan.mocaspaces.data.home.MeetingRoomAdapter
import com.technopolitan.mocaspaces.databinding.FragmentMeetingRoomBinding
import com.technopolitan.mocaspaces.di.DaggerApplicationComponent
import com.technopolitan.mocaspaces.di.viewModel.ViewModelFactory
import com.technopolitan.mocaspaces.models.meeting.MeetingRoomMapper
import com.technopolitan.mocaspaces.modules.ApiResponseModule
import com.technopolitan.mocaspaces.ui.home.HomeViewModel
import com.technopolitan.mocaspaces.utilities.loadMore
import javax.inject.Inject

class MeetingRoomFragment : Fragment() {
    lateinit var binding: FragmentMeetingRoomBinding

    @Inject
    lateinit var meetingRoomAdapter: MeetingRoomAdapter

    @Inject
    lateinit var meetingSpaceApiHandler: ApiResponseModule<List<MeetingRoomMapper>>

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var meetingRoomViewModel: MeetingRoomViewModel

    override fun onAttach(context: Context) {
        DaggerApplicationComponent.factory().buildDi(requireContext(), requireActivity(), this)
            .inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMeetingRoomBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel =
            ViewModelProvider(requireActivity(), viewModelFactory)[HomeViewModel::class.java]
        meetingRoomViewModel =
            ViewModelProvider(requireActivity(), viewModelFactory)[MeetingRoomViewModel::class.java]
        listenForMediators()
        initView()

    }

    private fun listenForMediators() {
        listenForFilter()
        listForWorkSpaceListMediator()
    }


    private fun listenForFilter() {
        homeViewModel.getMeetingSpaceFilterLiveData().observe(viewLifecycleOwner) {
            clearAdapter()
            meetingRoomViewModel.setFilter(type = it.type, id = it.id)
            initWorkSpaceLiveData()
        }
    }


    private fun initView() {
        listenForScrolling()
        listenForSwipeToRefresh()
    }

    private fun listenForSwipeToRefresh() {
        binding.meetingRefreshLayout.setOnRefreshListener {
            meetingRoomViewModel.refresh()
            clearAdapter()
//            homeViewModel.setSearchHint(SearchHintMapper())
            binding.meetingRefreshLayout.isRefreshing = false
        }
    }

    private fun clearAdapter() {
        meetingRoomAdapter.clearAdapter()
    }

    private fun listenForScrolling() {
        binding.meetingRecycler.loadMore {
            if (meetingRoomViewModel.hasLoadMore() && meetingRoomAdapter.itemCount > 0)
                meetingRoomViewModel.loadMore()
        }
    }


    private fun initWorkSpaceLiveData() {
        meetingRoomViewModel.getMeetingSpaceApi().observe(viewLifecycleOwner) { response ->
            if (meetingRoomAdapter.itemCount == 0) {
                meetingSpaceApiHandler.handleResponse(
                    response,
                    binding.meetingProgress.progressView,
                ) {
                    meetingRoomViewModel.updateMeetingSpaceRemainingPage(response.remainingPage)
                    meetingRoomViewModel.setMeetingSpaceListMediator(it.toMutableList())
                    meetingRoomViewModel.removeSourceFromMeetingSpaceApi()
                }
            } else {
                meetingSpaceApiHandler.handleResponse(response) {
                    meetingRoomViewModel.updateMeetingSpaceRemainingPage(response.remainingPage)
                    meetingRoomViewModel.setMeetingSpaceListMediator(response.data!!.toMutableList())
                    meetingRoomViewModel.removeSourceFromMeetingSpaceApi()
                }
            }
        }
    }

    private fun listForWorkSpaceListMediator() {
        meetingRoomViewModel.getMeetingSpaceListLiveData().observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                meetingRoomAdapter.setData(it)
                binding.meetingRecycler.adapter = meetingRoomAdapter
                binding.meetingRecycler.setHasFixedSize(true)
            }
        }
    }


}