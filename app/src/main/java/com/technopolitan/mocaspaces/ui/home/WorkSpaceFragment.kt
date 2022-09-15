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
import com.technopolitan.mocaspaces.data.ApiStatus
import com.technopolitan.mocaspaces.data.SuccessStatus
import com.technopolitan.mocaspaces.data.home.WorkSpaceAdapter
import com.technopolitan.mocaspaces.databinding.FragmentWorkSpaceBinding
import com.technopolitan.mocaspaces.di.DaggerApplicationComponent
import com.technopolitan.mocaspaces.models.location.mappers.WorkSpaceMapper
import com.technopolitan.mocaspaces.modules.ApiResponseModule
import javax.inject.Inject


class WorkSpaceFragment : Fragment() {

    @Inject
    lateinit var viewModel: HomeViewModel
    lateinit var binding: FragmentWorkSpaceBinding

    @Inject
    lateinit var workSpaceAdapter: WorkSpaceAdapter

    @Inject
    lateinit var workSpaceApiHandler: ApiResponseModule<List<WorkSpaceMapper>>

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
        initView()
        initWorkSpaceLiveData()
    }


    private fun initView() {
        binding.workSpaceLoadMore.progressView.visibility = View.GONE
        binding.workSpaceRecycler.adapter = workSpaceAdapter
        listenForScrolling()
    }

    private fun listenForScrolling() {
        binding.workSpaceRecycler.addOnScrollListener(scrollListener)
    }

    private val scrollListener: RecyclerView.OnScrollListener = object :
        RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
            if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == workSpaceAdapter.itemCount - 1) {
                viewModel.updatePageNumber()
                binding.workSpaceLoadMore.progressView.visibility = View.VISIBLE
            }
        }
    }

    private fun initWorkSpaceLiveData() {
        viewModel.getWorkSpaceList().observe(viewLifecycleOwner) { response ->
            if (workSpaceAdapter.itemCount == 0)
                workSpaceApiHandler.handleResponse(
                    response,
                    binding.workSpaceProgress.progressView,
                    binding.workSpaceRefreshLayout
                ) {
                    viewModel.updateWorkSpaceRemainingPage(response.remainingPage)
                    workSpaceAdapter.init(it.toMutableList())
                }
            else {
                if(response is SuccessStatus){
                    workSpaceAdapter.init(response.data!!.toMutableList())
                    viewModel.updateWorkSpaceRemainingPage(response.remainingPage)
                    binding.workSpaceLoadMore.progressView.visibility = View.GONE
                }
            }
        }
    }
}