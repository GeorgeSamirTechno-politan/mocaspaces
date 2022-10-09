package com.technopolitan.mocaspaces.data.remote

import com.technopolitan.mocaspaces.enums.SharedPrefKey
import com.technopolitan.mocaspaces.bases.BaseRemote
import com.technopolitan.mocaspaces.data.ApiStatus
import com.technopolitan.mocaspaces.data.FailedStatus
import com.technopolitan.mocaspaces.data.HeaderResponse
import com.technopolitan.mocaspaces.data.SuccessStatus
import com.technopolitan.mocaspaces.models.location.request.AddFavouriteWorkSpaceRequest
import com.technopolitan.mocaspaces.modules.NetworkModule
import com.technopolitan.mocaspaces.modules.SharedPrefModule
import com.technopolitan.mocaspaces.network.BaseUrl
import com.technopolitan.mocaspaces.utilities.SingleLiveEvent
import io.reactivex.Flowable
import javax.inject.Inject

class AddFavouriteWorkSpaceRemote @Inject constructor(
    private var networkModule: NetworkModule,
    private var sharedPrefModule: SharedPrefModule
) : BaseRemote<String, String>() {


    private var locationId: Int = 0
    fun addFavourite(locationId: Int): SingleLiveEvent<ApiStatus<String>> {
        this.locationId = locationId
        return handleApi()
    }

    override fun flowable(): Flowable<HeaderResponse<String>> {
        return networkModule.provideService(BaseUrl.locationApi).addWorkspaceFavourite(
            addFavouriteWorkSpaceRequest = AddFavouriteWorkSpaceRequest(
                id = 0,
                basicUserId = sharedPrefModule.getIntFromShared(SharedPrefKey.UserId.name),
                locationId = locationId
            )
        )
    }

    override fun handleResponse(it: HeaderResponse<String>): ApiStatus<String> {
        return if (it.succeeded) {
            SuccessStatus(it.message, it.data)
        } else FailedStatus(it.message)
    }
}