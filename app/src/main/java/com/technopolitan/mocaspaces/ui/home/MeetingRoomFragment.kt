package com.technopolitan.mocaspaces.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.technopolitan.mocaspaces.data.home.MeetingRoomAdapter
import com.technopolitan.mocaspaces.databinding.FragmentMeetingRoomBinding
import com.technopolitan.mocaspaces.di.DaggerApplicationComponent
import com.technopolitan.mocaspaces.di.viewModel.ViewModelFactory
import com.technopolitan.mocaspaces.models.meeting.MeetingRoomMapper
import com.technopolitan.mocaspaces.modules.ApiResponseModule
import javax.inject.Inject

class MeetingRoomFragment : Fragment() {
    //    @Inject
    lateinit var viewModel: HomeViewModel

    lateinit var binding: FragmentMeetingRoomBinding

    @Inject
    lateinit var meetingRoomAdapter: MeetingRoomAdapter

    @Inject
    lateinit var workSpaceApiHandler: ApiResponseModule<List<MeetingRoomMapper>>

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

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
        viewModel =
            ViewModelProvider(requireActivity(), viewModelFactory)[HomeViewModel::class.java]
        initView()
        initMeetingLiveData()
    }


    private fun initView() {
        binding.meetingSpaceLoadMore.progressView.visibility = View.GONE
        binding.meetingRecycler.adapter = meetingRoomAdapter
        listenForScrolling()
        binding.meetingRefreshLayout.setOnRefreshListener {
            viewModel.resetMeeting()
            binding.meetingRefreshLayout.isRefreshing = false

        }
    }

    private fun listenForScrolling() {
        binding.meetingRecycler.addOnScrollListener(scrollListener)
    }

    private val scrollListener: RecyclerView.OnScrollListener = object :
        RecyclerView.OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
            if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == meetingRoomAdapter.itemCount - 1 && meetingRoomAdapter.itemCount > 10) {
                viewModel.updatePageNumber()
                binding.meetingSpaceLoadMore.progressView.visibility = View.VISIBLE
            }
        }
    }

    private fun initMeetingLiveData() {
//        viewModel.getMeetingRoomList().observe(viewLifecycleOwner) { response ->
//            if (meetingRoomAdapter.itemCount == 0)
//                workSpaceApiHandler.handleResponse(
//                    response,
//                    binding.meetingProgress.progressView,
//                    binding.meetingRefreshLayout
//                ) {
//                    viewModel.updateMeetingRoomPage(response.remainingPage)
//                    meetingRoomAdapter.init(it.toMutableList())
//                }
//            else {
//                if (response is SuccessStatus) {
//                    meetingRoomAdapter.init(response.data!!.toMutableList())
//                    viewModel.updateMeetingRoomPage(response.remainingPage)
//                    binding.meetingSpaceLoadMore.progressView.visibility = View.GONE
//                }
//            }
//        }
    }


}