package com.technopolitan.mocaspaces.ui.locationDetails

import androidx.lifecycle.LiveData
import com.technopolitan.mocaspaces.bases.BaseViewModel
import com.technopolitan.mocaspaces.data.ApiStatus
import com.technopolitan.mocaspaces.data.repo.DetailsType
import com.technopolitan.mocaspaces.data.repo.LocationDetailsRepo
import com.technopolitan.mocaspaces.models.location.mappers.LocationDetailsMapper
import javax.inject.Inject

class LocationDetailsViewModel @Inject constructor(private var locationDetailsRepo: LocationDetailsRepo) :
    BaseViewModel<LocationDetailsMapper>() {

    fun getDetailsLiveData(): LiveData<ApiStatus<LocationDetailsMapper>> = apiMediatorLiveData

    fun setDetailsRequest(locationId: Int, viewType: Int) {
        apiMediatorLiveData =
            locationDetailsRepo.getDetails(locationId, getViewTypeDetails(viewType))
    }

    fun getViewTypeDetails(viewType: Int): DetailsType {
        return when (viewType) {
            1 -> DetailsType.WorkSpace
            2 -> DetailsType.MeetingSpace
            else -> DetailsType.EventSpace
        }
    }
}