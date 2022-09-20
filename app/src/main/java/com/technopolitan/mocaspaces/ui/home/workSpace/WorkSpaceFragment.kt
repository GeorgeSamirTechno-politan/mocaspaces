package com.technopolitan.mocaspaces.ui.home.workSpace

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.technopolitan.mocaspaces.data.SuccessStatus
import com.technopolitan.mocaspaces.data.home.WorkSpaceAdapter
import com.technopolitan.mocaspaces.databinding.FragmentWorkSpaceBinding
import com.technopolitan.mocaspaces.di.DaggerApplicationComponent
import com.technopolitan.mocaspaces.di.viewModel.ViewModelFactory
import com.technopolitan.mocaspaces.models.location.mappers.WorkSpaceMapper
import com.technopolitan.mocaspaces.modules.ApiResponseModule
import com.technopolitan.mocaspaces.ui.home.HomeViewModel
import com.technopolitan.mocaspaces.utilities.loadMore
import javax.inject.Inject


class WorkSpaceFragment : Fragment() {

    lateinit var binding: FragmentWorkSpaceBinding

    @Inject
    lateinit var workSpaceAdapter: WorkSpaceAdapter

    @Inject
    lateinit var workSpaceApiHandler: ApiResponseModule<List<WorkSpaceMapper>>

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var workspaceViewModel: WorkSpaceViewModel

    override fun onAttach(context: Context) {
        DaggerApplicationComponent.factory().buildDi(requireContext(), requireActivity(), this)
            .inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWorkSpaceBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel =
            ViewModelProvider(requireActivity(), viewModelFactory)[HomeViewModel::class.java]
        workspaceViewModel =
            ViewModelProvider(requireActivity(), viewModelFactory)[WorkSpaceViewModel::class.java]
        listenForMediators()
        initView()

    }

    private fun listenForMediators() {
        listenFormLocation()
        listenForFilter()
    }

    private fun listenFormLocation() {
        homeViewModel.getLocationLiveData().observe(viewLifecycleOwner) {
            workspaceViewModel.setLocation(it)
        }
    }

    private fun listenForFilter() {
        homeViewModel.getWorkSpaceFilterLiveData().observe(viewLifecycleOwner) {
            workspaceViewModel.setFilter(type = it.type, id = it.id)
            clearAdapter()
            initWorkSpaceLiveData()
            initLoadMore()
        }
    }


    private fun initView() {
        binding.workSpaceLoadMore.progressView.visibility = View.GONE
        binding.workSpaceRecycler.adapter = workSpaceAdapter
        listenForScrolling()
        listenForSwipeToRefresh()
    }

    private fun listenForSwipeToRefresh() {
        binding.workSpaceRefreshLayout.setOnRefreshListener {
            workspaceViewModel.refresh()
            clearAdapter()
//            homeViewModel.setSearchHint(SearchHintMapper())
            binding.workSpaceRefreshLayout.isRefreshing = false
        }
    }

    private fun clearAdapter() {
        workSpaceAdapter.clearList()
    }

    private fun listenForScrolling() {
        binding.workSpaceRecycler.loadMore(workspaceViewModel.hasLoadMore()) {
            workspaceViewModel.loadMore()
            binding.workSpaceLoadMore.progressView.visibility = View.VISIBLE
        }
    }


    private fun initWorkSpaceLiveData() {
        workspaceViewModel.getWorkSpaceList().observe(viewLifecycleOwner) { response ->
            workSpaceApiHandler.handleResponse(
                response,
                binding.workSpaceProgress.progressView,
                binding.workSpaceRefreshLayout
            ) {
                workspaceViewModel.updateWorkSpaceRemainingPage(response.remainingPage)
                workSpaceAdapter.init(it.toMutableList())

            }
        }
    }

    private fun initLoadMore() {
        workspaceViewModel.loadMoreLiveData().observe(viewLifecycleOwner) {
            if (it is SuccessStatus) {
                workSpaceAdapter.addList(it.data!!.toMutableList())
                workspaceViewModel.updateWorkSpaceRemainingPage(it.remainingPage)
                binding.workSpaceLoadMore.progressView.visibility = View.GONE
            }
        }
    }
}