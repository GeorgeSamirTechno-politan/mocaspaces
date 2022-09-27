package com.technopolitan.mocaspaces.data.remote

import androidx.lifecycle.MediatorLiveData
import com.technopolitan.mocaspaces.SharedPrefKey
import com.technopolitan.mocaspaces.bases.BaseRemote
import com.technopolitan.mocaspaces.data.ApiStatus
import com.technopolitan.mocaspaces.data.FailedStatus
import com.technopolitan.mocaspaces.data.HeaderResponse
import com.technopolitan.mocaspaces.data.SuccessStatus
import com.technopolitan.mocaspaces.models.location.request.AddFavouriteWorkSpaceRequest
import com.technopolitan.mocaspaces.modules.NetworkModule
import com.technopolitan.mocaspaces.modules.SharedPrefModule
import com.technopolitan.mocaspaces.network.BaseUrl
import io.reactivex.Flowable
import javax.inject.Inject

class AddFavouriteWorkSpaceRemote @Inject constructor(
    private var networkModule: NetworkModule,
    private var sharedPrefModule: SharedPrefModule
) : BaseRemote<String, String>() {


    private var locationId: Int = 0
    fun addFavourite(locationId: Int): MediatorLiveData<ApiStatus<String>> {
        this.locationId = locationId
        apiMediator = MediatorLiveData()
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