package com.technopolitan.mocaspaces.ui.locationDetails

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.databinding.FragmentLocationDetailsBinding
import com.technopolitan.mocaspaces.di.DaggerApplicationComponent
import com.technopolitan.mocaspaces.di.viewModel.ViewModelFactory
import com.technopolitan.mocaspaces.modules.GoogleMapModule
import com.technopolitan.mocaspaces.ui.home.HomeViewModel
import javax.inject.Inject

class LocationDetailsFragment : Fragment() {

    private lateinit var binding: FragmentLocationDetailsBinding
    private lateinit var viewModel: LocationDetailsViewModel
    private lateinit var homeViewModel: HomeViewModel

    @Inject
    lateinit var googleMapModule: GoogleMapModule

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    override fun onAttach(context: Context) {
        DaggerApplicationComponent.factory().buildDi(context, requireActivity(), this).inject(this)
        super.onAttach(context)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocationDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModels()
        listenForViewType()
    }

    private fun initViewModels() {
        viewModel =
            ViewModelProvider(this, viewModelFactory)[LocationDetailsViewModel::class.java]
        homeViewModel =
            ViewModelProvider(requireActivity(), viewModelFactory)[HomeViewModel::class.java]
    }

    private fun listenForViewType() {
        homeViewModel.getViewType().observe(viewLifecycleOwner) {
            when (it) {
                1 -> {
                    updateToolBarColor(R.color.accent_color)
                    updateToolBarItemColor(R.color.accent_color)
                    binding.venueNameTextView.visibility = View.INVISIBLE
                }
                2 -> {
                    updateToolBarColor(R.color.meeting_space_color)
                    updateToolBarItemColor(R.color.meeting_space_color)
                    binding.venueNameTextView.visibility = View.VISIBLE
                }
                3 -> {
                    updateToolBarColor(R.color.event_space_color)
                    updateToolBarItemColor(R.color.event_space_color)
                    binding.venueNameTextView.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun updateToolBarColor(color: Int) {
        binding.toolBar.setBackgroundColor(requireContext().getColor(color))
    }

    private fun updateToolBarItemColor(color: Int) {
        binding.toolBar.setNavigationIconTint(requireContext().getColor(color))
        binding.shareImageBtn.setColorFilter(requireContext().getColor(color))
        binding.favouriteStatusImageBtn.setColorFilter(requireContext().getColor(color))
    }

//    private fun listenFor

}