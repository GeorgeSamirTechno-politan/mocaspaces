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
import com.technopolitan.mocaspaces.data.SuccessStatus
import com.technopolitan.mocaspaces.data.home.MeetingRoomAdapter
import com.technopolitan.mocaspaces.databinding.FragmentEventSpaceBinding
import com.technopolitan.mocaspaces.di.DaggerApplicationComponent
import com.technopolitan.mocaspaces.di.viewModel.ViewModelFactory
import com.technopolitan.mocaspaces.models.meeting.MeetingRoomMapper
import com.technopolitan.mocaspaces.modules.ApiResponseModule
import javax.inject.Inject

class EventSpaceFragment : Fragment() {
    //    @Inject
    lateinit var viewModel: HomeViewModel

    lateinit var binding: FragmentEventSpaceBinding

    @Inject
    lateinit var eventSpaceAdapter: MeetingRoomAdapter

    @Inject
    lateinit var eventSpaceApiHandler: ApiResponseModule<List<MeetingRoomMapper>>

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
        binding = FragmentEventSpaceBinding.inflate(layoutInflater, container, false)
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
        binding.eventSpaceLoadMore.progressView.visibility = View.GONE
        binding.eventRecycler.adapter = eventSpaceAdapter
        listenForScrolling()
        binding.eventRefreshLayout.setOnRefreshListener {
            viewModel.resetEventSpace()
            binding.eventRefreshLayout.isRefreshing = false

        }
    }

    private fun listenForScrolling() {
        binding.eventRecycler.addOnScrollListener(scrollListener)
    }

    private val scrollListener: RecyclerView.OnScrollListener = object :
        RecyclerView.OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
            if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == eventSpaceAdapter.itemCount - 1 && eventSpaceAdapter.itemCount> 10) {
                viewModel.updatePageNumber()
                binding.eventSpaceLoadMore.progressView.visibility = View.VISIBLE
            }
        }
    }

    private fun initMeetingLiveData() {
        viewModel.getEventRoomList().observe(viewLifecycleOwner) { response ->
            if (eventSpaceAdapter.itemCount == 0)
                eventSpaceApiHandler.handleResponse(
                    response,
                    binding.eventProgress.progressView,
                    binding.eventRefreshLayout
                ) {
                    viewModel.updateEventSpacePage(response.remainingPage)
                    eventSpaceAdapter.init(it.toMutableList())
                }
            else {
                if(response is SuccessStatus){
                    eventSpaceAdapter.init(response.data!!.toMutableList())
                    viewModel.updateEventSpacePage(response.remainingPage)
                    binding.eventSpaceLoadMore.progressView.visibility = View.GONE
                }
            }
        }
    }
}