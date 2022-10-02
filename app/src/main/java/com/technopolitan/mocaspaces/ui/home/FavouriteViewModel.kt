package com.technopolitan.mocaspaces.ui.home

import androidx.lifecycle.LifecycleOwner
import com.technopolitan.mocaspaces.bases.BaseViewModel
import com.technopolitan.mocaspaces.data.remote.AddFavouriteWorkSpaceRemote
import com.technopolitan.mocaspaces.data.remote.DeleteWorkSpaceFavouriteRemote
import com.technopolitan.mocaspaces.modules.ApiResponseModule
import com.technopolitan.mocaspaces.utilities.SingleLiveEvent
import javax.inject.Inject

class FavouriteViewModel @Inject constructor(
    private var addFavouriteWorkSpaceRemote: AddFavouriteWorkSpaceRemote,
    private var deleteWorkSpaceFavouriteRemote: DeleteWorkSpaceFavouriteRemote,
    private var apiResponseModule: ApiResponseModule<String>
) : BaseViewModel<String>() {

    private var locationId: Int = 0
    private var isFavourite: Boolean = false

    fun setFavourite(
        locationId: Int,
        isFavourite: Boolean,
        lifecycleOwner: LifecycleOwner,
        callback: (locationId: Int, updatedFavourite: Boolean) -> Unit
    ) {
        this.locationId = locationId
        this.isFavourite = isFavourite
        if (isFavourite)
            setDeleteFavourite()
        else setAddFavourite()
        handleFavourite(lifecycleOwner, callback)
    }

    private fun setAddFavourite() {
        apiMediatorLiveData = SingleLiveEvent()
        apiMediatorLiveData = addFavouriteWorkSpaceRemote.addFavourite(locationId)
    }

    private fun setDeleteFavourite() {
        apiMediatorLiveData = SingleLiveEvent()
        apiMediatorLiveData = deleteWorkSpaceFavouriteRemote.deleteFavourite(locationId)
    }

    private fun handleFavourite(
        lifecycleOwner: LifecycleOwner,
        callback: (locationId: Int, updatedFavourite: Boolean) -> Unit
    ) {
        apiMediatorLiveData.observe(lifecycleOwner) {
            apiResponseModule.handleResponse(it, true) {
                callback(locationId, isFavourite.not())
            }
        }
    }
}