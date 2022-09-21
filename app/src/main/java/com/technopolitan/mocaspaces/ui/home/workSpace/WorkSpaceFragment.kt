package com.technopolitan.mocaspaces.ui.home.workSpace

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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
    lateinit var favouriteApiHandler: ApiResponseModule<String>

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
        }
    }


    private fun initView() {
//        binding.workSpaceLoadMore.progressView.visibility = View.GONE
        binding.workSpaceRecycler.adapter = workSpaceAdapter
        workSpaceAdapter.setFavouriteCallBack {
            setFavourite(it)
        }
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
        workSpaceAdapter.setData(mutableListOf(), false)
    }

    private fun listenForScrolling() {
        binding.workSpaceRecycler.loadMore(workspaceViewModel.hasLoadMore()) {
            workspaceViewModel.loadMore()
//            binding.workSpaceLoadMore.progressView.visibility = View.VISIBLE
        }
    }


    private fun initWorkSpaceLiveData() {
        workspaceViewModel.getWorkSpaceList().observe(viewLifecycleOwner) { response ->
            if (workSpaceAdapter.itemCount == 0) {
                workSpaceApiHandler.handleResponse(
                    response,
                    binding.workSpaceProgress.progressView,
                    binding.workSpaceRefreshLayout
                ) {
                    workspaceViewModel.updateWorkSpaceRemainingPage(response.remainingPage)
                    workSpaceAdapter.setData(it.toMutableList(), response.remainingPage > 0)
                    workspaceViewModel.removeSourceFromWorkSpaceApi()
                }
            } else {
                workSpaceApiHandler.handleResponse(response) {
                    workSpaceAdapter.addMoreDate(
                        response.data!!.toMutableList(),
                        response.remainingPage > 0
                    )
                    workspaceViewModel.updateWorkSpaceRemainingPage(response.remainingPage)
                    workspaceViewModel.removeSourceFromWorkSpaceApi()
                }
            }
        }
    }

    private fun setFavourite(item: WorkSpaceMapper) {
        if (item.isFavourite)
            workspaceViewModel.setDeleteFavourite(item.id)
        else workspaceViewModel.setAddFavourite(item.id)
        listenForFavouriteApi(item.isFavourite)
    }

    private fun listenForFavouriteApi(isFavourite: Boolean) {
        workspaceViewModel.getFavourite().observe(viewLifecycleOwner) {
            favouriteApiHandler.handleResponse(it) {
                if (isFavourite)
                    workspaceViewModel.removeSourceOfDeleteFavourite()
                else workspaceViewModel.removeSourceOfAddFavourite()
            }
        }
    }
}