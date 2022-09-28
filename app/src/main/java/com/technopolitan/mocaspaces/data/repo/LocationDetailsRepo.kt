package com.technopolitan.mocaspaces.data.repo

import androidx.lifecycle.MediatorLiveData
import com.technopolitan.mocaspaces.data.ApiStatus
import com.technopolitan.mocaspaces.data.remote.WorkSpaceDetailsRemote
import com.technopolitan.mocaspaces.models.location.mappers.LocationDetailsMapper
import javax.inject.Inject

enum class DetailsType {
    WorkSpace,
    MeetingSpace,
    EventSpace,

}

class LocationDetailsRepo @Inject constructor(private var workSpaceDetailsRemote: WorkSpaceDetailsRemote) {

    fun getDetails(
        locationId: Int,
        detailsType: DetailsType
    ): MediatorLiveData<ApiStatus<LocationDetailsMapper>> {
        return when (detailsType) {
            DetailsType.WorkSpace -> workSpaceDetailsRemote.getWorkSpaceDetails(locationId)
            DetailsType.MeetingSpace -> workSpaceDetailsRemote.getWorkSpaceDetails(locationId)
            DetailsType.EventSpace -> workSpaceDetailsRemote.getWorkSpaceDetails(locationId)
        }
    }
}