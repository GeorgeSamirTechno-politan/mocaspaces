package com.technopolitan.mocaspaces.ui.home.workSpace

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView.SmoothScroller
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.data.home.WorkSpaceAdapter
import com.technopolitan.mocaspaces.databinding.FragmentWorkSpaceBinding
import com.technopolitan.mocaspaces.di.DaggerApplicationComponent
import com.technopolitan.mocaspaces.di.viewModel.ViewModelFactory
import com.technopolitan.mocaspaces.models.location.mappers.WorkSpaceMapper
import com.technopolitan.mocaspaces.modules.ApiResponseModule
import com.technopolitan.mocaspaces.modules.NavigationModule
import com.technopolitan.mocaspaces.ui.home.HomeViewModel
import com.technopolitan.mocaspaces.utilities.loadMore
import javax.inject.Inject


class WorkSpaceFragment : Fragment() {

    lateinit var binding: FragmentWorkSpaceBinding

    @Inject
    lateinit var workSpaceAdapter: WorkSpaceAdapter

    @Inject
    lateinit var workSpaceApiHandler: ApiResponseModule<List<WorkSpaceMapper?>>

    @Inject
    lateinit var favouriteApiHandler: ApiResponseModule<String>

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var navigationModule: NavigationModule

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
        listForWorkSpaceListMediator()
    }

    private fun listenFormLocation() {
        homeViewModel.getLocationLiveData().observe(viewLifecycleOwner) {
            workspaceViewModel.setLocation(it)
        }
    }

    private fun listenForFilter() {
        homeViewModel.getWorkSpaceFilterLiveData().observe(viewLifecycleOwner) {
            clearAdapter()
            workspaceViewModel.setFilter(type = it.type, id = it.id)
            initWorkSpaceLiveData()
        }
    }


    private fun initView() {
        listenForScrolling()
        workSpaceAdapter.setFavouriteCallBack { item, position ->
            setFavourite(item, position)
        }
        workSpaceAdapter.setBookCallBack {
            homeViewModel.setSelectedLocationId(it.id)
            navigationModule.navigateTo(R.id.action_home_to_location_details)

        }
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
        workSpaceAdapter.clearAdapter()
    }

    private fun listenForScrolling() {
        binding.workSpaceRecycler.loadMore {
            if (workspaceViewModel.hasLoadMore() && workSpaceAdapter.itemCount > 0)
                workspaceViewModel.loadMore()
        }
    }


    private fun initWorkSpaceLiveData() {
        workspaceViewModel.getWorkSpaceList().observe(viewLifecycleOwner) { response ->
            if (workSpaceAdapter.itemCount == 0) {
                workSpaceApiHandler.handleResponse(
                    response,
                    binding.workSpaceProgress.progressView,
                ) {
                    workspaceViewModel.updateWorkSpaceRemainingPage(response.remainingPage)
                    workspaceViewModel.setWorkSpaceListMediator(it.toMutableList())
//                    workspaceViewModel.removeSourceFromWorkSpaceApi()
                }
            } else {
                workSpaceApiHandler.handleResponse(response) {
                    workspaceViewModel.updateWorkSpaceRemainingPage(response.remainingPage)
                    workspaceViewModel.setWorkSpaceListMediator(response.data!!.toMutableList())
//                    workspaceViewModel.removeSourceFromWorkSpaceApi()
                }
            }
        }
    }

    private fun listForWorkSpaceListMediator() {
        workspaceViewModel.getWorkSpaceListLiveData().observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                workSpaceAdapter.setData(it)
                binding.workSpaceRecycler.adapter = workSpaceAdapter
                if(workspaceViewModel.scrolledPosition > 0)
                   Handler(Looper.getMainLooper()).postDelayed({
                       binding.workSpaceRecycler.smoothScrollToPosition(workspaceViewModel.scrolledPosition)
                   }, 500)
            }
        }
    }


    private fun setFavourite(item: WorkSpaceMapper, position: Int) {
        if (item.isFavourite)
            workspaceViewModel.setDeleteFavourite(item.id)
        else workspaceViewModel.setAddFavourite(item.id)
        listenForFavouriteApi(item, position)
    }

    private fun listenForFavouriteApi(item: WorkSpaceMapper, position: Int) {
        workspaceViewModel.getFavourite().observe(viewLifecycleOwner) {
            favouriteApiHandler.handleResponse(it) {
//                workspaceViewModel.updateItem(item)
//                workspaceViewModel.removeSourceOfAddAndDeleteFavourite()
                item.isFavourite = item.isFavourite.not()
//                workSpaceAdapter.updateFavouriteItem(item, position)
                workspaceViewModel.updateItem(item, position)
            }
        }
    }
}