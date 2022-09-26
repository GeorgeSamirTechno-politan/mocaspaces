package com.technopolitan.mocaspaces.ui.home.bizLounge

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.technopolitan.mocaspaces.data.home.BizLoungeAdapter
import com.technopolitan.mocaspaces.databinding.FragmentBizLoungeBinding
import com.technopolitan.mocaspaces.di.DaggerApplicationComponent
import com.technopolitan.mocaspaces.di.viewModel.ViewModelFactory
import com.technopolitan.mocaspaces.models.location.bizLounge.BizLoungeMapper
import com.technopolitan.mocaspaces.modules.ApiResponseModule
import com.technopolitan.mocaspaces.ui.home.HomeViewModel
import com.technopolitan.mocaspaces.utilities.loadMore
import javax.inject.Inject

class BizLoungeFragment : Fragment() {
    lateinit var binding: FragmentBizLoungeBinding

    @Inject
    lateinit var bizLoungeAdapter: BizLoungeAdapter

    @Inject
    lateinit var bizLoungeApiHandler: ApiResponseModule<List<BizLoungeMapper?>>

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var bizLoungeViewMode: BizLoungeViewModel

    override fun onAttach(context: Context) {
        DaggerApplicationComponent.factory().buildDi(requireContext(), requireActivity(), this)
            .inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBizLoungeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel =
            ViewModelProvider(requireActivity(), viewModelFactory)[HomeViewModel::class.java]
        bizLoungeViewMode =
            ViewModelProvider(requireActivity(), viewModelFactory)[BizLoungeViewModel::class.java]
        listenForMediators()
        initView()

    }

    private fun listenForMediators() {
        listenForFilter()
        listForAdapterMediator()
    }


    private fun listenForFilter() {
        homeViewModel.getBizLoungeFilterLiveData().observe(viewLifecycleOwner) {
            clearAdapter()
            bizLoungeViewMode.setFilter(type = it.type, id = it.id)
            listenForApiLiveData()
        }
    }


    private fun initView() {
        listenForScrolling()
        bizLoungeAdapter.setClickOnBook {

        }
        listenForSwipeToRefresh()
    }

    private fun listenForSwipeToRefresh() {
        binding.bizLoungeRefreshLayout.setOnRefreshListener {
            bizLoungeViewMode.refresh()
            clearAdapter()
            binding.bizLoungeRefreshLayout.isRefreshing = false
        }
    }

    private fun clearAdapter() {
        bizLoungeAdapter.clearAdapter()
    }

    private fun listenForScrolling() {
        binding.bizLoungeRecycler.loadMore {
            if (bizLoungeViewMode.hasLoadMore() && bizLoungeAdapter.itemCount > 0)
                bizLoungeViewMode.loadMore()
        }
    }


    private fun listenForApiLiveData() {
        bizLoungeViewMode.getApiList().observe(viewLifecycleOwner) { response ->
            if (bizLoungeAdapter.itemCount == 0) {
                bizLoungeApiHandler.handleResponse(
                    response,
                    binding.bizLoungeProgress.progressView,
                ) {
                    bizLoungeViewMode.updateWorkSpaceRemainingPage(response.remainingPage)
                    bizLoungeViewMode.setBizLoungeListMediator(it.toMutableList())
                    bizLoungeViewMode.removeSourceBizLoungeApi()
                }
            } else {
                bizLoungeApiHandler.handleResponse(response) {
                    bizLoungeViewMode.updateWorkSpaceRemainingPage(response.remainingPage)
                    bizLoungeViewMode.setBizLoungeListMediator(response.data!!.toMutableList())
                    bizLoungeViewMode.removeSourceBizLoungeApi()
                }
            }
        }
    }

    private fun listForAdapterMediator() {
        bizLoungeViewMode.getAdapterListLiveData().observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                bizLoungeAdapter.setData(it)
                binding.bizLoungeRecycler.adapter = bizLoungeAdapter
                binding.bizLoungeRecycler.setHasFixedSize(true)
            }
        }
    }

}