package com.technopolitan.mocaspaces.ui.locationDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.technopolitan.mocaspaces.bases.BaseViewModel
import com.technopolitan.mocaspaces.data.ApiStatus
import com.technopolitan.mocaspaces.data.repo.DetailsType
import com.technopolitan.mocaspaces.data.repo.LocationDetailsRepo
import com.technopolitan.mocaspaces.models.location.mappers.LocationDetailsMapper
import com.technopolitan.mocaspaces.models.shared.PriceResponse
import javax.inject.Inject

class LocationDetailsViewModel @Inject constructor(private var locationDetailsRepo: LocationDetailsRepo) :
    BaseViewModel<LocationDetailsMapper>() {

    private var favouriteMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()
    private var locationId: Int = 0
    fun getDetailsLiveData(): LiveData<ApiStatus<LocationDetailsMapper>> = apiMediatorLiveData
    private lateinit var locationDetailsMapper: LocationDetailsMapper

    init {
        favouriteMutableLiveData.postValue(false)
    }

    fun getFavouriteLiveData(): LiveData<Boolean> = favouriteMutableLiveData

    fun setFavourite(isFavourite: Boolean) {
        favouriteMutableLiveData.postValue(isFavourite)
    }

    fun setLocationId(locationId: Int) {
        this.locationId = locationId
    }

    fun getLocationId(): Int = locationId

    fun getFavourite(): Boolean? = favouriteMutableLiveData.value


    fun setDetailsRequest(locationId: Int, viewType: Int) {
        apiMediatorLiveData =
            locationDetailsRepo.getDetails(locationId, getViewTypeDetails(viewType))
    }

    fun getPriceResponse(): PriceResponse = locationDetailsMapper.priceResponse

    fun getCurrency(): String = locationDetailsMapper.currency

    fun setDetails(locationDetailsMapper: LocationDetailsMapper) {
        this.locationDetailsMapper = locationDetailsMapper
    }

    private fun getViewTypeDetails(viewType: Int): DetailsType {
        return when (viewType) {
            1 -> DetailsType.WorkSpace
            2 -> DetailsType.MeetingSpace
            else -> DetailsType.EventSpace
        }
    }
}