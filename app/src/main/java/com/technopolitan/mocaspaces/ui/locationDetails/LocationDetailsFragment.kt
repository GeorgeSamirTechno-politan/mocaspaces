package com.technopolitan.mocaspaces.ui.locationDetails

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.gms.maps.model.LatLng
import com.technopolitan.mocaspaces.R
import com.technopolitan.mocaspaces.data.home.AmenityAdapter
import com.technopolitan.mocaspaces.data.home.PriceAdapter
import com.technopolitan.mocaspaces.data.locationDetails.MarketingAdapter
import com.technopolitan.mocaspaces.databinding.FragmentLocationDetailsBinding
import com.technopolitan.mocaspaces.di.DaggerApplicationComponent
import com.technopolitan.mocaspaces.di.viewModel.ViewModelFactory
import com.technopolitan.mocaspaces.models.location.mappers.AmenityMapper
import com.technopolitan.mocaspaces.models.location.mappers.LocationDetailsMapper
import com.technopolitan.mocaspaces.models.location.mappers.MarketingMapper
import com.technopolitan.mocaspaces.models.location.mappers.PriceMapper
import com.technopolitan.mocaspaces.modules.ApiResponseModule
import com.technopolitan.mocaspaces.modules.GlideModule
import com.technopolitan.mocaspaces.modules.GoogleMapModule
import com.technopolitan.mocaspaces.modules.NavigationModule
import com.technopolitan.mocaspaces.ui.home.HomeViewModel
import com.technopolitan.mocaspaces.utilities.autoScroll
import com.technopolitan.mocaspaces.utilities.loadHtml
import javax.inject.Inject

class LocationDetailsFragment : Fragment() {

    private lateinit var binding: FragmentLocationDetailsBinding
    private lateinit var viewModel: LocationDetailsViewModel
    private lateinit var homeViewModel: HomeViewModel

    @Inject
    lateinit var googleMapModule: GoogleMapModule

    @Inject
    lateinit var detailsApiHandler: ApiResponseModule<LocationDetailsMapper>

    @Inject
    lateinit var glideModule: GlideModule

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var navigationModule: NavigationModule

    @Inject
    lateinit var marketingAdapter: MarketingAdapter

    private lateinit var amenityAdapter: AmenityAdapter


    private lateinit var priceAdapter: PriceAdapter

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
                    updateToolBarItemColor(R.color.workspace_color)
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
            viewModel.setDetailsRequest(homeViewModel.getSelectedLocationId(), it)
            listenForDetailsApi()
        }
    }

    private fun updateToolBarColor(color: Int) {
        binding.toolBar.setBackgroundColor(requireContext().getColor(color))
    }

    private fun updateToolBarItemColor(color: Int) {
        binding.toolBar.setNavigationIconTint(requireContext().getColor(color))
        binding.shareImageBtn.setColorFilter(requireContext().getColor(color))
        binding.favouriteStatusImageBtn.setColorFilter(requireContext().getColor(color))
        binding.toolBar.setNavigationOnClickListener {
            navigationModule.popBack()
        }
    }

    private fun listenForDetailsApi() {
        viewModel.getDetailsLiveData().observe(viewLifecycleOwner) {
            detailsApiHandler.handleResponse(
                it,
                binding.detailsProgress.progressView,
                binding.detailsLayout
            ) { detailsMapper ->
                setFavourite(detailsMapper.isFavourite)
                loadImage(detailsMapper.mainImage)
                setHasFoodMenu(detailsMapper.hasFoodMenu)
                setPriceAdapter(detailsMapper.priceList)
                setAmenityAdapter(detailsMapper.amenityList)
                setGoogleMap(detailsMapper.locationLatLong, detailsMapper.locationName)
                setMarketingAdapter(detailsMapper.marketingList)
                setOthersDetails(detailsMapper)
            }
        }
    }

    private fun setFavourite(isFavourite: Boolean) {
        if (isFavourite)
            binding.favouriteStatusImageBtn.setImageDrawable(
                AppCompatResources.getDrawable(
                    requireContext(),
                    R.drawable.ic_favourite
                )
            )
        else binding.favouriteStatusImageBtn.setImageDrawable(
            AppCompatResources.getDrawable(
                requireContext(),
                R.drawable.ic_un_favourite
            )
        )
    }

    private fun loadImage(mainImage: String) {
        glideModule.loadImage(mainImage, binding.locationImageView)
    }

    private fun setHasFoodMenu(hasFoodMenu: Boolean) {
        if (hasFoodMenu)
            binding.foodMenuTextView.visibility = View.VISIBLE
        else binding.foodMenuTextView.visibility = View.INVISIBLE
    }

    private fun setPriceAdapter(list: MutableList<PriceMapper>) {
        priceAdapter = PriceAdapter()
        binding.priceViewPager.adapter = priceAdapter
        binding.priceViewPager.autoScroll(5000)
        priceAdapter.setData(list.toMutableList())
    }

    private fun setAmenityAdapter(list: MutableList<AmenityMapper>) {
        amenityAdapter = AmenityAdapter(glideModule)
        binding.amenityRecycler.layoutManager = GridLayoutManager(requireContext(), 6)
        binding.amenityRecycler.adapter = amenityAdapter
        amenityAdapter.setData(list.toMutableList())
    }

    private fun setGoogleMap(latLng: LatLng, title: String) {
        googleMapModule.addMarker(latLng, title).disableMapScroll(true)
            .build(binding.mapInclude.root.id)
    }

    private fun setMarketingAdapter(list: MutableList<MarketingMapper>) {
        binding.marketingRecycler.layoutManager = GridLayoutManager(context, 2)
        binding.marketingRecycler.adapter = marketingAdapter
        marketingAdapter.setData(list.toMutableList())
    }

    private fun setOthersDetails(detailsMapper: LocationDetailsMapper) {
        binding.venueNameTextView.text = detailsMapper.venueName
        binding.locationNameTextView.text = detailsMapper.locationName
        binding.locationDirectionTextView.text = detailsMapper.shortAddress
        binding.addressInfoTextView.text = detailsMapper.longAddress
        binding.workingHourTextView.text = detailsMapper.workTimeMapper.getOpenHourText()
        binding.aboutInfoTextView.loadHtml(detailsMapper.about)
        binding.termsOfUseInfoTextView.loadHtml(detailsMapper.termsOfUse)
    }

    override fun onDestroyView() {
        homeViewModel.setBackFromDetailsLiveData(true)
        super.onDestroyView()
    }


}