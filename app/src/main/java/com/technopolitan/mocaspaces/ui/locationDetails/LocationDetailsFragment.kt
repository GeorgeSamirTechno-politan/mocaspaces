package com.technopolitan.mocaspaces.ui.locationDetails

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.appbar.AppBarLayout
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
import com.technopolitan.mocaspaces.modules.*
import com.technopolitan.mocaspaces.ui.home.FavouriteViewModel
import com.technopolitan.mocaspaces.ui.home.HomeViewModel
import com.technopolitan.mocaspaces.ui.home.workSpace.WorkSpaceViewModel
import com.technopolitan.mocaspaces.utilities.*
import javax.inject.Inject

class LocationDetailsFragment : Fragment() {

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

    @Inject
    lateinit var utilityModule: UtilityModule

    private lateinit var amenityAdapter: AmenityAdapter
    private lateinit var priceAdapter: PriceAdapter
    private lateinit var binding: FragmentLocationDetailsBinding
    private lateinit var viewModel: LocationDetailsViewModel
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var favouriteViewModel: FavouriteViewModel
    private lateinit var workSpaceViewModel: WorkSpaceViewModel
    private var bookingType: Int = 1

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
        updateFavourite()
    }

    private fun initViewModels() {
        viewModel =
            ViewModelProvider(
                requireActivity(),
                viewModelFactory
            )[LocationDetailsViewModel::class.java]
        homeViewModel =
            ViewModelProvider(requireActivity(), viewModelFactory)[HomeViewModel::class.java]
        favouriteViewModel =
            ViewModelProvider(this, viewModelFactory)[FavouriteViewModel::class.java]
        workSpaceViewModel =
            ViewModelProvider(requireActivity(), viewModelFactory)[WorkSpaceViewModel::class.java]
    }

    private fun listenForViewType() {
        homeViewModel.getViewType().observe(viewLifecycleOwner) {
            when (it) {
                1 -> {
                    updateToolBarColor(R.color.accent_color)
                    updateToolBarItemColor(R.color.workspace_color)
                    binding.venueNameTextView.visibility = View.INVISIBLE
                    bookingType = 1
                }
                2 -> {
                    updateToolBarColor(R.color.meeting_space_color)
                    updateToolBarItemColor(R.color.meeting_space_color)
                    binding.venueNameTextView.visibility = View.VISIBLE
                    bookingType = 2
                }
                3 -> {
                    updateToolBarColor(R.color.event_space_color)
                    updateToolBarItemColor(R.color.event_space_color)
                    binding.venueNameTextView.visibility = View.VISIBLE
                    bookingType = 3
                }
            }
            viewModel.setDetailsRequest(homeViewModel.getSelectedLocationId(), it)
            listenForFavourite()
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
        updateCollapsing()
    }

    private fun updateCollapsing() {
        binding.appBar.addOnOffsetChangedListener(appBarChangeListener)
    }


    private fun listenForDetailsApi() {
        viewModel.getDetailsLiveData().observe(viewLifecycleOwner) {
            detailsApiHandler.handleResponse(
                it,
                binding.detailsProgress.progressView,
                binding.detailsLayout
            ) { detailsMapper ->
                setFavourite(detailsMapper.isFavourite)
                viewModel.setLocationId(detailsMapper.id)
                setShareClicking(detailsMapper.shareLink)
                loadImage(detailsMapper.mainImage)
                setHasFoodMenu(detailsMapper.hasFoodMenu)
                setPriceAdapter(detailsMapper.priceList)
                setAmenityAdapter(detailsMapper.amenityList)
                setGoogleMap(detailsMapper.locationLatLong, detailsMapper.locationName)
                setMarketingAdapter(detailsMapper.marketingList)
                setOthersDetails(detailsMapper)
                viewModel.setDetails(detailsMapper)
                binding.detailsBtn.setOnClickListener {
                    handleClickOnBook()
                }
            }
        }
    }

    private fun listenForFavourite() {
        viewModel.getFavouriteLiveData().observe(viewLifecycleOwner) {
            if (it)
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

    }

    private fun setFavourite(isFavourite: Boolean) {
        viewModel.setFavourite(isFavourite)
    }

    private fun updateFavourite() {
        binding.favouriteStatusImageBtn.setOnClickListener {
            viewModel.getFavourite()?.let {
                favouriteViewModel.setFavourite(
                    viewModel.getLocationId(),
                    it,
                    viewLifecycleOwner
                ) { locationId, updatedFavourite ->
                    setFavourite(updatedFavourite)
                    workSpaceViewModel.updateItem(locationId, updatedFavourite)
                }
            }
        }
    }

    private fun setShareClicking(shareLink: String) {
        binding.shareImageBtn.setOnClickListener {
            utilityModule.shareLink(shareLink)
        }
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
        try {
            val supportMapFragment =
                childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
            googleMapModule.addMarker(latLng, title).disableMapScroll(true)
                .build(supportMapFragment)
        } catch (e: Exception) {
            Log.e(javaClass.name, "setGoogleMap: ", e)
        }
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

    private val appBarChangeListener: AppBarStateChangeListener =
        object : AppBarStateChangeListener() {
            override fun onStateChanged(appBarLayout: AppBarLayout?, appBarState: AppBarState?) {
                when (appBarState) {
                    AppBarState.EXPANDED -> {
                        setAppBarWithExpandedOrIdle()
                    }
                    AppBarState.COLLAPSED -> {
                        setAppBarWithCollapsed()
                    }
                    AppBarState.IDLE -> {
                        setAppBarWithExpandedOrIdle()
                    }
                    null -> {
                        setAppBarWithExpandedOrIdle()
                    }
                }
            }

        }

    private fun setAppBarWithExpandedOrIdle() {
        binding.scrollView.setBackgroundResource(R.drawable.location_detail_top_rounded_corner)
        binding.scrollView.setMargin(top = resources.getDimensionPixelOffset(com.intuit.sdp.R.dimen._minus20sdp))
    }

    private fun setAppBarWithCollapsed() {
        binding.scrollView.setBackgroundColor(requireContext().getColor(R.color.white))
        binding.scrollView.setMargin()
    }

    override fun onDestroyView() {
        homeViewModel.setBackFromDetailsLiveData(true)
        super.onDestroyView()
    }

    /**
     *
     * @see bookingType
     * it used for different navigation
     * 1 for workspace
     * 2 for meeting
     * 3 for event space
     *
     * */
    private fun handleClickOnBook() {
        when (bookingType) {
            1 -> {
                navigationModule.navigateTo(R.id.action_location_details_to_work_space_plans_fragment)
            }
            2 -> {}
            3 -> {}
        }
    }


}